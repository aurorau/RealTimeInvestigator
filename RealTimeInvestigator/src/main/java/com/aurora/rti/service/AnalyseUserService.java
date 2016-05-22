package com.aurora.rti.service;

import java.util.List;
import java.util.Map;

import com.aurora.rti.util.UserDetailsDTO;

public interface AnalyseUserService {

	Map<String, Map<String, Integer>> getUserAnalyseData(List<UserDetailsDTO> list);

}
