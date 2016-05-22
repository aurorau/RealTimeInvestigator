

$(document).ready(function() {

	getCurrentUserCount();
	setInterval(function(){ 
		getCurrentUserCount();
	},30000);

});

function getCurrentUserCount () {
	var allUsers = 0;
	var desktoUserCount = 0;
	var mobileUserCount = 0;
	$.ajax({
        type: "GET",
        url: "dashboardFormController/getCurrentUserCount",
        success: function(data) {
        	$("#userCountDynamicTable").html(data);
	  		$("#userCountDynamicTable").displayTagAjax();
	  		removeTableText('userCountDynamicTable');
	  		getDeviceCount();
        }
	});
}

function getDeviceCount(){
	$.ajax({
        type: "GET",
        url: "dashboardFormController/getDeviceCount",
        success: function(data) {
        	$('#mobileCount').text(data.responce.mobileCount);
        	$('#desktopCount').text(data.responce.desktopCount);
        	$('#fraudCount').text(data.responce.fraudCount);
        }
	});
}

function getUserDetailsBySessionId(sessionId,userId){
	var q =$('#addDetailsSearchId').val();
	var dv= "";
	q = '';
	var formdata = 'ajax=true&q='+q+'&sid='+sessionId;
	$.ajax({
        type: "GET",
        url: "dashboardFormController/getUserDetailsBySessionId",
        data: formdata,
        success: function(data) {
          $("#eventDetailsTableId").html(data);
       	  $("#eventDetailsTableId").displayTagAjax();
       	  
          $("#deviceName").text($("#"+sessionId).val());
          $("#userId").text("User :"+userId);
       	  getHeaderDetailsData(sessionId);
       	  getAnalyseData(sessionId);
        }
	});
}

function getAnalyseData(sessionId){
	var formdata = 'ajax=true&sid='+sessionId;
	$.ajax({
        type: "GET",
        url: "dashboardFormController/getAnalyseData",
        data: formdata,
        success: function(data) {
        	if(data.responce != null) {
        		eventAnalysis(data.responce.eventAnalyseMap);
        		deviceAnalysis(data.responce.deviceAnalyseMap);
        		userAnalysis(data.responce.userAnalyseMap);
        	}
        }
	});
}

function getHeaderDetailsData(sessionId){
	q = '';
	var formdata = 'ajax=true&sid='+sessionId;
	$.ajax({
        type: "GET",
        url: "dashboardFormController/getHeaderDetailsData",
        data: formdata,
        success: function(data) {
        	if(data.responce != null) {
        		var createdDate = '';
        		$("#totalEvents").text(data.responce.TOTAL_COUNT);
        		$("#numOfSessionTimeout").text(data.responce.NUM_OF_SESSION_TIMEOUT);
        		$("#createdDate").text(getDate(data.responce.FIRST_ACCESS_TIME));
        		$("#createdTime").text(getTime(data.responce.FIRST_ACCESS_TIME));
        		$("#lastAccessDate").text(getDate(data.responce.LAST_ACCESS_TIME));
        		$("#lastAccessTime").text(getTime(data.responce.LAST_ACCESS_TIME));
        		$("#userStayingTime").text(data.responce.USER_STAYING_TIME+" min");
        		$("#userIdleTime").text(data.responce.MAX_IDLE_TIME+" min");
        		$("#avgTime").text(data.responce.AVG_TIME_TWO_EVENT+" sec");
        	}
        }
	});
}

function getLocationDetails(bid) {
	var formdata = 'ajax=true&bid='+bid;
	$.ajax({
        type: "GET",
        url: "dashboardFormController/getProxyDetails",
        data: formdata,
        success: function(data) {
        	
        }
	});
}

var persantageCount = function(totalEvent, specificEvent){
	return  Math.round((specificEvent/totalEvent) *100);
}

function userAnalysis(data){
	var bowserIdPositive = data.BROWSER_ID.POSITIVE;
	var bowserIdNegeitive = data.BROWSER_ID.NEGETIVE;
	var OSNamePositive = data.OS_NAME.POSITIVE;
	var OSNameNegetive = data.OS_NAME.NEGETIVE;
	var proxyPositive = data.PROXY_COUNT.POSITIVE;
	var proxyNegetive = data.PROXY_COUNT.NEGETIVE;
	var timeZonePositive = data.TIME_ZONE.POSITIVE;
	var timeZoneNegetive = data.TIME_ZONE.NEGETIVE;
	var eventSequencePositive = data.EVENT_SEQUENCE.POSITIVE;
	var eventSequenceNegetive = data.EVENT_SEQUENCE.NEGETIVE;
	var userLocationPositive = data.USER_LOCATION.POSITIVE;
	var userLocationNegetive = data.USER_LOCATION.NEGETIVE;
	
	userAnalysisBarChart.setData(  [{ "y": "Browser ID", "a": bowserIdPositive, "b": bowserIdNegeitive },
	                                { "y": "Time Zone", "a": timeZonePositive, "b": timeZoneNegetive },
	                                { "y": "OS Name", "a": OSNamePositive, "b": proxyNegetive},
	                                { "y": "Event Sequ:", "a": eventSequencePositive, "b": eventSequenceNegetive},
	                                { "y": "Proxy Count", "a": proxyPositive, "b": proxyNegetive},
	                                { "y": "User Location", "a": userLocationPositive, "b": userLocationNegetive}]);
}
function deviceAnalysis(data){
	var userEventDesktop = data.deviceTypeByEvents.desktopDevice*100;
	var userEventMobile = data.deviceTypeByEvents.mobileDevice*100;
	var diamensionDesktop = data.deviceTypeByDimention.desktopDevice*100;
	var diamensionMobile = data.deviceTypeByDimention.mobileDevice*100;
	var viewDesktop = data.deviceTypeByOrientation.desktopDevice*100;
	var viewMobile = data.deviceTypeByOrientation.mobileDevice*100;
	
	deviceAnalysisBarChart.setData([{ "y": "by User Events", "a": userEventDesktop, "b": userEventMobile },
	                                { "y": "by Dimention", "a": diamensionDesktop, "b": diamensionMobile },
	                                { "y": "by Oriantation", "a": viewDesktop, "b": viewMobile}]);
}
function eventAnalysis(data){
	var total_Events_Count = data.TOTAL_COUNT;
	var total_RF_Count = data.RF_COUNT;
	var total_IMG_Count = data.TOTAL_IMG_COUNT;
	var total_IMG_LC_Count = data.IMG_COUNT_LC;
	var total_IMG_TS_Count = data.IMG_COUNT_TS;
	var total_IMG_TM_Count = data.IMG_COUNT_TM;
	var total_IMG_TZE_Count = data.IMG_COUNT_TZE;
	var total_IMG_STZE_Count = data.IMG_COUNT_STZE;
	var total_PARA_Count = data.TOTAL_P_COUNT;
	var total_KP_Count = data.TYPE_COUNT_KP;
	var total_BTN_Count = data.TOTAL_BTN_COUNT;
	var total_OPTION_Count = data.TOTAL_OPTION_COUNT;
	var total_SELECT_Count = data.TOTAL_SELECT_COUNT;
	var total_A_Count = data.TOTAL_A_COUNT;
	var total_INPUT_Count = data.TOTAL_INPT_COUNT;
	var total_SE_Count = data.SE_COUNT;
	var total_LC_Count = data.LC_COUNT;
	var total_DC_Count = data.DC_COUNT;
	var total_TS_Count = data.TS_COUNT;
	var total_TM_Count = data.TM_COUNT;
	var total_TZE_Count = data.TZE_COUNT;
	var total_STZE_Count = data.STZE_COUNT;
	
	
	var chartData = [{"title": "Refresh Count","Percentage": -persantageCount(total_Events_Count,total_RF_Count),"val-int": total_RF_Count},
	                 {"title": "LC Count","Percentage": -persantageCount(total_Events_Count,total_LC_Count),"val-int": total_LC_Count},
	                 {"title": "DC Count","Percentage": -persantageCount(total_Events_Count,total_DC_Count),"val-int": total_DC_Count},
	                 {"title": "TS Count","Percentage": -persantageCount(total_Events_Count,total_TS_Count),"val-int": total_TS_Count},
	                 {"title": "TM Count","Percentage": -persantageCount(total_Events_Count,total_TM_Count),"val-int": total_TM_Count},
	                 {"title": "SE Count","Percentage": -persantageCount(total_Events_Count,total_SE_Count),"val-int": total_SE_Count},
	                 {"title": "Touch Zoom Count","Percentage": -persantageCount(total_Events_Count,total_TZE_Count),"val-int": total_TZE_Count},
	                 {"title": "Swap Zoom Count","Percentage": -persantageCount(total_Events_Count,total_STZE_Count),"val-int": total_STZE_Count},
	                 {"title": "Total Image Count","Percentage": -persantageCount(total_Events_Count,total_IMG_Count),"val-int": total_IMG_Count},
	                 {"title": "LC Image Count","Percentage": -persantageCount(total_IMG_Count,total_IMG_LC_Count),"val-int": total_IMG_LC_Count},
	                 {"title": "TS Image Count","Percentage": -persantageCount(total_IMG_Count,total_IMG_TS_Count),"val-int": total_IMG_TS_Count},
	                 {"title": "TM Image Count","Percentage": -persantageCount(total_IMG_Count,total_IMG_TM_Count),"val-int": total_IMG_TM_Count},
	                 {"title": "Touch Zoom Image Count","Percentage": -persantageCount(total_IMG_Count,total_IMG_TZE_Count),"val-int": total_IMG_TZE_Count},
	                 {"title": "Swap Zoom Image Count","Percentage": -persantageCount(total_IMG_Count,total_IMG_STZE_Count),"val-int": total_IMG_STZE_Count},
	                 {"title": "Para Count","Percentage": -persantageCount(total_Events_Count,total_PARA_Count),"val-int": total_PARA_Count},
	                 {"title": "Input Count","Percentage": -persantageCount(total_Events_Count,total_INPUT_Count),"val-int": total_INPUT_Count},
	                 {"title": "Link Count","Percentage": -persantageCount(total_Events_Count,total_A_Count),"val-int": total_A_Count},
	                 {"title": "Select Count","Percentage": -persantageCount(total_Events_Count,total_SELECT_Count),"val-int": total_SELECT_Count},
	                 {"title": "Option Count","Percentage": -persantageCount(total_Events_Count,total_OPTION_Count),"val-int": total_OPTION_Count},
	                 {"title": "Button Count","Percentage": -persantageCount(total_Events_Count,total_BTN_Count),"val-int": total_BTN_Count},
	                 {"title": "Input Key Type Count","Percentage": -persantageCount(total_Events_Count,total_KP_Count),"val-int": total_KP_Count}];
	eventAnalysischart.dataProvider =chartData ;
	eventAnalysischart.validateData();
}

var deviceAnalysisBarChart =  Morris.Bar({
	  element: 'device-analysis-graph',
	  data: [
	        // {y:null}
	    { y: 'by User Events', a: 100, b: 90 },
	    { y: 'by Dimention', a: 75,  b: 65 },
	    { y: 'by Oriantation', a: 50,  b: 40 }
	  ],
	  xkey: 'y',
	  ykeys: ['a', 'b'],
	  labels: ['Desktop', 'Mobile'],
	  barColors:['#9b59b6','#3498db']
});

var userAnalysisBarChart = Morris.Bar({
	  element: 'user-analysis-graph',
	  data: [
	    { y: 'Browser ID', a: 100, b: 90 },
	    { y: 'Time Zone', a: 75,  b: 65 },
	    { y: 'OS Name', a: 50,  b: 40 },
	    { y: 'Event Sequ:', a: 50,  b: 40 },
	    { y: 'Proxy Count', a: 50,  b: 40 },
	    { y: 'User Location', a: 50,  b: 40 }
	  ],
	  xkey: 'y',
	  ykeys: ['a', 'b'],
	  labels: ['Positive', 'Negative'],
	  barColors:['#5CB85C','#D9534F'],
	  xLabelAngle:'45'
});



var eventAnalysischart = AmCharts.makeChart("chartdiv", {
  type: "serial",
  theme: "dark",
  rotate: true,
  marginBottom: 50,
  dataProvider: [{
    "title": "LC count",
    "Percentage": -0.1,
    "val-int": 20
  }, {
    "title": "TC Count",
    "Percentage": -20,
    "val-int": 16
  }, {
    "title": "TM Count",
    "Percentage": -3,
    "val-int": 20
  }, {
    "title": "Touch Zoom Count",
    "Percentage": -80,
    "val-int": 40
  }, {
    "title": "Swap Zoom Count",
    "Percentage": -10,
    "val-int": 50
  }, {
    "title": "Image Count",
    "Percentage": -40,
    "val-int": 30
  }, {
    "title": "LC Image Count",
    "Percentage": -20,
    "val-int": 10
  }, {
    "title": "TS Image Count",
    "Percentage": -20,
    "val-int": 50
  }, {
    "title": "TM Image Count",
    "Percentage": -40,
    "val-int": 40
  }, {
    "title": "Touch Zoom Image Count",
    "Percentage": -10,
    "val-int": 70
  }, {
    "title": "Swap Zoom Image Count",
    "Percentage": -10,
    "val-int": 40
  }, {
    "title": "P tag Count",
    "Percentage": -40,
    "val-int": 40
  }, {
    "title": "Input tag Count",
    "Percentage": -7,
    "val-int": 10
  }, {
    "title": "a tag Count",
    "Percentage": -15,
    "val-int": 40
  }, {
    "title": "Select tag Count",
    "Percentage": -0,
    "val-int": 0
  }, {
    "title": "Option tag Count",
    "Percentage": -0,
    "val-int": 0
  }, {
    "title": "Button tag Count",
    "Percentage": -20,
    "val-int": 20
  }, {
    "title": "Input kp Count",
    "Percentage": -0,
    "val-int": 0
  }, {
    "title": "SE Count",
    "Percentage": -0,
    "val-int": 0
  }],
  "startDuration": 1,
  "graphs": [{
    "fillAlphas": 0.8,
    "lineAlpha": 0.2,
    "type": "column",
    "valueField": "Percentage",
    "title": "Percentage",
    "labelText": "[[value]]",
    "clustered": false,
    "labelFunction": function(item) {
      return Math.abs(item.values.value);
    },
    "balloonFunction": function(item) {
      return item.category + ": " + Math.abs(item.values.value) + "%";
    }
  }, {
    "fillAlphas": 0.8,
    "lineAlpha": 0.2,
    "type": "column",
    "valueField": "val-int",
    "title": "val-int",
    "labelText": "[[value]]",
    "clustered": false,
    "labelFunction": function(item) {
      return Math.abs(item.values.value);
    },
    "balloonFunction": function(item) {
      return item.category + ": " + Math.abs(item.values.value) + "";
    }
  }],
  "categoryField": "title",
  "categoryAxis": {
    "gridPosition": "start",
    "gridAlpha": 0.2,
    "axisAlpha": 0
  },
  "valueAxes": [{
    "gridAlpha": 0,
    "ignoreAxisWidth": true,
    "labelFunction": function(value) {
      return Math.abs(value) + '';
    },
    "guides": [{
      "value": 0,
      "lineAlpha": 0.2
    }]
  }],
  "balloon": {
    "fixedPosition": true
  },
  "chartCursor": {
    "valueBalloonsEnabled": false,
    "cursorAlpha": 0.05,
    "fullWidth": true
  },
  "allLabels": [{
    "text": "Percentage",
    "x": "28%",
    "y": "97%",
    "bold": true,
    "align": "middle"
  }, {
    "text": "val-int",
    "x": "75%",
    "y": "97%",
    "bold": true,
    "align": "middle"
  }],
 "export": {
    "enabled": true
  }   

});

function getDate(val){
	return val.substring(0, val.indexOf(" "));
}
function getTime(val){
	return val.substring(val.indexOf(" ")+1);
}