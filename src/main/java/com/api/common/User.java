package com.api.common;

import java.io.Serializable;

public class User implements Serializable {

    private String firstName;
    private String lastName;
    private String activationStatusCode;
    private String preferredLanguage;
    private String memberShipPlanPurchaseId;
    private String email;
    private String serviceArea;
    private String mobilePhone;
    private String guid;
    private String region;
    private String ssoSession;
    private String correlationID;

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

    public String getActivationStatusCode() {
        return activationStatusCode;
    }

    public void setActivationStatusCode(String activationStatusCode) {
        this.activationStatusCode = activationStatusCode;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getMemberShipPlanPurchaseId() {
        return memberShipPlanPurchaseId;
    }

    public void setMemberShipPlanPurchaseId(String memberShipPlanPurchaseId) {
        this.memberShipPlanPurchaseId = memberShipPlanPurchaseId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSsoSession() {
        return ssoSession;
    }

    public void setSsoSession(String ssoSession) {
        this.ssoSession = ssoSession;
    }

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    public String toString(){
        return "Logged In User has firstName "+this.firstName+", lastName "+this.lastName +
                ",Guid "+this.guid;
    }







}
