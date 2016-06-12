package com.egen.egen_be_challenge.controllers;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.egen.egen_be_challenge.entities.Alerts;
import com.egen.egen_be_challenge.utilities.MorphiaMongo;

public class AlertControllerTest {

	static AlertController alertController = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		alertController = new AlertController();
		MorphiaMongo morphiaMongo = new MorphiaMongo();
		morphiaMongo.createAlert(1465692744230L, "This is alert 1");
		morphiaMongo.createAlert(1465692749282L, "This is alert 2");
		morphiaMongo.createAlert(1465692754332L, "This is alert 3");
		morphiaMongo.closeMongoClient();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		// TODO: Delete the entries made in before class
		alertController = null;
	}

	@Test
	public void testRead() {
		List<Alerts> alerts = alertController.read();
		assertNotNull(alerts);
		assertTrue(!alerts.isEmpty());
	}

	@Test
	public void testReadByTimeStamp() {
		List<Alerts> alerts = alertController.read(Long.parseLong("1465692744230"), Long.parseLong("1465692754332"));
		assertNotNull(alerts);
		assertTrue(!alerts.isEmpty());
	}
}