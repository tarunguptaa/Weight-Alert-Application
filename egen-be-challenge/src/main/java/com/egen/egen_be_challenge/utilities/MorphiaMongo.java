package com.egen.egen_be_challenge.utilities;

import java.net.UnknownHostException;
import java.util.List;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.egen.egen_be_challenge.daoimpl.AlertsDAOImpl;
import com.egen.egen_be_challenge.daoimpl.MetricsDAOImpl;
import com.egen.egen_be_challenge.entities.Alerts;
import com.egen.egen_be_challenge.entities.Metrics;
import com.mongodb.MongoClient;

public class MorphiaMongo {

	private MongoClient mongoClient = null;

	public MorphiaMongo() {
		establishClient();
	}

	public void createAlert(long timeStamp, String alert) {
		AlertsDAOImpl alertsDAOImpl = new AlertsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		alertsDAOImpl.save(new Alerts(timeStamp, alert));
	}

	public void createMetrics(long timeStamp, int value) {
		MetricsDAOImpl metricsDAOImpl = new MetricsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		metricsDAOImpl.save(new Metrics(timeStamp, value));
	}

	public List<Alerts> readAlerts() {
		AlertsDAOImpl alertsDAOImpl = new AlertsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		return alertsDAOImpl.find().asList();
	}

	public List<Alerts> readAlerts(long startTimeStamp, long endTimeStamp) {
		AlertsDAOImpl alertsDAOImpl = new AlertsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		Query<Alerts> query = createQuery(Alerts.class).filter("timeStamp >", startTimeStamp).filter("timeStamp <",
				endTimeStamp);
		return alertsDAOImpl.find(query).asList();
	}

	public List<Metrics> readMetrics() {
		MetricsDAOImpl metricsDAOImpl = new MetricsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		return metricsDAOImpl.find().asList();
	}

	public List<Metrics> readMetrics(long startTimeStamp, long endTimeStamp) {
		MetricsDAOImpl metricsDAOImpl = new MetricsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		Query<Metrics> query = createQuery(Metrics.class).filter("timeStamp >", startTimeStamp).filter("timeStamp <",
				endTimeStamp);
		return metricsDAOImpl.find(query).asList();
	}

	private <T> Query<T> createQuery(Class<T> collection) {
		return getMorphia().createDatastore(mongoClient, "egen-challenge").createQuery(collection);
	}

	public int getBaseValue() {
		establishClient();
		MetricsDAOImpl metricsDAOImpl = new MetricsDAOImpl(mongoClient, getMorphia(), "egen-challenge");
		Query<Metrics> query = createQuery(Metrics.class).order("timeStamp").limit(1);
		List<Metrics> metrics = metricsDAOImpl.find(query).asList();
		if (!metrics.isEmpty()) {
			return metricsDAOImpl.find(query).asList().get(0).getValue();
		}
		return 0;
	}

	public void cleanDatabase() {
		establishClient();
		mongoClient.dropDatabase("egen-challenge");
	}

	public Morphia getMorphia() {
		Morphia morphia = new Morphia();
		morphia.createDatastore(mongoClient, "egen-challenge");
		return morphia;
	}

	private void establishClient() {
		if (null == mongoClient) {
			try {
				mongoClient = new MongoClient("localhost", 27017);
			} catch (UnknownHostException e) {
				System.out.println("Failed at establishing mongo client.Host Not Available:" + e.getMessage());
			}
		}
	}
	public  void closeMongoClient(){
		if (null != mongoClient) {
			mongoClient.close();
		}
	}
}
