package com.piesat.project.common.log.service;


import com.piesat.project.common.log.bean.LogBean;

public interface LogService {
	//增删改
	public int createLog(LogBean log);
	public int updateLog(LogBean log);
	
	

	
}
