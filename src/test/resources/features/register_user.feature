Feature: the client registers a user

  Scenario: the client calls POST /users with correct values
    When the client calls POST /users with values:
      | username | birthDate  | countryOfResidence | phoneNumber  | gender |
      | user1    | 2000-01-01 | france             | +33600000000 | male   |
      | user2    | 2000-01-01 | france             |              |        |
    Then the users will be created:
      | user1 |
      | user2 |