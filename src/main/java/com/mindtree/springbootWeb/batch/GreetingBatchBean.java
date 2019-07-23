package com.mindtree.springbootWeb.batch;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mindtree.springbootWeb.model.Greeting;
import com.mindtree.springbootWeb.service.GreetingService;

@Component
public class GreetingBatchBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GreetingService greetingService;
	
	// Cron Job
	// @Scheduled(cron = "0,30 * * * * *")
	public void cronJob() {
		logger.info(">Cron Job");

		// Add Logic here
		Collection<Greeting> greetings = greetingService.findAll();
		logger.info("There are {} greetings in the data base", greetings.size());

		logger.info("<Cron Job");
	}

	// Fixed Rate Scheduling
	/*
	 * This configures a fixed rate process with a start delay after the application starts up
	 * It invokes process after a fixed time even if the previous process has not ended
	 * It means that next job is scheduled after(10s of) the initialization of the first job
	 */

	/*
	 * Sample output
	 * 2019-07-23 11:58:46.132  INFO 9352 --- [           main] c.m.s.SpringbootWebApplication           : Started SpringbootWebApplication in 5.968 seconds (JVM running for 7.308)
	 * 2019-07-23 11:58:51.091  INFO 9352 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : >FixedRateDelay
	 * 2019-07-23 11:58:56.095  INFO 9352 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : Processing time was 5 seconds
	 * 2019-07-23 11:58:56.095  INFO 9352 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : >FixedRateDelay
	 * 2019-07-23 11:59:01.092  INFO 9352 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : >FixedRateDelay
	 * 2019-07-23 11:59:06.094  INFO 9352 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : Processing time was 5 seconds
	 * 2019-07-23 11:59:06.094  INFO 9352 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : >FixedRateDelay
	 */
	// @Scheduled(initialDelay = 5000, fixedRate = 10000)
	public void FixedRateJob() {
		logger.info(">FixedRateJob");

		// Add Logic here
		// Simulating a job processing time
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if (start + pause < System.currentTimeMillis()) {
				break;
			}
		} while (true);
		logger.info("Processing time was {} seconds", pause / 1000);

		logger.info("<FixedRateJob");
	}

	// Fixed Delay Scheduling
	/*
	 * This configures a fixed delay process with a start delay after the application starts up
	 * It invokes process after a fixed time even after the previous process has ended
	 * It means that next job is scheduled after(10s of) the completion of the first job
	 */

	/*
	 * 2019-07-23 12:25:08.236  INFO 7696 --- [           main] c.m.s.SpringbootWebApplication           : Started SpringbootWebApplication in 4.052 seconds (JVM running for 5.25)
	 * 2019-07-23 12:25:13.205  INFO 7696 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : >FixedDelayJob
	 * 2019-07-23 12:25:18.217  INFO 7696 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : Processing time was 5 seconds
	 * 2019-07-23 12:25:18.217  INFO 7696 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : <FixedDelayJob
	 * 2019-07-23 12:25:28.220  INFO 7696 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : >FixedDelayJob
	 * 2019-07-23 12:25:33.222  INFO 7696 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : Processing time was 5 seconds
	 * 2019-07-23 12:25:33.222  INFO 7696 --- [   scheduling-1] c.m.s.batch.GreetingBatchBean            : <FixedDelayJob
	 */
	
	//@Scheduled(initialDelay = 5000, fixedDelay = 10000)
	public void FixedDelayjob() {
		logger.info(">FixedDelayJob");

		// Add Logic here
		// Simulating a job processing time
		long pause = 5000;
		long start = System.currentTimeMillis();
		do {
			if (start + pause < System.currentTimeMillis()) {
				break;
			}
		} while (true);
		logger.info("Processing time was {} seconds", pause / 1000);

		logger.info("<FixedDelayJob");
	}
}
