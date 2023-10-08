package com.fgreau.offertechtest.integrationTests;

import com.fgreau.offertechtest.dtos.UserDto;
import com.fgreau.offertechtest.unitTests.AbstractTest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@ContextConfiguration(classes = CucumberSpringConfiguration.class)
public class UserStepDefinitions {

    @When("the client calls POST /users with values:")
    public void the_client_calls_post_users(List<UserDto> users) {
        users.forEach(userDto -> {
            System.out.println(userDto.getUsername());
        });
    }

    @Then("the users will be created:")
    public void the_users_will_be_created(List<String> usernames) {
        usernames.forEach(username -> {
            System.out.println(username);
        });
    }
}
