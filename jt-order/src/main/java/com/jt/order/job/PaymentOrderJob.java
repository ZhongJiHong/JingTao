package com.jt.order.job;

import java.util.Date;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.jt.order.mapper.OrderMapper;

public class PaymentOrderJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap()
				.get("applicationContext");

		OrderMapper orderMapper = applicationContext.getBean(OrderMapper.class);

		// 时间工具类
		Date moment = new DateTime().minusDays(2).toDate();

		orderMapper.updateOrderStatusOutOfTime(moment);
		System.out.println("OutOfTime");
	}

}
