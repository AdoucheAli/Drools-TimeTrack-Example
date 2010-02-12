package com.jboss.example;

import org.drools.RuleBase;
import org.drools.agent.*;



public class MyHarness {

	public static void main(String[] args) {
		RuleAgent agent = RuleAgent.newRuleAgent("/mortgageapproval.properties");
		RuleBase rb = agent.getRuleBase();

		
		
	}
	
}
