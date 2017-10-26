package com.pilaf.cs.tests.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.pilaf.cs.notification.biz.EmailBiz;
import com.pilaf.cs.security.JwtAuthenticationRequest;
import com.pilaf.cs.security.JwtAuthenticationResponse;
import com.pilaf.cs.tests.SpringIntegrationTest;
import com.pilaf.cs.tests.builder.UserRegistrationTest;
import com.pilaf.cs.users.model.User;
import com.pilaf.cs.users.repository.UserRepository;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;;

public class UserRegistrationSteps extends SpringIntegrationTest implements RestEndpoints {

	private TestRestTemplate restTemplate = new TestRestTemplate(new RestTemplate());

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailBiz emailBiz;

	@Before
	public void resetAllData() throws InterruptedException {
		System.out.println("RESET DATA");
		UserRegistrationTest.resetData();
	}

	@Given("^I am a person who wants to create a account$")
	public void i_am_a_person_who_wants_to_create_a_account() throws Throwable {
		UserRegistrationTest.resetData();
	}

	@When("^A user with username \"([^\"]*)\" exists in the database$")
	public void a_user_with_username_exists_in_the_database(String name) throws Throwable {
		assertThat("User should exist in database", userRepository.findByUsername(name), is(notNullValue()));
	}

	@Then("^I should get a response with status (\\d+)$")
	public void i_should_get_a_response_with_status(int statusCode) throws Throwable {
		assertThat("Wrong status code", UserRegistrationTest.getInstance().getCurrentHttpStatus(), equalTo(statusCode));
	}

	@When("^User \"([^\"]*)\" does not exist in the database$")
	public void user_does_not_exist_in_the_database(String username) throws Throwable {
		assertThat("User should not exist in the database", userRepository.findByUsername(username), is(nullValue()));
	}

	@Then("^User \"([^\"]*)\" enabled flag should be \"([^\"]*)\"$")
	public void user_enabled_flag_should_be(String name, String enabled) throws Throwable {
		assertThat("User enabled flag should be", userRepository.findByUsername(name).getEnabled(),
				equalTo(Boolean.parseBoolean(enabled)));
	}

	@Then("^in the output msg there should be an entry for user \"([^\"]*)\" and it should not be empty$")
	public void in_the_output_msg_there_should_be_an_entry_for_user_and_it_should_not_be_empty(String username)
			throws Throwable {
		assertThat("Email should be send to user", emailBiz.findAllByUser(username).isEmpty(), equalTo(false));
	}

	@When("^I try to perform a log in with the user \"([^\"]*)\" with password \"([^\"]*)\"$")
	public void i_try_to_perform_a_log_in_with_the_user_with_password(String username, String password)
			throws Throwable {
		User user = new User(username, password);
		UserRegistrationTest.getInstance().setCurrentUser(user);
		String url = String.format(LOGIN_ENDPOINT, port);
		JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest(
				UserRegistrationTest.getInstance().getCurrentUser().getUsername(),
				UserRegistrationTest.getInstance().getCurrentUser().getPassword());
		ResponseEntity<JwtAuthenticationResponse> response = restTemplate.postForEntity(url, authenticationRequest,
				JwtAuthenticationResponse.class);
		UserRegistrationTest.getInstance().setAuthorizationToken(response.getBody().getToken());
		UserRegistrationTest.getInstance().setCurrentHttpStatus(response.getStatusCode().value());
	}

	@Then("^I should get the response (\\d+)$")
	public void i_should_get_the_response(int resposeCode) throws Throwable {
		assertThat("Wrong status code", UserRegistrationTest.getInstance().getCurrentHttpStatus(),
				equalTo(resposeCode));
	}



	@Then("^User \"([^\"]*)\" should be in the database$")
	public void user_should_be_in_the_database(String username) throws Throwable {
		assertThat("User should not exist in the database", userRepository.findByUsername(username),
				is(notNullValue()));
	}

	@When("^I click the link then was send to me$")
	public void i_click_the_link_then_was_send_to_me() throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		String activationLink = emailBiz
				.getLastEmailForUser(UserRegistrationTest.getInstance().getCurrentUser().getUsername()).getMessage();
		restTemplate.getForEntity(activationLink, String.class);
		Thread.sleep(1000l);
		User user = userRepository.findByUsername(UserRegistrationTest.getInstance().getCurrentUser().getUsername());
		UserRegistrationTest.getInstance().setCurrentUser(user);
	}

	@Then("^the response should contain a not empty token$")
	public void the_response_should_contain_a_not_empty_token() throws Throwable {
		assertThat("Token should not be empty", UserRegistrationTest.getInstance().getAuthorizationToken(),
				is(notNullValue()));
	}
	
//	@When("^I want to register a new user with username \"([^\"]*)\" and password \"([^\"]*)\" and first name \"([^\"]*)\" and surname \"([^\"]*)\" and email = \"([^\"]*)\"$")
//	public void i_want_to_register_a_new_user_with_username_and_password_and_first_name_and_surname_and_email(String arg1, String arg2, String arg3, String arg4, String arg5) throws Throwable {
//		User user = new User(arg1, arg2);
//		user.setEmail(arg5);
//		user.setLastname(arg3);
//		user.setFirstname(arg4);
//		UserRegistrationTest.getInstance().setCurrentUser(user);
//		String url = String.format(REGISTRATION_ENDPOINT, port);
//		ResponseEntity<String> response = restTemplate.postForEntity(url, user, String.class);
//		UserRegistrationTest.getInstance().setCurrentHttpStatus(response.getStatusCodeValue());
//	}
	
	@When("^I register a user with login  \"([^\"]*)\" and password \"([^\"]*)\" and firstname  \"([^\"]*)\" and surname \"([^\"]*)\" and login \"([^\"]*)\"$")
	public void i_register_a_user_with_login_and_password_and_firstname_and_surname_and_login(String arg1, String arg2, String arg3, String arg4, String arg5) throws Throwable {
		User user = new User(arg1, arg2);
		user.setEmail(arg5);
		user.setLastname(arg3);
		user.setFirstname(arg4);
		UserRegistrationTest.getInstance().setCurrentUser(user);
		String url = String.format(REGISTRATION_ENDPOINT, port);
		ResponseEntity<String> response = restTemplate.postForEntity(url, user, String.class);
		UserRegistrationTest.getInstance().setCurrentHttpStatus(response.getStatusCodeValue());
	}
}
