package com.pilaf.cs.tests.steps.users;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.pilaf.cs.tests.builder.UserRegistrationTestState;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;;

public class UserRegistrationSteps extends AbstractUserRegistrationTestCase {

	@Given("^AB- I am a person who wants to create a account$")
	public void ab_I_am_a_person_who_wants_to_create_a_account() throws Throwable {
		UserRegistrationTestState.resetData();
	}

	@When("^AB- A user with username \"([^\"]*)\" exists in the database$")
	public void ab_A_user_with_username_exists_in_the_database(String name) throws Throwable {
		assertThat("User should exist in database", userRepository.findByUsername(name), is(notNullValue()));
	}

	@Then("^AB- I should get a response with status (\\d+)$")
	public void ab_I_should_get_a_response_with_status(int statusCode) throws Throwable {
		assertThat("Wrong status code", UserRegistrationTestState.getInstance().getCurrentHttpStatus(),
				equalTo(statusCode));
	}

	@Then("^AB- I should get the response status (\\d+)$")
	public void ab_I_should_get_the_response_status(int statusCode) throws Throwable {
		assertThat("Wrong status code", UserRegistrationTestState.getInstance().getCurrentHttpStatus(),
				equalTo(statusCode));
	}

	@When("^AB- User \"([^\"]*)\" does not exist in the database$")
	public void ab_User_does_not_exist_in_the_database(String username) throws Throwable {
		assertThat("User should not exist in the database", userRepository.findByUsername(username), is(nullValue()));
	}

	@Then("^AB- User \"([^\"]*)\" enabled flag should be \"([^\"]*)\"$")
	public void ab_User_enabled_flag_should_be(String name, String enabled) throws Throwable {
		assertThat("User enabled flag should be", userRepository.findByUsername(name).getEnabled(),
				equalTo(Boolean.parseBoolean(enabled)));
	}

	@Then("^AB- in the output msg there should be an entry for user \"([^\"]*)\" and it should not be empty$")
	public void ab_in_the_output_msg_there_should_be_an_entry_for_user_and_it_should_not_be_empty(String username)
			throws Throwable {
		assertThat("Email should be send to user", emailBiz.findAllByUser(username).isEmpty(), equalTo(false));
	}

	@When("^AB- I try to perform a log in with the user \"([^\"]*)\" with password \"([^\"]*)\"$")
	public void ab_I_try_to_perform_a_log_in_with_the_user_with_password(String username, String password)
			throws Throwable {
		loginTestHelper.logInWithUser(username, password, UserRegistrationTestState.getInstance(), port);
	}

	@Then("^AB- I should get the response (\\d+)$")
	public void ab_I_should_get_the_response(int resposeCode) throws Throwable {
		assertThat("Wrong status code", UserRegistrationTestState.getInstance().getCurrentHttpStatus(),
				equalTo(resposeCode));
	}

	@Then("^AB- User \"([^\"]*)\" should be in the database$")
	public void ab_User_should_be_in_the_database(String username) throws Throwable {
		assertThat("User should exist in the database", userRepository.findByUsername(username), is(notNullValue()));
	}

	@Then("^AB- the response should contain a not empty token$")
	public void ab_the_response_should_contain_a_not_empty_token() throws Throwable {
		assertThat("Token should not be empty", UserRegistrationTestState.getInstance().getAuthorizationToken(),
				is(notNullValue()));
	}

	@When("^AB- I register a user with login  \"([^\"]*)\" and password \"([^\"]*)\" and firstname  \"([^\"]*)\" and surname \"([^\"]*)\" and email \"([^\"]*)\"$")
	public void ab_I_register_a_user_with_login_and_password_and_firstname_and_surname_and_email(String login,
			String password, String firstname, String surname, String email) throws Throwable {
		registerUser(login, password, firstname, surname, email);
	}

	@When("^AB- I click the link then was send to \"([^\"]*)\"$")
	public void ab_I_click_the_link_then_was_send_to(String email) throws Throwable {
		clickTheLinkThatWasSendOnEmail(email);
	}
}
