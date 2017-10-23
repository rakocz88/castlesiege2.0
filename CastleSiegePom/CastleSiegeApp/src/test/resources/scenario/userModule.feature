Feature: User module feature Feature

#Scenario: I want to log in as a valid user and get a token from the oauth2 service
#	Given I am a user that is not loged in
#	When I try to get a token using the oauth login endpoint and using username = "filip" and password "filip"
#	Then the oauth token should not be empty

Scenario: I want to log in as a customer user
	Given I am a user that is not loged in
	When I try to get the restEndpoint for information for user "filip" with the token
	Then I should get the response status 500
	When I try to get a token with the username "filip" and password "filip"
	Then I should get a request with status code 200 and a token in it
	When I try to get the restEndpoint for information for user "filip" with the token
	Then I should get the response status 200 and a  user "filip" in the response
	When I try to get the restEndpoint for information for user "filip2" with the token
	Then I should get the response status 403
	When I try to get all the users with non admin user
	Then I should get the response status 403
	
Scenario: I want to log in as a admin user
	Given I am a user that is not loged in
	When I try to get a token with the username "admin" and password "admin"
	Then I should get a request with status code 200 and a token in it
	When I try to get the restEndpoint for information for user "admin" with the token
	Then I should get the response status 200 and a  user "admin" in the response
	When I try to get the restEndpoint for information for user "filip" with the token
	Then I should get the response status 200 and a  user "filip" in the response
	When I try to get all the users 
	Then I should get the response status 200
	And The list of users should not be empty
	
	