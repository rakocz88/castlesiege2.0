Feature: Case for game finding.It describes all the actions beetweeen user log and and then the game starts 

Scenario: Two players log in and want to start a game
	Given There are 2 players that are not logged in
	When I try to log in with "filip" "filip"
	Then The user "filip" should have a valid token
	When The user "filip" wants to start a game
	Then The user "filip" should be added to the list searching for a game
	When Another user tries to log in with "filip2" "filip2"
	Then The user "filip2" should have a valid token
	When The user "filip2" wants to start a game
	Then The user "filip2" should get a msg that a game is found
	And The user "filip" should subscribe to the new game channel
	And The user "filip2" should subscribe to the new game channel
	And a new instance of the game with the users "filip" and "filip2" should be created
	And The table UserSearchGame should be empty
	And The user "filip" should no longer subscribe to the channel "searchGame"
	And The user "filip2" should no longer subscribe to the channel "searchGame"

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
	
	
	
	