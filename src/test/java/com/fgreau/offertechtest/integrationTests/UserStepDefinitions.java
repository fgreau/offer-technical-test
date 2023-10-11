package com.fgreau.offertechtest.integrationTests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fgreau.offertechtest.dtos.UserDto;
import com.fgreau.offertechtest.mappers.UserMapper;
import com.fgreau.offertechtest.repositories.UserRepository;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ComponentScan("com.fgreau.offertechtest")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = { UserStepDefinitions.class })
public class UserStepDefinitions {

    private static final String USERS_BASE_ENDPOINT = "/users";

    private static final String IDENTIFIED_USER_ENDPOINT = USERS_BASE_ENDPOINT + "/{username}";

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    private static List<MvcResult> results;

    /**
     * Allows the automatic conversion of DataTables of UserDtos to Lists of UserDtos.
     *
     * @param entry map of UserDto fields
     * @return UserDto
     */
    @DataTableType
    public UserDto userDtoEntry(Map<String, String> entry) {
        return userMapper.mapToDto(entry);
    }

    @Before
    public void setUp() {
        userRepository.deleteAll();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Given("the user with username {string} has been registered")
    public void theUserWithUsernameHasBeenRegistered(String username) {
        final UserDto userDto = UserDto.builder()
            .username(username)
            .birthDate(LocalDate.of(2000, 1, 1))
            .countryOfResidence("France")
            .build();

        userRepository.save(userMapper.map(userDto));
    }

    @Given("the user has been registered with values:")
    public void theUserHasBeenRegisteredWithValues(UserDto userDto) {
        userRepository.save(userMapper.map(userDto));
    }

    @When("the client calls createUser endpoint with values:")
    public void theClientCallsPostUsers(List<UserDto> userDtos) {
        results = userDtos.stream()
            .map(userDto -> {
                try {
                    return mockMvc.perform(post(USERS_BASE_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userDto)))
                        .andReturn();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            })
            .toList();
    }

    @When("the client calls getUser endpoint with username {string}")
    public void theClientCallsGETUsersUsernameWithUsername(String username) throws Exception {
        results = List.of(
            mockMvc.perform(get(IDENTIFIED_USER_ENDPOINT, username))
                .andReturn()
        );
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBeCode(int expectedResponseStatus) {
        results.stream()
            .map(MvcResult::getResponse)
            .map(MockHttpServletResponse::getStatus)
            .forEach(status -> assertEquals(expectedResponseStatus, status));
    }

    @Then("the users will be registered:")
    public void theUsersWillBeRegistered(List<String> usernames) {
        final List<UserDto> returnedDtos = results.stream()
            .map(this::extractUserDtoFromResponse)
            .toList();

        assertEquals(usernames.size(), returnedDtos.size());

        for (int i = 0; i < usernames.size(); i++) {
            assertEquals(usernames.get(i), returnedDtos.get(i).getUsername());
            assertTrue(userRepository.existsById(usernames.get(i)));
        }
    }

    private UserDto extractUserDtoFromResponse(MvcResult result) {
        try {
            String responseContent = result.getResponse().getContentAsString();
            return objectMapper.readValue(responseContent, UserDto.class);
        } catch (UnsupportedEncodingException | JsonProcessingException e) {
            throw new RuntimeException("Error processing response content", e);
        }
    }

    @Then("the response error message should be {string} with field name:")
    public void theResponseMessageShouldBeWithFieldName(String responseMessage, List<String> fieldNames) {
        assertEquals(fieldNames.size(), results.size());

        for (int i = 0; i < fieldNames.size(); i++) {
            String errorMessage = String.format(responseMessage, fieldNames.get(i));
            assertEquals(errorMessage, results.get(i).getResponse().getErrorMessage());
        }
    }

    @Then("the response error message should be {string}")
    public void theResponseMessageShouldBe(String responseMessage) {
        results.stream()
            .map(MvcResult::getResponse)
            .map(MockHttpServletResponse::getErrorMessage)
            .forEach(resultErrorMessage -> assertEquals(responseMessage, resultErrorMessage));
    }

    @Then("the result should be:")
    public void theBodyShouldBe(UserDto expectedUserDto) throws UnsupportedEncodingException, JsonProcessingException {
        final UserDto responseUserDto = objectMapper.readValue(results.get(0).getResponse().getContentAsString(), UserDto.class);
        assertEquals(expectedUserDto, responseUserDto);
    }

}
