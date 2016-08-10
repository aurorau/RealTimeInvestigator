package com.aurora.rti.service;

import java.util.List;

import com.aurora.rti.util.UserDetailsDTO;

public interface HorizontalAnalysisService {

	List<UserDetailsDTO> horizontalAnalysis(List<UserDetailsDTO> list);
	String commonValidation(UserDetailsDTO dto);

}
