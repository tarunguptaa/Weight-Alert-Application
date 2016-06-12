package com.egen.egen_be_challenge.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.easyrules.api.RulesEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.egen.egen_be_challenge.easyrules.EasyRule1;
import com.egen.egen_be_challenge.easyrules.EasyRule2;
import com.egen.egen_be_challenge.entities.Metrics;
import com.egen.egen_be_challenge.utilities.MorphiaMongo;
import com.egen.egen_be_challenge.utilities.Sensor;

@RestController
@RequestMapping("/metric")
public class MetricController {
	private static Logger LOGGER = Logger.getLogger(MetricController.class);

	@Autowired
	private ApplicationContext applicationContext;

	@RequestMapping(method = RequestMethod.POST, value = "/create", produces = "application/json")
	public void create(@RequestBody final Sensor sensor) {
		MorphiaMongo morphiaMongo = new MorphiaMongo();
		try {
			morphiaMongo.createMetrics(sensor.getTimeStamp(), sensor.getValue());

			RulesEngine rulesEngine = applicationContext.getBean(RulesEngine.class);

			int baseValue = morphiaMongo.getBaseValue();

			EasyRule1 easyRule1 = applicationContext.getBean(EasyRule1.class);
			easyRule1.setBaseValue(baseValue);
			easyRule1.setSensor(sensor);

			EasyRule2 easyRule2 = applicationContext.getBean(EasyRule2.class);
			easyRule2.setBaseValue(baseValue);
			easyRule2.setSensor(sensor);

			rulesEngine.registerRule(easyRule1);
			rulesEngine.registerRule(easyRule2);
			rulesEngine.fireRules();
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		} finally {
			morphiaMongo.closeMongoClient();
			if (applicationContext != null) {
				((AbstractApplicationContext) applicationContext).registerShutdownHook();
			}
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/read", produces = "application/json")
	public List<Metrics> read() {
		MorphiaMongo morphiaMongo = new MorphiaMongo();
		List<Metrics> metrics = null;
		try {
			metrics = morphiaMongo.readMetrics();
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		} finally {
			morphiaMongo.closeMongoClient();
		}
		return metrics;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/readByTimeRange/{startTimeStamp}/{endTimeStamp}", produces = "application/json")
	public List<Metrics> read(@PathVariable long startTimeStamp, @PathVariable long endTimeStamp) {
		MorphiaMongo morphiaMongo = new MorphiaMongo();
		List<Metrics> metrics = null;
		try {
			metrics = morphiaMongo.readMetrics(startTimeStamp, endTimeStamp);
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		} finally {
			morphiaMongo.closeMongoClient();
		}
		return metrics;
	}
}
