package com.fgreau.offertechtest.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO of User.
 */
@Data
@Builder
public class UserDto {

    /**
     * The username.
     * Must be unique.
     */
    @NotNull
    String username;

    /**
     * The user's birth date.
     */
    @NotNull
    LocalDate birthDate;

    /**
     * The user's country of residence.
     * As there is no specific usage yet, there is no format constraint.
     */
    @NotNull
    String countryOfResidence;

    /**
     * The user's phone number.
     * As there is no specific usage yet, there is no format constraint.
     */
    String phoneNumber;

    /**
     * The user's specified gender.
     * As there is no specific usage yet, there is no format constraint.
     * Could be updated to use enum values, like {MALE/FEMALE/OTHER}.
     */
    String gender;

}
