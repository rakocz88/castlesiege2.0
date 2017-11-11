Feature: Case for game finding.It describes all the actions beetweeen user log and and then the game starts 

Scenario: Five users log and and want to find a match for them 
	Given There are 5 users that are not logged in 
	When They try to log with the names "filip" , "filip" , "filip2" , "filip2" , "filip3" , "filip3"  , "filip4" , "filip4"  , "filip5" , "filip5" 
	Then All the users should have valid tokens 
	When The users "filip" , "filip2" , "filip3" , "filip4" , "filip5" want to start a game 
	Then 2 instances of a game should be created 
	And 4 users of the users "filip" , "filip2" , "filip3" , "filip4" , "filip5" should be given to a room 
	And 1 user of the users "filip" , "filip2" , "filip3" , "filip4" , "filip5" should still be searching for a room to play 
	When one of the game rooms is selected 
	And two of the users send a "startGame" event 
	Then the game should be set as started 
	And the game should have set two users 
	And the two users should recieve s STOMP message with the event "startGame" 
	
	
Scenario: The maximum amount of games it set 
	Given There are 5 users that are not logged in 
	And The number of maximum games is set to 1
	When They try to log with the names "filip" , "filip" , "filip2" , "filip2" , "filip3" , "filip3"  , "filip4" , "filip4"  , "filip5" , "filip5" 
	Then All the users should have valid tokens 
	When The users "filip" , "filip2" , "filip3" , "filip4" , "filip5" want to start a game
	Then 2 instances of a game should be created
	
	
	
	