package com.pilaf.cs.tests.steps;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import com.pilaf.cs.tests.builder.WebSocketSecurityTest;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class WebSocketSecuritySteps extends AbstractCSTestCase {

	@Given("^I am a user that is not loged in -websocketSecurity$")
	public void i_am_a_user_that_is_not_loged_in_websocketSecurity() throws Throwable {
		WebSocketSecurityTest.reset();
	}

	@Then("^I should get the response with the text \"([^\"]*)\"$")
	public void i_should_get_the_response_with_the_text(String arg1) throws Throwable {
		System.out.println("OK");
	}

	@When("^I try to get a token with the username \"([^\"]*)\" and password \"([^\"]*)\" -websocketSecurity$")
	public void i_try_to_get_a_token_with_the_username_and_password_websocketSecurity(String username, String password)
			throws Throwable {
		logInWithUser(username, password, WebSocketSecurityTest.getInstance());
	}

	@Then("^I should get a request with status code (\\d+) and a token in it - websocketSecurity$")
	public void i_should_get_a_request_with_status_code_and_a_token_in_it_websocketSecurity(int expectedStatusCode)
			throws Throwable {
		assertThat("Response Status is invalid", WebSocketSecurityTest.getInstance().getCurrentHttpStatus(),
				equalTo(expectedStatusCode));
		assertThat("Response should contain token", WebSocketSecurityTest.getInstance().getAuthorizationToken(),
				is(not(isEmptyOrNullString())));
	}

	@When("^I try to create a connection to the websocket \"([^\"]*)\" and subscribe to the channel \"([^\"]*)\" on the host \"([^\"]*)\"$")
	public void i_try_to_create_a_connection_to_the_websocket_and_subscribe_to_the_channel_on_the_host(
			String websocketname, String chanel, String host) throws Throwable {
		String URL = String.format("ws://%s:%d/%s", host, port, websocketname);
		connectAndSubscribeToChannel(URL, chanel, WebSocketSecurityTest.getInstance());
	}

	@When("^I try to send a test message with the text \"([^\"]*)\" to the chanel \"([^\"]*)\"$")
	public void i_try_to_send_a_test_message_with_the_text_to_the_chanel(String text, String channel) throws Throwable {
		WebSocketSecurityTest.getInstance().getStompSession().send(channel, text);
		Object returnedObject = WebSocketSecurityTest.getInstance().getCompletableFuture().get(3, SECONDS);
		assertThat("Object should not be null", returnedObject, notNullValue());
	}

	@Then("^The operation should fail$")
	public void the_operation_should_fail() throws Throwable {
		assertThat("Operation should fail", WebSocketSecurityTest.getInstance().isAuthenticationFailure(),
				equalTo(true));
	}

}
