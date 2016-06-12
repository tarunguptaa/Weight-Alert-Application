package com.egen.egen_be_challenge.utilities;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.egen.egen_be_challenge.easyrules.EasyRule1;
import com.egen.egen_be_challenge.easyrules.EasyRule2;

@Configuration
public class BeanDefinition {

	@Bean
	public RulesEngine RulesEngine() {
		return RulesEngineBuilder.aNewRulesEngine().withSkipOnFirstAppliedRule(true).build();
	}

	@Bean
	public EasyRule1 EasyRule1() {
		return new EasyRule1();
	}

	@Bean
	public EasyRule2 EasyRule2() {
		return new EasyRule2();
	}

}
