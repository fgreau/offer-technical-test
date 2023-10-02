# Offer technical test
### A simple springboot API
___

## What should be done:

A Springboot API that exposes two services:
- one that allows to register a user
- one that displays the details of a registered user

A user is defined by:
- a user name
- a birthdate
- a country of residence

A user has **optional** attributes:
- a phone number
- a gender

Only **adult French** residents are allowed to create an account!

Inputs **must** be validated and return proper error messages/http statuses.

## Deliverables:
- **Source code** (Github link for example)
- **Documentation** (how to build from sources, how to use the API)
- **Request samples** (I.e. Postman collection)

## Bonuses:
- Include an embedded database in your project to facilitate the installation and execution by someone else
- Use AOP to log inputs and outputs of each call as well as the processing time
- Feel free to enrich the model as you see fit

### Make sure you pay special attention to code and test quality.