package com.pilaf.cs.tests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/results" }, strict = false, glue = {
		"com.pilaf.cs.tests.steps" }, features = { "classpath:scenario/userModule.feature",
				"classpath:scenario/websocketSecurity.feature", "classpath:scenario/gameBasicScenario.feature" })
public class RunBDDTest {
}
