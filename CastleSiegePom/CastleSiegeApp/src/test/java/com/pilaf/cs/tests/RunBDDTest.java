package com.pilaf.cs.tests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty", "html:target/results" }, strict = false, glue = {
		"com.pilaf.cs.tests.steps" }, features = { "classpath:scenario/userModuleScenarios.feature",
				"classpath:scenario/websocketSecurityScenarios.feature",
				"classpath:scenario/gameSearchScenarios.feature", "classpath:scenario/gameStateCache.feature" })
public class RunBDDTest {
}
