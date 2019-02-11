package com.jeeplus.modules.jxc.task;

import com.jeeplus.modules.monitor.entity.Task;

public class MonthReportTask extends Task{
	
	@Override
	public void run() {
//		FinancialService financialService = SpringContextHolder.getBean(FinancialService.class);
//		LogService logService = SpringContextHolder.getBean(LogService.class);
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Calendar calendar = Calendar.getInstance();
//		Log log = new Log();
//		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
//		calendar.set(Calendar.DATE, 1);
//		calendar.set(Calendar.HOUR, 0);
//		calendar.set(Calendar.MINUTE, 0);
//		calendar.set(Calendar.SECOND, 0);
//		calendar.set(Calendar.MILLISECOND, 0);
//		Date  beginDate = calendar.getTime();
//		int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//		calendar.set(Calendar.DAY_OF_MONTH, lastDay);
//		calendar.set(Calendar.HOUR, 23);
//		calendar.set(Calendar.MINUTE, 59);
//		calendar.set(Calendar.SECOND, 59);
//		Date endDate = calendar.getTime();
//		Financial financial = financialService.queryFinancialByTime(beginDate, endDate);
//		// 月报
//		financial.setId("BB" + IdWorker.getId());
//		financial.setType(2);
//		financial.setCreateDate(beginDate);
//		financial.setUpdateDate(endDate);
//		financial.setRemarks("由系统生成");
//		financialService.insert(financial);
//		log.setTitle(dateFormat.format(beginDate) + " - " + dateFormat.format(endDate) + " 已经生成");
//		logService.save(log);
	}
}
