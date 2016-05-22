package com.aurora.rti.service;

import java.util.List;
import java.util.Map;

import com.aurora.rti.util.UserDetailsDTO;

public interface AnalyseDeviceService {

	Map<String, Object> deviceIdenticication(List<UserDetailsDTO> list);

}
