package com.fgreau.offertechtest.services;

import com.fgreau.offertechtest.dtos.UserDto;
import com.fgreau.offertechtest.mappers.UserMapper;
import com.fgreau.offertechtest.models.User;
import com.fgreau.offertechtest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

/**
 * Service methods associated with user manipulation.
 */
@Service
public class UserService {

    private static final HttpStatusCode BAD_REQUEST = HttpStatusCode.valueOf(400);

    private static final HttpStatusCode CONFLICT = HttpStatusCode.valueOf(409);

    private static final String MISSING_FIELD_ERROR = "Field %s is required";

    private static final String USERNAME_ALREADY_EXISTS_ERROR = "This username is not available";

    private static final String INVALID_COUNTRY_ERROR = "The user must live in France";

    private static final String AGE_ERROR = "The user must be over 18";

    private static final String REQUIRED_FIELD_USERNAME = "username";

    private static final String REQUIRED_FIELD_BIRTHDATE = "birthDate";

    private static final String REQUIRED_FIELD_COUNTRY_OF_RESIDENCE = "countryOfResidence";

    private static final String REQUIRED_COUNTRY = "france";

    private static final int ADULT_AGE = 18;

    /**
     * User mapper.
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * User repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Registers a new user to the database.
     *
     * @param userDto new user
     * @return registered user
     */
    public UserDto registerUser(final UserDto userDto) {
        validateUser(userDto);
        final User registeredUser = userRepository.save(userMapper.map(userDto));
        return userMapper.map(registeredUser);
    }

    /**
     * Checks if all the user data are valid.
     *
     * @param userDto new user
     */
    private void validateUser(final UserDto userDto) {
        // Required fields
        if (userDto.getUsername() == null) {
            throw new ResponseStatusException(BAD_REQUEST, String.format(MISSING_FIELD_ERROR, REQUIRED_FIELD_USERNAME));
        }

        if (userDto.getBirthDate() == null) {
            throw new ResponseStatusException(BAD_REQUEST, String.format(MISSING_FIELD_ERROR, REQUIRED_FIELD_BIRTHDATE));
        }

        if (userDto.getCountryOfResidence() == null) {
            throw new ResponseStatusException(BAD_REQUEST, String.format(MISSING_FIELD_ERROR, REQUIRED_FIELD_COUNTRY_OF_RESIDENCE));
        }

        // Username must be available
        if (userRepository.existsById(userDto.getUsername())) {
            throw new ResponseStatusException(CONFLICT, USERNAME_ALREADY_EXISTS_ERROR);
        }

        // Country of residence must be France
        if (!REQUIRED_COUNTRY.equalsIgnoreCase(userDto.getCountryOfResidence())) {
            throw new ResponseStatusException(BAD_REQUEST, INVALID_COUNTRY_ERROR);
        }

        // Age must be over 18
        if (LocalDate.now().minusYears(ADULT_AGE).isBefore(userDto.getBirthDate())) {
            throw new ResponseStatusException(BAD_REQUEST, AGE_ERROR);
        }
    }
}
