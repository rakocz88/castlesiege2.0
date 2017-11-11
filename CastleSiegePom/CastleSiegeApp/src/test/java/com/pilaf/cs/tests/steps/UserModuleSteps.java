package com.pilaf.cs.tests.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.pilaf.cs.tests.builder.UserTest;
import com.pilaf.cs.users.model.User;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;;

public class UserModuleSteps extends AbstractCSTestCase {

	@Before
	public void resetAllData() throws InterruptedException {
		System.out.println("RESET DATA");
		UserTest.resetData();
	}

	@Given("^I am a user that is not loged in$")
	public void i_am_a_user_that_is_not_loged_in() throws Throwable {
		UserTest.resetData();
	}

	@When("^I try to get a token with the username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_try_to_get_a_token_with_the_username_and_password(String userName, String password) throws Throwable {
		logInWithUser(userName, password, UserTest.getInstance());

	}

	@Then("^I should get a request with status code (\\d+) and a token in it$")
	public void i_should_get_a_request_with_status_code_and_a_token_in_it(int expectedStatusCode) throws Throwable {
		assertThat("Response Status is invalid", UserTest.getInstance().getCurrentHttpStatus(),
				equalTo(expectedStatusCode));
		assertThat("Response should contain token", UserTest.getInstance().getAuthorizationToken(),
				is(not(isEmptyOrNullString())));
	}

	@When("^I try to get the restEndpoint for information for user \"([^\"]*)\" with the token$")
	public void i_try_to_get_the_restEndpoint_for_information_for_user_with_the_token(String userName)
			throws Throwable {
		MultiValueMap<String, String> headers = UserModuleTestUtils
				.getHeaders(UserTest.getInstance().getAuthorizationToken());
		String url = String.format(GET_USER_ENDPOINT, port, userName);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<User> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, User.class);
		UserTest.getInstance().setCurrentHttpStatus(response.getStatusCode().value());
		UserTest.getInstance().setReturnedUser(response.getBody());
	}

	@Then("^I should get the response status (\\d+) and a  user \"([^\"]*)\" in the response$")
	public void i_should_get_the_response_status_and_a_user_in_the_response(int statusCode, String userName)
			throws Throwable {
		assertThat("Wrong http status", UserTest.getInstance().getCurrentHttpStatus(), equalTo(statusCode));
		assertThat("User should not be null", UserTest.getInstance().getReturnedUser(), notNullValue());
		assertThat("User name should be " + userName, UserTest.getInstance().getReturnedUser().getUsername(),
				equalTo(userName));

	}

	@Then("^I should get the response status (\\d+)$")
	public void i_should_get_the_response_status(int responseStatus) throws Throwable {
		assertThat("Wrong http status", UserTest.getInstance().getCurrentHttpStatus(), equalTo(responseStatus));
	}

	@When("^I try to get all the users with non admin user$")
	public void i_try_to_get_all_the_users_with_non_admin_user() throws Throwable {
		String url = String.format(GET_ALL_USERy_ENDPOINT, port);
		MultiValueMap<String, String> headers = UserModuleTestUtils
				.getHeaders(UserTest.getInstance().getAuthorizationToken());
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
		UserTest.getInstance().setCurrentHttpStatus(response.getStatusCode().value());
	}

	@When("^I try to get all the users$")
	public void i_try_to_get_all_the_users() throws Throwable {
		String url = String.format(GET_ALL_USERy_ENDPOINT, port);
		MultiValueMap<String, String> headers = UserModuleTestUtils
				.getHeaders(UserTest.getInstance().getAuthorizationToken());
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);
		ResponseEntity<User[]> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, User[].class);
		UserTest.getInstance().setCurrentHttpStatus(response.getStatusCode().value());
		UserTest.getInstance().setUserList(new ArrayList<>(Arrays.asList(response.getBody())));
	}

	@Then("^The list of users should not be empty$")
	public void the_list_of_users_should_not_be_empty() throws Throwable {
		assertThat("UserListShouldNotBeEmpty", UserTest.getInstance().getUserList().isEmpty(), is(false));
	}

}
