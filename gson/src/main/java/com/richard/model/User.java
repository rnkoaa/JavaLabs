package com.richard.model;

import org.joda.time.DateTime;

import java.util.List;

/**
 * User: rnkoaa
 * Created: 2/19/15 8:50 PM
 */

public class User {
    private String id, firstName,
            lastName, jobTitle, bizName, bizEmail,
            bizAddress,
            bizZip, bizWebsite, bizPhone,
            personalZip, personalAddress,
            personalEmail, personalWebsite, personalPhone;
    private String shareAll; //this could be boolean
    private String connectedWithId;
    private String connectionType;
    private String personalConnectionGeoLocation;
    private String connectionId;
    private DateTime connectionCreated;

    private DateTime created;
    private UserNote userNote;

    private List<SocialNetwork> socialNetworks;
    private String password;

    public User() {
    }

    public User(UserBuilder userBuilder) {
        this.firstName = userBuilder.getFirstName();
        this.lastName = userBuilder.getLastName();
        this.personalEmail = userBuilder.getPersonalEmail();
        this.password = userBuilder.getPassword();
        this.personalZip = userBuilder.getPersonalZip();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getBizName() {
        return bizName;
    }

    public void setBizName(String bizName) {
        this.bizName = bizName;
    }

    public String getBizEmail() {
        return bizEmail;
    }

    public void setBizEmail(String bizEmail) {
        this.bizEmail = bizEmail;
    }

    public String getBizAddress() {
        return bizAddress;
    }

    public void setBizAddress(String bizAddress) {
        this.bizAddress = bizAddress;
    }

    public String getBizZip() {
        return bizZip;
    }

    public void setBizZip(String bizZip) {
        this.bizZip = bizZip;
    }

    public String getBizWebsite() {
        return bizWebsite;
    }

    public void setBizWebsite(String bizWebsite) {
        this.bizWebsite = bizWebsite;
    }

    public String getBizPhone() {
        return bizPhone;
    }

    public void setBizPhone(String bizPhone) {
        this.bizPhone = bizPhone;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public String getPersonalZip() {
        return personalZip;
    }

    public String getPassword() {
        return password;
    }
    /*
    initialValues.put("users_id", userJsonObject.getString("id"));
		    	initialValues.put("users_first_name", userJsonObject.getString("fn"));
		    	initialValues.put("users_last_name", userJsonObject.getString("ln"));
		    	initialValues.put("users_job_title", userJsonObject.getString("ti"));
		    	initialValues.put("users_biz_name", userJsonObject.getString("bn"));
		    	initialValues.put("users_biz_email", userJsonObject.getString("be"));
		    	initialValues.put("users_biz_address", userJsonObject.getString("ba"));
		    	initialValues.put("users_biz_zip", userJsonObject.getString("bz"));
		    	initialValues.put("users_biz_website", userJsonObject.getString("bw"));
		    	initialValues.put("users_biz_phone", userJsonObject.getString("bp"));
		    	initialValues.put("users_connection_type", connectionType); // Can't rely on the string "ct", as it's not present
		    																// when updating a user directly through QR or Flick.
	    		initialValues.put("users_pers_zip", userJsonObject.getString("pz"));
	    		initialValues.put("users_connection_pers_geolocation", userJsonObject.getString("pg"));
     */
}
