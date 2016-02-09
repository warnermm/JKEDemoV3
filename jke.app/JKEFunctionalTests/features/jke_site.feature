Feature: JKEFunctionalTests

  Scenario: A new JKE website was deployed
    Given I have a jke web server
    Then I should be able to visit the homepage
    
  Scenario: A user tries to log in to the JKE website
    Given I have a jke web server
	    And I have a user account
    Then I should be able to login to the user account
    
    Scenario: A user tries make a transaction with a negative value
    Given I have a jke web server
    Then I should not be able to contribute a negative amount
    
	Scenario: A user gets a stock quote for ticker symbol 'IBM'
	Given I have a jke web server
		And I have a user account
	Then I should be able to get a stock quote for 'IBM'
