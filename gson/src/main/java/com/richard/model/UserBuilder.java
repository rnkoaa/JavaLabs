package com.richard.model;

/**
 * User: rnkoaa
 * Created: 2/20/15 8:57 PM
 */
public class UserBuilder {
    private String firstName;
    private String lastName;
    private String personalZip;
    private String password;
    private String personalEmail;

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder personalZip(String zipCode) {
        this.personalZip = zipCode;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder personalEmail(String emailAddress) {
        this.personalEmail = emailAddress;
        return this;
    }

    public User build() {
        return new User(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public String getPersonalZip() {
        return personalZip;
    }
}
