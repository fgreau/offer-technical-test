package com.fgreau.offertechtest.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Registered user entity.
 */
@Entity
@Table(name = "data_user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 6695383790847736493L;

    /**
     * Unique username.
     */
    @Id
    @Column(name = "username")
    private String username;

    /**
     * Birth date.
     */
    @Column(name = "birth_date")
    @NotNull
    private LocalDate birthDate;

    /**
     * Country of residence.
     */
    @Column(name = "country_of_residence")
    @NotNull
    private String countryOfResidence;

    /**
     * Creation date.
     */
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    /**
     * Phone number.
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Gender.
     */
    @Column(name = "gender")
    private String gender;

    /**
     * Logical deletion.
     */
    @Column(name = "deleted")
    private boolean deleted;

    /**
     * Setting creation date upon creation.
     */
    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
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

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
    }
}
