#Feature: This feature describe how running games should react to messages adding resources

#Scenario: CAa- Players getting and spending gold
#	Given CAa- Wait 2 seconds
#	And CAa- There is a running game for  players "player1" and "player2" with the uuid "9282eb6d-8a8c-42ce-ac96-89fa854e1f39"
#	And CAa - The 2 players should have no resources
#	When CAa- "player1" sends a message that will give him 1 gold
#	Then CAa- "player1" should have 1 gold
#	And CAa- "player2" should have 0 gold
#	When CAa- "player2" sends a message that will give him 2 gold and player 1 will lose 1 gold
#	Then CAa- "player1" should have 0 gold
#	And CAa- "player2" should have 2 gold
#	When CAa- "player1" sends a message that will give him 3 gold
#	Then CAa- "player1" should have 3 gold
	
#Scenario: CAb- Players getting and spending mana
#	Given CAb- Wait 2 seconds
#	And CAb- There is a running game for  players "player1" and "player2" with the uuid "c23bbff3-bccc-498d-8f39-081d1c398193"
#	When CAb- "player1" sends a message that will give him 1 "white" mana
#	Then CAb- "player1" should have 1 "white" mana
#	When CAb- "player2" sends a message that will give him 2 "black" mana and 3 "red" mana
#	Then CAb- "player2" should have 2 "black" mana and 3 "red" mana
#	When CAb- "player1" sends a message that will steal 2 "red" mana from "player2"
#	Then CAb- "player1" should have 2 "red" mana and "player2" should gave 1 red mana
#	When CAb- "player2" sends a message that will steal 1 mana from every color from "player1" and add it to its "red" mana
#	Then CAb- "player2" should have 3 "red" mana and "player1" should have 0 "white" mana and 1 "red" mana
	
	
#Scenario: CAd- Players drawing cards 
#	Given CAd- Wait 2 seconds
#	And CAd- There is a running game for  players "player1" and "player2" with the uuid "82e15888-4e21-4570-bcab-0539fa954ab5"
#	And CAd- the 2 players dont have any cards in the hands
#	When CAd- "player1" sends a message that will allow to draw him one card from the creature deck
#	Then CAd- "player1" should have 1 card in his hand and it should be a creature card
#	When CAd- "player1" sends a message that will allow "player2" to draw a card from the imperial deck
#	Then CAd- "player2" should have 1 card on his hand and it should be a imperial card
#	When CAd- "player1" send a message that will allow every player to draw a card from any deck
#	Then CAd- "player1" should have 4 cards on hist hand and 1 card should be a attachment card, 2 creature card and 1 imperial card
#	And CAd- "player2" should have 4 cards on hist hand and 1 card should be a attachment card, 1 creature card and 12 imperial card
	
#Scenario: CAf - Players earning and loosing victory points 
#	Given CAf- Wait 2 seconds
#	And CAf- There is a running game for  players "player1" and "player2" with the uuid "82e15888-4e21-4570-bcab-0539fa954ab"
#	And CAf- "player1" and "player2" have 0 victory points
#	When CAf- "player1" sends a message that gives him 2 victory points
#	Then CAf- "player1" should have 3 victory points
#	When CAf- "player2" sends a message that gives him 3 victory points
#	Then CAf- "player2" should have 3 victory points
#	When CAf- "player1" sends a message that gives him and the other player 3 victory points
#	Then CAf- "player1" should have 5 victory points
#	And CAf- "player2" should have 6 victory points
	
#Scenario: CAg - Resources given by a castle
#	Given CAg- Wait 2 seconds
#	And CAg- There is a running game for  players "player1" and "player2" with the uuid "82e15888-4e21-4570-bcab-0539fa954gg"
#	And CAg- "player1" has a castle with starting victory points 3 and that gives 3 gold per round and 1 white mana per rount
#	When CAg- The game starts
#	Then CAg- "player1" should have 3 victory points and 0 gold and 0 white mana
#	When CAg- The turn starts
#	And CAg- The resources gained from castle phase ends
#	Then CAg- "player1" should have 3 victor points and 3 gold and 1 white mana
#	
	
#Scenario: CAh- There are 5 games active and players perform multiple resouces changes
#	Given CAh- Wait 2 seconds
#	And CAh- There is a running game for  players "player1" and "player2" with the uuid "82e15888-4e21-4570-bcab-0539fa955gg"
#	And CAh- There is a running game for  players "player3" and "player4" with the uuid "82e15888-4e21-4570-bcab-0539fa964gg"
#	And CAh- There is a running game for  players "player5" and "player6" with the uuid "82e15888-4e21-4570-bcab-0539fa054gg"
#	When CAh- "player1" sends a message that gives him 2 gold
#	Then CAh- "player1" should have 2 gold
#	And CAg- "player2" , "player3" , "player4" , "player5" , "player6"  should have 0 gold
#	When CAh- "player4" sends a message that gives him 2 and the oponent 3 victory points
#	Then CAh- "player4" and "player3" should have 3 victory points
#	And CAg- "player1" , "player2" , "player5" , "player6"  should have 0 victory points
#	When CAh- "player5" sends a message that gives the oponent 1 red mana
#	Then CAh- "player6" should have 1 red mana
#	And CAg- "player1" , "player2" , "player3" , "player4" , "player5"  should have 0 red mana
#

	
	
	
	