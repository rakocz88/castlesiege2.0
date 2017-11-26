package com.pilaf.cs.tests.steps.websocket;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.springframework.beans.factory.annotation.Autowired;

import com.pilaf.cs.tests.builder.WebSocketSecurityTestState;
import com.pilaf.cs.tests.helper.LoginTestHelper;
import com.pilaf.cs.tests.helper.WebSocketTestHelper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class WebSocketSecuritySteps extends AbstractWebsocketScenarioTestCase {
	
	@Autowired
	private LoginTestHelper loginTestHelper;
	
	@Autowired
	private WebSocketTestHelper webSocketTestHelper;

	@Given("^ADa- I am a user that is not loged in$")
	public void ada_I_am_a_user_that_is_not_loged_in() throws Throwable {
		WebSocketSecurityTestState.reset();
	}

	@Then("^ADa- I should get the response with the text \"([^\"]*)\"$")
	public void ada_I_should_get_the_response_with_the_text(String text) throws Throwable {
		//TODO
	}

	@When("^ADa- I try to get a token with the username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void ada_I_try_to_get_a_token_with_the_username_and_password(String username, String password)
			throws Throwable {
		loginTestHelper.logInWithUser(username, password, WebSocketSecurityTestState.getInstance(), port);
	}

	@Then("^ADa- I should get a request with status code (\\d+) and a token in it$")
	public void ada_I_should_get_a_request_with_status_code_and_a_token_in_it(int expectedStatusCode) throws Throwable {
		assertThat("Response Status is invalid", WebSocketSecurityTestState.getInstance().getCurrentHttpStatus(),
				equalTo(expectedStatusCode));
		assertThat("Response should contain token", WebSocketSecurityTestState.getInstance().getAuthorizationToken(),
				is(not(isEmptyOrNullString())));
	}

	@When("^ADa- I try to create a connection to the websocket \"([^\"]*)\" and subscribe to the channel \"([^\"]*)\" on the host \"([^\"]*)\"$")
	public void ada_I_try_to_create_a_connection_to_the_websocket_and_subscribe_to_the_channel_on_the_host(
			String websocketname, String chanel, String host) throws Throwable {
		String URL = String.format("ws://%s:%d/%s", host, port, websocketname);
		webSocketTestHelper.connectAndSubscribeToChannel(URL, chanel, WebSocketSecurityTestState.getInstance());
	}

	@When("^ADa- I try to send a test message with the text \"([^\"]*)\" to the chanel \"([^\"]*)\"$")
	public void ada_I_try_to_send_a_test_message_with_the_text_to_the_chanel(String text, String channel)
			throws Throwable {
		WebSocketSecurityTestState.getInstance().getStompSession().send(channel, text);
		Object returnedObject = WebSocketSecurityTestState.getInstance().getCompletableFuture().get(3, SECONDS);
		assertThat("Object should not be null", returnedObject, notNullValue());
	}

	@Given("^ADb- I am a user that is not loged in$")
	public void adb_I_am_a_user_that_is_not_loged_in() throws Throwable {
		WebSocketSecurityTestState.reset();
	}

	@When("^ADb- I try to create a connection to the websocket \"([^\"]*)\" and subscribe to the channel \"([^\"]*)\" on the host \"([^\"]*)\"$")
	public void adb_I_try_to_create_a_connection_to_the_websocket_and_subscribe_to_the_channel_on_the_host(
			String websocketname, String chanel, String host) throws Throwable {
		String URL = String.format("ws://%s:%d/%s", host, port, websocketname);
		webSocketTestHelper.connectAndSubscribeToChannel(URL, chanel, WebSocketSecurityTestState.getInstance());
	}

	@Then("^ADb- The operation should fail$")
	public void adb_The_operation_should_fail() throws Throwable {
		assertThat("Operation should fail", WebSocketSecurityTestState.getInstance().isAuthenticationFailure(),
				equalTo(true));
	}

}
