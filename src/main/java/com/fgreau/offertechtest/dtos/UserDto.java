package com.fgreau.offertechtest.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO of User.
 */
public class UserDto {

    /**
     * The username.
     * Must be unique.
     */
    @NotNull
    private String username;

    /**
     * The user's birth date.
     */
    @NotNull
    private LocalDate birthDate;

    /**
     * The user's country of residence.
     * As there is no specific usage yet, there is no format constraint.
     */
    @NotNull
    private String countryOfResidence;

    /**
     * The user's phone number.
     * As there is no specific usage yet, there is no format constraint.
     */
    private String phoneNumber;

    /**
     * The user's specified gender.
     * As there is no specific usage yet, there is no format constraint.
     * Could be updated to use enum values, like {MALE/FEMALE/OTHER}.
     */
    private String gender;

    /**
     * Default constructor with non-nullable fields.
     *
     * @param username username
     * @param birthDate birth date
     * @param countryOfResidence country of residence
     */
    public UserDto(final @NotNull String username, final @NotNull LocalDate birthDate, final @NotNull String countryOfResidence) {
        this.username = username;
        this.birthDate = birthDate;
        this.countryOfResidence = countryOfResidence;
    }

    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(final @NotNull String username) {
        this.username = username;
    }

    public @NotNull LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final @NotNull LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public @NotNull String getCountryOfResidence() {
        return countryOfResidence;
    }

    public void setCountryOfResidence(final @NotNull String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(final String gender) {
        this.gender = gender;
    }
}
