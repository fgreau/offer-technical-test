Feature: the client registers a user

  Scenario: the client calls POST /users with correct values
    When the client calls createUser endpoint with values:
      | username | birthDate  | countryOfResidence | phoneNumber  | gender |
      | user1    | 2000-01-01 | france             | +33600000000 | male   |
      | user2    | 2000-01-01 | france             |              |        |
    Then the response status should be 200
    And the users will be registered:
      | user1 |
      | user2 |

  Scenario: the client calls POST /users with missing required fields
    When the client calls createUser endpoint with values:
      | username | birthDate  | countryOfResidence | phoneNumber | gender |
      |          | 2000-01-01 | france             |             |        |
      | user     |            | france             |             |        |
      | user     | 2000-01-01 |                    |             |        |
    Then the response status should be 400
    And the response error message should be "Field %s is required" with field name:
      | username           |
      | birthDate          |
      | countryOfResidence |

  Scenario: the client calls POST /users with already existing username
    Given the user with username "user" has been registered
    When the client calls createUser endpoint with values:
      | username | birthDate  | countryOfResidence | phoneNumber | gender |
      | user     | 2000-01-01 | france             |             |        |
    Then the response status should be 409
    And the response error message should be "This username is not available"

  Scenario: the client calls POST /users with an underage person
    When the client calls createUser endpoint with values:
      | username | birthDate  | countryOfResidence | phoneNumber | gender |
      | user     | 2020-01-01 | france             |             |        |
    Then the response status should be 400
    And the response error message should be "The user must be over 18"

  Scenario: the client calls POST /users with another country than France
    When the client calls createUser endpoint with values:
      | username | birthDate  | countryOfResidence | phoneNumber | gender |
      | user     | 2000-01-01 | swiss              |             |        |
    Then the response status should be 400
    And the response error message should be "The user must live in France"

  Scenario: the client calls GET /users/{username} with an existing username
    Given the user has been registered with values:
      | username | birthDate  | countryOfResidence | phoneNumber   | gender |
      | user     | 2000-01-01 | france             | ++33600000000 | female |
    When the client calls getUser endpoint with username "user"
    Then the response status should be 200
    And the result should be:
      | username | birthDate  | countryOfResidence | phoneNumber   | gender |
      | user     | 2000-01-01 | france             | ++33600000000 | female |

  Scenario: the client calls GET /users/{username} with an unknown username
    When the client calls getUser endpoint with username "user"
    Then the response status should be 404