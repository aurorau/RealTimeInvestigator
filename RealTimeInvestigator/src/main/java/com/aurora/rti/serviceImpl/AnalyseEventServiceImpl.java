package com.aurora.rti.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aurora.rti.dao.SessionDetailsDao;
import com.aurora.rti.model.SessionDetails;
import com.aurora.rti.service.AnalyseEventService;
import com.aurora.rti.service.CommonService;
import com.aurora.rti.util.UserDetailsDTO;

@Service("analyseEventService")
public class AnalyseEventServiceImpl implements AnalyseEventService {

	private static final Logger logger = Logger.getLogger(AnalyseEventServiceImpl.class);
	private CommonService commonService = null;
	private SessionDetailsDao sessionDetailsDao = null;
	
	@Autowired
	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
	
	@Autowired
	public void setSessionDetailsDao(SessionDetailsDao sessionDetailsDao) {
		this.sessionDetailsDao = sessionDetailsDao;
	}

	@Transactional
	public Map<String, Object> getHeaderDetailsData(List<UserDetailsDTO> dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int totalCount = dto.size();
			long timeDiffer = commonService.getTimeDifference(dto.get(0).getZoneDateTime(), dto.get(dto.size()-1).getZoneDateTime()); // staying time
			int userStayingTime = (int) (timeDiffer/60000);
			int avgTimeTwoEvent = 0;
			long timeDiffer1 = 0;
			long maxIdleTime = 0;
			String lastAccessTime = null;
			String firstAccessTime = null;
			int numOfSessionTimeOut = 0;
			int sessionTimeOutCount = 0;
			
			for(int x = 0; x<dto.size(); x++) { //getting time difference each consecutive events
				if((x+1) <dto.size()){
					long mxTD = commonService.getTimeDifference(dto.get(x).getZoneDateTime(),dto.get(x+1).getZoneDateTime());
					timeDiffer1 += mxTD;
					
					if(mxTD > maxIdleTime) {
						maxIdleTime = mxTD;
					}
				}
			}
			SessionDetails sessionDetails = sessionDetailsDao.getById(dto.get(0).getSid());
			sessionTimeOutCount = sessionDetailsDao.getSessionTimeOutCountBySessionId(sessionDetails.getSessionId());
			
			numOfSessionTimeOut = sessionTimeOutCount;
			lastAccessTime = dto.get(0).getZoneDateTime();
			firstAccessTime = dto.get(dto.size()-1).getZoneDateTime();
			avgTimeTwoEvent = (int)(timeDiffer1/totalCount)/1000; 
			
			map.put("MAX_IDLE_TIME", (int)(maxIdleTime/60000));
			map.put("TOTAL_COUNT", totalCount);
			map.put("USER_STAYING_TIME", userStayingTime);
			map.put("AVG_TIME_TWO_EVENT", avgTimeTwoEvent);
			map.put("LAST_ACCESS_TIME", lastAccessTime);
			map.put("FIRST_ACCESS_TIME", firstAccessTime);
			map.put("NUM_OF_SESSION_TIMEOUT", numOfSessionTimeOut);
		} catch (Exception e){
			logger.error("+++++++++ Error in getHeaderDetailsData in AnalyseEventServiceImpl :"+e);
		}
		return map;
	}
	
	public Map<String, Integer> getEventAnalyseData(List<UserDetailsDTO> dto) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		int TOTAL_COUNT = dto.size();
		int RC_COUNT = 0;
		int LC_COUNT = 0;
		int DC_COUNT = 0;
		int KP_COUNT = 0;
		int TS_COUNT = 0 ;
		int TM_COUNT = 0;
		int TZE_COUNT = 0;
		int STZE_COUNT = 0;
		int RF_COUNT = 0;
		int SE_COUNT = 0;
		int P_COUNT_TM = 0;
		int P_COUNT_TS = 0;
		int P_COUNT_TZE = 0;
		int P_COUNT_STZE = 0;
		int P_COUNT_LC = 0;
		int IMG_COUNT_TM = 0;
		int IMG_COUNT_TS = 0;
		int IMG_COUNT_TZE = 0;
		int IMG_COUNT_STZE = 0;
		int IMG_COUNT_LC = 0;
		int TOTAL_IMG_COUNT = 0;
		int TOTAL_P_COUNT = 0;
		int INPT_COUNT_TS = 0;
		int INPT_COUNT_LC = 0;
		int A_COUNT_TS = 0;
		int A_COUNT_LC = 0;
		int BTN_COUNT_TS = 0;
		int BTN_COUNT_LC = 0;
		int SELECT_COUNT_TS = 0;
		int SELECT_COUNT_LC = 0;
		int OPTION_COUNT_TS = 0;
		int OPTION_COUNT_LC = 0;
		int TOTAL_INPT_COUNT = 0;
		int TOTAL_A_COUNT = 0;
		int TOTAL_BTN_COUNT = 0;
		int TOTAL_SELECT_COUNT = 0;
		int TOTAL_OPTION_COUNT = 0;
		int TYPE_COUNT_KP = 0;
		int SE_COUNT_BY_KP = 0;
		int TAP_COUNT = 0;
		
		for(UserDetailsDTO dt : dto){
			if(Integer.parseInt(dt.getNumOfTaps()) > 0){
				TAP_COUNT += 1;
			}
			if(dt.getEventName().equalsIgnoreCase("LC")){
				LC_COUNT += 1;
				Map<String,Integer> mapLC = getTagCountForEvent(dt);
				P_COUNT_LC += mapLC.get("pCount");
				IMG_COUNT_LC += mapLC.get("imgCount");
				INPT_COUNT_LC += mapLC.get("inputCount");
				A_COUNT_LC += mapLC.get("anchorCount");
				BTN_COUNT_LC += mapLC.get("buttonCount");
				SELECT_COUNT_LC += mapLC.get("selectCount");
				OPTION_COUNT_LC += mapLC.get("optionCount");
				
			} else if(dt.getEventName().equalsIgnoreCase("RC")) {
				RC_COUNT += 1;
			} else if(dt.getEventName().equalsIgnoreCase("DC")) {
				DC_COUNT += 1;
			} else if(dt.getEventName().equalsIgnoreCase("KP")) {
				KP_COUNT += 1;
				if(dt.getTagName().equalsIgnoreCase("INPUT") || dt.getTagName().equalsIgnoreCase("TEXTAREA")){
					TYPE_COUNT_KP += 1;
				} else if(dt.getTagName().equalsIgnoreCase("BODY")){
					SE_COUNT_BY_KP += 1;
				}
			} else if(dt.getEventName().equalsIgnoreCase("TS")) {
				TS_COUNT += 1;
				Map<String,Integer> mapTS = getTagCountForEvent(dt);
				P_COUNT_TS += mapTS.get("pCount");
				IMG_COUNT_TS += mapTS.get("imgCount");
				INPT_COUNT_TS += mapTS.get("inputCount");
				A_COUNT_TS += mapTS.get("anchorCount");
				BTN_COUNT_TS += mapTS.get("buttonCount");
				SELECT_COUNT_TS += mapTS.get("selectCount");
				OPTION_COUNT_TS += mapTS.get("optionCount");
				
			} else if(dt.getEventName().equalsIgnoreCase("TM")) {
				TM_COUNT += 1;
				Map<String,Integer> mapTM = getTagCountForEvent(dt);
				P_COUNT_TM += mapTM.get("pCount");
				IMG_COUNT_TM += mapTM.get("imgCount");
			} else if(dt.getEventName().equalsIgnoreCase("TZE")) {
				TZE_COUNT += 1;
				Map<String,Integer> mapTZE = getTagCountForEvent(dt);
				P_COUNT_TZE += mapTZE.get("pCount");
				IMG_COUNT_TZE += mapTZE.get("imgCount");
			} else if(dt.getEventName().equalsIgnoreCase("STZE")) {
				STZE_COUNT += 1;
				Map<String,Integer> mapSTZE = getTagCountForEvent(dt);
				P_COUNT_STZE += mapSTZE.get("pCount");
				IMG_COUNT_STZE += mapSTZE.get("imgCount");
			} else if(dt.getEventName().equalsIgnoreCase("RF")) {
				RF_COUNT += 1;
			} else if(dt.getEventName().equalsIgnoreCase("SE")) {
				SE_COUNT += 1;
			}
		}
		
		TOTAL_IMG_COUNT = IMG_COUNT_TM + IMG_COUNT_TS + IMG_COUNT_TZE + IMG_COUNT_STZE + IMG_COUNT_STZE + IMG_COUNT_LC;
		TOTAL_P_COUNT = P_COUNT_TM + P_COUNT_TS + P_COUNT_TZE + P_COUNT_STZE + P_COUNT_LC;
		TOTAL_INPT_COUNT = INPT_COUNT_TS + INPT_COUNT_LC;
		TOTAL_A_COUNT = A_COUNT_TS + A_COUNT_LC;
		TOTAL_BTN_COUNT = BTN_COUNT_TS + BTN_COUNT_LC;
		TOTAL_SELECT_COUNT = SELECT_COUNT_TS + SELECT_COUNT_LC;
		TOTAL_OPTION_COUNT = OPTION_COUNT_TS + OPTION_COUNT_LC;
		
		map.put("RC_COUNT", RC_COUNT);
		map.put("LC_COUNT", LC_COUNT);
		map.put("DC_COUNT", DC_COUNT);
		map.put("KP_COUNT", KP_COUNT);
		map.put("TS_COUNT", TS_COUNT);
		map.put("TM_COUNT", TM_COUNT);
		map.put("TZE_COUNT", TZE_COUNT);
		map.put("STZE_COUNT", STZE_COUNT);
		map.put("RF_COUNT", RF_COUNT);
		map.put("SE_COUNT", SE_COUNT);
		map.put("P_COUNT_TM", P_COUNT_TM);
		map.put("P_COUNT_TS", P_COUNT_TS);
		map.put("P_COUNT_TZE", P_COUNT_TZE);
		map.put("P_COUNT_STZE", P_COUNT_STZE);
		map.put("P_COUNT_LC", P_COUNT_LC);
		map.put("IMG_COUNT_TS", IMG_COUNT_TS);
		map.put("IMG_COUNT_TM", IMG_COUNT_TM);
		map.put("IMG_COUNT_TZE", IMG_COUNT_TZE);
		map.put("IMG_COUNT_STZE", IMG_COUNT_STZE);
		map.put("IMG_COUNT_LC", IMG_COUNT_LC);
		map.put("TOTAL_IMG_COUNT", TOTAL_IMG_COUNT);
		map.put("TOTAL_P_COUNT", TOTAL_P_COUNT);	
		map.put("INPT_COUNT_TS", INPT_COUNT_TS);
		map.put("A_COUNT_TS", A_COUNT_TS);
		map.put("BTN_COUNT_TS", BTN_COUNT_TS);
		map.put("SELECT_COUNT_TS", SELECT_COUNT_TS);
		map.put("OPTION_COUNT_TS", OPTION_COUNT_TS);
		map.put("INPT_COUNT_LC", INPT_COUNT_LC);
		map.put("A_COUNT_LC", A_COUNT_LC);
		map.put("BTN_COUNT_LC", BTN_COUNT_LC);
		map.put("SELECT_COUNT_LC", SELECT_COUNT_LC);
		map.put("OPTION_COUNT_LC", OPTION_COUNT_LC);
		map.put("TOTAL_INPT_COUNT", TOTAL_INPT_COUNT);
		map.put("TOTAL_A_COUNT", TOTAL_A_COUNT);
		map.put("TOTAL_BTN_COUNT", TOTAL_BTN_COUNT);
		map.put("TOTAL_SELECT_COUNT", TOTAL_SELECT_COUNT);
		map.put("TOTAL_OPTION_COUNT", TOTAL_OPTION_COUNT);
		map.put("TYPE_COUNT_KP", TYPE_COUNT_KP);
		map.put("SE_COUNT_BY_KP", SE_COUNT_BY_KP);
		map.put("TAP_COUNT", TAP_COUNT);
		map.put("TOTAL_COUNT", TOTAL_COUNT);
		
		return map;
	}
	public Map<String, Integer> getTagCountForEvent(UserDetailsDTO dt){
		
		Map<String, Integer> map =  new HashMap<String, Integer>();
		
		int pCount = 0;
		int imgCount = 0;
		int inputCount = 0;
		int selectCount = 0;
		int anchorCount = 0;
		int buttonCount = 0;
		int optionCount = 0;
		
		if(dt.getTagName().equalsIgnoreCase("P")){
			pCount += 1;
		} else if(dt.getTagName().equalsIgnoreCase("IMG")){
			imgCount += 1;
		} else if(dt.getTagName().equalsIgnoreCase("INPUT")){
			inputCount += 1;
		} else if(dt.getTagName().equalsIgnoreCase("SELECT")){
			selectCount += 1;
		} else if(dt.getTagName().equalsIgnoreCase("A")){
			anchorCount += 1;
		} else if(dt.getTagName().equalsIgnoreCase("BUTTON")){
			buttonCount += 1;
		} else if(dt.getTagName().equalsIgnoreCase("OPTION")){
			optionCount += 1;
		}
		
		map.put("pCount", pCount);
		map.put("imgCount", imgCount);
		map.put("inputCount", inputCount);
		map.put("selectCount", selectCount);
		map.put("anchorCount", anchorCount);
		map.put("buttonCount", buttonCount);
		map.put("optionCount", optionCount);
		
		return map;
	}
}
