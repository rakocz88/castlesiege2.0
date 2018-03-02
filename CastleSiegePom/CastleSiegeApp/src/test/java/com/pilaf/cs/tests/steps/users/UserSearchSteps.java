package com.pilaf.cs.tests.steps.users;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import com.pilaf.cs.users.model.ESUser;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserSearchSteps  extends AbstractUserRegistrationTestCase{
	
	@Given("^AC- User \"([^\"]*)\" does not exist in the database$")
	public void ac_User_does_not_exist_in_the_database(String login) throws Throwable {
		esUserRepository.deleteAll();
		assertThat("User should not exist in database", userRepository.findByUsername(login), is(nullValue()));
	}

	@When("^AC- I register a user with login  \"([^\"]*)\" and password \"([^\"]*)\" and firstname  \"([^\"]*)\" and surname \"([^\"]*)\" and email \"([^\"]*)\"$")
	public void ac_I_register_a_user_with_login_and_password_and_firstname_and_surname_and_email(String login, String password, String firstname, String surname, String email) throws Throwable {
		registerUser(login,password,firstname, surname, email, UserSearchTestState.getInstance());
	}

	@Then("^AC- I should get the response status (\\d+)$")
	public void ac_I_should_get_the_response_status(int responseType) throws Throwable {
	   assertThat(UserSearchTestState.getInstance().getCurrentHttpStatus(), equalTo(responseType));
	}

	@When("^AC- I try to find a user in Elastic Search with the login \"([^\"]*)\"$")
	public void ac_I_try_to_find_a_user_in_Elastic_Search_with_the_login(String login) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		esUserRepository.findAll();
		ESUser esUser = esUserRepository.findByUsername(login);
	    assertThat(esUser, is(notNullValue()));
	    UserSearchTestState.getInstance().setEsUser(esUser);
	}

	@Then("^AC- I should get a user with the login \"([^\"]*)\" and firstname \"([^\"]*)\" and surname \"([^\"]*)\" and email \"([^\"]*)\" and the enabled flag should be set to \"([^\"]*)\"$")
	public void ac_I_should_get_a_user_with_the_login_and_firstname_and_surname_and_email_and_the_enabled_flag_should_be_set_to(String login, String firstname, String lastname, String email, String enabled) throws Throwable {
	    assertThat(UserSearchTestState.getInstance().getEsUser().getUsername(), equalTo(login));
	    assertThat(UserSearchTestState.getInstance().getEsUser().getFirstname(), equalTo(firstname));
	    assertThat(UserSearchTestState.getInstance().getEsUser().getLastname(), equalTo(lastname));
	    assertThat(UserSearchTestState.getInstance().getEsUser().getEnabled(), equalTo(Boolean.parseBoolean(enabled)));
	}

	@When("^B- I click the link then was send to \"([^\"]*)\"$")
	public void b_I_click_the_link_then_was_send_to(String email) throws Throwable {
		clickTheLinkThatWasSendOnEmail(email, UserSearchTestState.getInstance());
	}

}
