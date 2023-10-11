package com.fgreau.offertechtest.unitTests.services;

import com.fgreau.offertechtest.dtos.UserDto;
import com.fgreau.offertechtest.models.User;
import com.fgreau.offertechtest.repositories.UserRepository;
import com.fgreau.offertechtest.services.UserService;
import com.fgreau.offertechtest.unitTests.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest extends AbstractTest {

    private static final String USERNAME = "pascal456";

    private static final LocalDate BIRTHDATE = LocalDate.of(1992, 2, 6);

    private static final String COUNTRY = "france";

    private static final String INCORRECT_COUNTRY = "britany";

    private static final String PHONE_NUMBER = "+33600000000";

    private static final String GENDER = "male";

    private static final int BAD_REQUEST_CODE = 400;

    private static final int CONFLICT_CODE = 409;

    private static final String MISSING_FIELD_ERROR = "Field %s is required";

    private static final String USERNAME_ALREADY_EXISTS_ERROR = "This username is not available";

    private static final String INVALID_COUNTRY_ERROR = "The user must live in France";

    private static final String AGE_ERROR = "The user must be over 18";

    private static final String REQUIRED_FIELD_USERNAME = "username";

    private static final String REQUIRED_FIELD_BIRTHDATE = "birthDate";

    private static final String REQUIRED_FIELD_COUNTRY_OF_RESIDENCE = "countryOfResidence";

    private static final int ADULT_AGE = 18;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void givenCorrectDto_whenRegisters_thenUserRegisteredAndCorrectDto() {
        final UserDto dto = getDto();

        final UserDto registeredDto = userService.registerUser(dto);

        final User registeredEntity = userRepository.findById(USERNAME).orElse(null);
        assertNotNull(registeredEntity);
        assertEquals(USERNAME, registeredEntity.getUsername());
        assertEquals(BIRTHDATE, registeredEntity.getBirthDate());
        assertEquals(COUNTRY, registeredEntity.getCountryOfResidence());
        assertEquals(PHONE_NUMBER, registeredEntity.getPhoneNumber());
        assertEquals(GENDER, registeredEntity.getGender());
        assertNotNull(registeredEntity.getCreationDate());
        assertEquals(LocalDate.now(), registeredEntity.getCreationDate().toLocalDate());
        assertFalse(registeredEntity.getDeleted());

        assertEquals(USERNAME, registeredDto.getUsername());
        assertEquals(BIRTHDATE, registeredDto.getBirthDate());
        assertEquals(COUNTRY, registeredDto.getCountryOfResidence());
        assertEquals(PHONE_NUMBER, registeredDto.getPhoneNumber());
        assertEquals(GENDER, registeredDto.getGender());
    }

    @Test
    void givenCorrectDtoMinimalConfiguration_whenRegisters_thenUserRegisteredAndCorrectDto() {
        final UserDto dto = UserDto.builder().username(USERNAME).birthDate(BIRTHDATE).countryOfResidence(COUNTRY).build();

        final UserDto registeredDto = userService.registerUser(dto);

        final User registeredEntity = userRepository.findById(USERNAME).orElse(null);
        assertNotNull(registeredEntity);
        assertEquals(USERNAME, registeredEntity.getUsername());
        assertEquals(BIRTHDATE, registeredEntity.getBirthDate());
        assertEquals(COUNTRY, registeredEntity.getCountryOfResidence());
        assertNull(registeredEntity.getPhoneNumber());
        assertNull(registeredEntity.getGender());
        assertNotNull(registeredEntity.getCreationDate());
        assertEquals(LocalDate.now(), registeredEntity.getCreationDate().toLocalDate());
        assertFalse(registeredEntity.getDeleted());

        assertEquals(USERNAME, registeredDto.getUsername());
        assertEquals(BIRTHDATE, registeredDto.getBirthDate());
        assertEquals(COUNTRY, registeredDto.getCountryOfResidence());
        assertNull(registeredDto.getPhoneNumber());
        assertNull(registeredDto.getGender());
    }

    @Test
    void givenCorrectDtoMinimalAcceptedAge_whenRegisters_thenUserRegisteredAndCorrectDto() {
        final UserDto dto = getDto();
        dto.setBirthDate(LocalDate.now().minusYears(ADULT_AGE));

        final UserDto registeredDto = userService.registerUser(dto);

        final User registeredEntity = userRepository.findById(USERNAME).orElse(null);
        assertNotNull(registeredEntity);
        assertEquals(USERNAME, registeredEntity.getUsername());
        assertEquals(LocalDate.now().minusYears(ADULT_AGE), registeredEntity.getBirthDate());
        assertEquals(COUNTRY, registeredEntity.getCountryOfResidence());
        assertEquals(PHONE_NUMBER, registeredEntity.getPhoneNumber());
        assertEquals(GENDER, registeredEntity.getGender());
        assertNotNull(registeredEntity.getCreationDate());
        assertEquals(LocalDate.now(), registeredEntity.getCreationDate().toLocalDate());
        assertFalse(registeredEntity.getDeleted());

        assertEquals(USERNAME, registeredDto.getUsername());
        assertEquals(LocalDate.now().minusYears(ADULT_AGE), registeredDto.getBirthDate());
        assertEquals(COUNTRY, registeredDto.getCountryOfResidence());
        assertEquals(PHONE_NUMBER, registeredDto.getPhoneNumber());
        assertEquals(GENDER, registeredDto.getGender());
    }

    @Test
    void givenDtoMissingUsername_whenRegisters_thenResponseStatusExceptionBadRequest() {
        final UserDto dto = getDto();
        dto.setUsername(null);

        final ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.registerUser(dto));

        assertEquals(BAD_REQUEST_CODE, exception.getStatusCode().value());
        assertEquals(String.format(MISSING_FIELD_ERROR, REQUIRED_FIELD_USERNAME), exception.getReason());
    }

    @Test
    void givenDtoMissingBirthdate_whenRegisters_thenResponseStatusExceptionBadRequestAndUserNotRegistered() {
        final UserDto dto = getDto();
        dto.setBirthDate(null);

        final ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.registerUser(dto));

        assertEquals(BAD_REQUEST_CODE, exception.getStatusCode().value());
        assertEquals(String.format(MISSING_FIELD_ERROR, REQUIRED_FIELD_BIRTHDATE), exception.getReason());

        assertFalse(userRepository.existsById(USERNAME));
    }

    @Test
    void givenDtoMissingCountryOfResidence_whenRegisters_thenResponseStatusExceptionBadRequestAndUserNotRegistered() {
        final UserDto dto = getDto();
        dto.setCountryOfResidence(null);

        final ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.registerUser(dto));

        assertEquals(BAD_REQUEST_CODE, exception.getStatusCode().value());
        assertEquals(String.format(MISSING_FIELD_ERROR, REQUIRED_FIELD_COUNTRY_OF_RESIDENCE), exception.getReason());

        assertFalse(userRepository.existsById(USERNAME));
    }

    @Test
    void givenDtoWithExistingUsername_whenRegisters_thenResponseStatusExceptionConflict() {
        userService.registerUser(getDto());
        assertTrue(userRepository.existsById(USERNAME));

        final UserDto dto = getDto();

        final ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.registerUser(dto));

        assertEquals(CONFLICT_CODE, exception.getStatusCode().value());
        assertEquals(USERNAME_ALREADY_EXISTS_ERROR, exception.getReason());
    }

    @Test
    void givenDtoWithWrongCountry_whenRegisters_thenResponseStatusExceptionBadRequestAndUserNotRegistered() {
        final UserDto dto = getDto();
        dto.setCountryOfResidence(INCORRECT_COUNTRY);

        final ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.registerUser(dto));

        assertEquals(BAD_REQUEST_CODE, exception.getStatusCode().value());
        assertEquals(INVALID_COUNTRY_ERROR, exception.getReason());

        assertFalse(userRepository.existsById(USERNAME));
    }

    @Test
    void givenDtoWithAgeUnder18_whenRegisters_thenResponseStatusExceptionBadRequestAndUserNotRegistered() {
        // 1 day before accepted age
        assertWhenUnderEighteen(LocalDate.now().minusYears(ADULT_AGE).plusDays(1));

        // 1 day old
        assertWhenUnderEighteen(LocalDate.now().minusDays(1));

        // 0 day old
        assertWhenUnderEighteen(LocalDate.now());

        // -1 day old
        assertWhenUnderEighteen(LocalDate.now().plusDays(1));
    }

    private void assertWhenUnderEighteen(final LocalDate birthDate) {
        final UserDto dto = getDto();
        dto.setBirthDate(birthDate);

        final ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.registerUser(dto));

        assertEquals(BAD_REQUEST_CODE, exception.getStatusCode().value());
        assertEquals(AGE_ERROR, exception.getReason());

        assertFalse(userRepository.existsById(USERNAME));
    }

    /**
     * Sets up a valid UserDto with all fields set.
     *
     * @return dto
     */
    private UserDto getDto() {
        return UserDto.builder()
            .username(USERNAME)
            .birthDate(BIRTHDATE)
            .countryOfResidence(COUNTRY)
            .phoneNumber(PHONE_NUMBER)
            .gender(GENDER)
            .build();
    }

}