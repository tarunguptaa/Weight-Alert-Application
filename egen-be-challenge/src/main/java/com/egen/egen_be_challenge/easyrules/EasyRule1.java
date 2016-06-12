package com.egen.egen_be_challenge.easyrules;

import org.apache.log4j.Logger;
import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

import com.egen.egen_be_challenge.utilities.MorphiaMongo;
import com.egen.egen_be_challenge.utilities.Sensor;

@Rule(name = "Lower Bound Rule", description = "This rule will save alert if the person weight shoots by 10% of base weight.")
public class EasyRule1 {

	private static Logger LOGGER = Logger.getLogger(EasyRule1.class);

	private int baseValue;
	private Sensor sensor;

	public EasyRule1() {
	}

	public EasyRule1(int baseValue, Sensor sensor) {
		this.baseValue = baseValue;
		this.sensor = sensor;
	}

	@Condition
	public boolean when() {
		// check if the person weight drops by 10%
		return sensor.getValue() < (0.9 * baseValue);
	}

	@Action
	public void then() throws Exception {
		MorphiaMongo morphiaMongo = new MorphiaMongo();
		try {
			new MorphiaMongo().createAlert(sensor.getTimeStamp(),
					"The weight of the person drops below 10% of his base weight");
		} catch (Exception exception) {
			LOGGER.error(exception.getMessage());
		} finally {
			morphiaMongo.closeMongoClient();
		}
	}

	public int getBaseValue() {
		return baseValue;
	}

	public void setBaseValue(int baseValue) {
		this.baseValue = baseValue;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}