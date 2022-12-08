package com.springb.contactmanager.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userID;

    @NotBlank
    @Size(min = 3, max = 50,message = "userName characters are between 3 to 12 character")
    private String userName;

//    todo: make the it true
    @Column(unique = false)
    @NotBlank
    @Size(min = 3, max = 50,message = "Email characters between are 3 to 50 character")
    private String userEmail;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 3, max = 100,message = "password character are between 3 to 50 character")
    private String password;

    
    private String userRole;

    private boolean userStatus;
    
    private String userImageUrl;

    @Column(length = 500)
    private String userDesc;

    // orphanRemoval if user is not their then contact will vanished

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user",orphanRemoval =true)
    private List<Contact> contactList = new ArrayList<>();

    public User() {
    }

    public User(int userID, String userName, String userEmail, String password, String userRole, boolean userStatus,
            String userImageUrl, String userDesc) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.userImageUrl = userImageUrl;
        this.userDesc = userDesc;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    @Override
    public String toString() {
        return "User [userID=" + userID + ", userName=" + userName + ", userEmail=" + userEmail + ", password="
                + password + ", userRole=" + userRole + ", userStatus=" + userStatus + ", userImageUrl=" + userImageUrl
                + ", userDesc=" + userDesc + ", contactList=" + contactList + "]";
    }


}
