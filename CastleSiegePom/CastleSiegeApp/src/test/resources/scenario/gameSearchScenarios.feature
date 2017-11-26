Feature: Case for game finding.It describes all the actions beetweeen user log and and then the game starts 

Scenario: AFa- Two players log in and want to start a game 
	Given AFa- There are 2 players that are not logged in 
	When AFa- I try to log in with "filip" "filip" 
	Then AFa- The user "filip" should have a valid token 
	When AFa- The user "filip" wants to start a game 
	Then AFa- The user "filip" should be added to the list searching for a game 
	When AFa- Another user tries to log in with "filip2" "filip2" 
	Then AFa- The user "filip2" should have a valid token 
	When AFa- The user "filip2" wants to start a game 
	Then AFa- The user "filip2" should get a msg that a game is found 
	And AFa- The user "filip" should subscribe to the new game channel 
	And AFa- The user "filip2" should subscribe to the new game channel 
	And AFa- a new instance of the game with the users "filip" and "filip2" should be created 
	And AFa- The table UserSearchGame should be empty 
	
Scenario: AFb- Five users log and and want to find a match for them 
	Given AFb- There are 2 players that are not logged in
	When AFb- They try to log with the names "filip" , "filip" , "filip2" , "filip2" , "filip3" , "filip3"  , "filip4" , "filip4"  , "filip5" , "filip5" 
	Then AFb- All the users should have valid tokens 
	When AFb- The users "filip" , "filip2" , "filip3" , "filip4" , "filip5" want to start a game 
	Then AFb- 2 instances of a game should be created 
	And AFb- 4 users of the users "filip" , "filip2" , "filip3" , "filip4" , "filip5" should be given to a room 
	And AFb- 1 user of the users "filip" , "filip2" , "filip3" , "filip4" , "filip5" should still be searching for a room to play 
	
	
Scenario: AG- The maximum amount of games it set 
	Given AG- There are 2 players that are not logged in 
	And AG- The number of maximum games is set to 1 
	When AG- They try to log with the names "filip" , "filip" , "filip2" , "filip2" , "filip3" , "filip3"  , "filip4" , "filip4"  , "filip5" , "filip5" 
	Then AG- All the users should have valid tokens 
	When AG- The users "filip" , "filip2" , "filip3" , "filip4" , "filip5" want to start a game 
	Then AG- 1 instances of a game should be created 
	Then AG- restert the amount of max games to default value; 
	
	
	
	