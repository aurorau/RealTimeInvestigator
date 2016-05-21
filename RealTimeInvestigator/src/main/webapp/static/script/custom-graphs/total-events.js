var chart = AmCharts.makeChart("chartdiv", {
  "type": "serial",
  "theme": "dark",
  "rotate": true,
  "marginBottom": 50,
  "dataProvider": [{
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
  }
      ],
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