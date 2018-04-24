package com.piesat.project.common.log.service.impl;


import com.piesat.project.common.log.bean.LogBean;
import com.piesat.project.common.log.service.LogService;

import org.springframework.stereotype.Service;

@Service("logService")
public class LogServiceImpl  implements LogService {
	

//	@Autowired
//	private LogDao logDao;
	
	@Override
	public int createLog(LogBean log) {
		//return this.logDao.insertSelective(log);
		System.out.println("模拟日志入库"+log);
		return 1;
	}
	
	@Override
	public int updateLog(LogBean log) {
		//return this.logDao.updateByPrimaryKeySelective(log);
		System.out.println("模拟日志更新"+log);
		return 1;
	}

}
