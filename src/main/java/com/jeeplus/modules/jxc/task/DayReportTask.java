package com.jeeplus.modules.jxc.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jeeplus.common.utils.IdWorker;
import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.jxc.entity.Financial;
import com.jeeplus.modules.jxc.service.FinancialService;
import com.jeeplus.modules.monitor.entity.Task;
import com.jeeplus.modules.sys.entity.Log;
import com.jeeplus.modules.sys.service.LogService;

public class DayReportTask extends Task{
	
	@Override
	public void run() {
		FinancialService financialService = SpringContextHolder.getBean(FinancialService.class);
		LogService logService = SpringContextHolder.getBean(LogService.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		Log log = new Log();
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		Date  beginDate = calendar.getTime();
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date endDate = calendar.getTime();
		Financial financial = financialService.queryFinancialByTime(beginDate, endDate);
		// 日报
		financial.setId("BB" + IdWorker.getId());
		financial.setType(1);
		financial.setCreateDate(beginDate);
		financial.setUpdateDate(endDate);
		financial.setRemarks("由系统生成");
		financialService.insert(financial);
		log.setTitle(dateFormat.format(beginDate) + " - " + dateFormat.format(endDate) + " 已经生成");
		logService.save(log);
	}
}
