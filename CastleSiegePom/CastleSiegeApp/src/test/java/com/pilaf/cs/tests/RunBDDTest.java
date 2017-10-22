package com.pilaf.cs.tests;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"com.pilaf.cs.tests.steps"}, features = "classpath:scenario/userModule.feature")
public class RunBDDTest {
}
