package com.fgreau.offertechtest.unitTests.mappers;

import com.fgreau.offertechtest.dtos.UserDto;
import com.fgreau.offertechtest.mappers.UserMapper;
import com.fgreau.offertechtest.models.User;
import com.fgreau.offertechtest.repositories.UserRepository;
import com.fgreau.offertechtest.unitTests.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserMapperTest extends AbstractTest {

    private static final String USERNAME = "pedro123";

    private static final LocalDate BIRTHDATE = LocalDate.of(1992, 2, 6);

    private static final String COUNTRY_UPPERCASE = "FRANCE";

    private static final String COUNTRY_LOWERCASE = "france";

    private static final String PHONE_NUMBER = "+33600000000";

    private static final String GENDER = "male";

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    void givenUserDto_whenMaps_thenCorrectEntity() {
        final UserDto dto = UserDto.builder()
            .username(USERNAME)
            .birthDate(BIRTHDATE)
            .countryOfResidence(COUNTRY_UPPERCASE)
            .phoneNumber(PHONE_NUMBER)
            .gender(GENDER)
            .build();

        final User entity = userMapper.map(dto);

        assertEquals(USERNAME, entity.getUsername());
        assertEquals(BIRTHDATE, entity.getBirthDate());
        assertEquals(COUNTRY_LOWERCASE, entity.getCountryOfResidence());
        assertEquals(PHONE_NUMBER, entity.getPhoneNumber());
        assertEquals(GENDER, entity.getGender());
        assertFalse(entity.getDeleted());
    }

    @Test
    void givenUserEntity_whenMaps_thenCorrectDto() {
        final User entity = userRepository.findById(USERNAME).orElse(null);
        assertNotNull(entity);

        final UserDto dto = userMapper.map(entity);

        assertEquals(USERNAME, dto.getUsername());
        assertEquals(BIRTHDATE, dto.getBirthDate());
        assertEquals(COUNTRY_LOWERCASE, dto.getCountryOfResidence());
        assertEquals(PHONE_NUMBER, dto.getPhoneNumber());
        assertEquals(GENDER, dto.getGender());
    }
}
