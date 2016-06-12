package com.egen.egen_be_challenge.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.egen.egen_be_challenge.entities.Metrics;
import com.egen.egen_be_challenge.utilities.Sensor;

public class MetricControllerTest {

	static MetricController metricController = null;
	static Sensor SENSOR_1 = null;
	static Sensor SENSOR_2 = null;
	static Sensor SENSOR_3 = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		metricController = new MetricController();
		SENSOR_1 = getSensorObject(Long.parseLong("1465692865645"), 235);
		SENSOR_2 = getSensorObject(Long.parseLong("1465692870696"), 250);
		SENSOR_3 = getSensorObject(Long.parseLong("1465692875741"), 265);
	}

	private static Sensor getSensorObject(Long timeStamp, int value) {
		Sensor sensor = new Sensor();
		sensor.setTimeStamp(timeStamp);
		sensor.setValue(value);
		return sensor;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// TODO: Delete the  entries made in before class 
		metricController = null;
		SENSOR_1 = null;
		SENSOR_2 = null;
		SENSOR_3 = null;
	}

	@Test
	public void testCreate() {
		metricController.create(SENSOR_1);
		metricController.create(SENSOR_2);
		metricController.create(SENSOR_3);
	}

	@Test
	public void testRead() {
		List<Metrics> metrics = metricController.read();
		assertNotNull(metrics);
		assertTrue(!metrics.isEmpty());
	}

	@Test
	public void testReadByTimeStamp() {
		List<Metrics> metrics = metricController.read(Long.parseLong("1465692865645"), Long.parseLong("1465692875741"));
		assertNotNull(metrics);
		assertTrue(!metrics.isEmpty());
	}
}