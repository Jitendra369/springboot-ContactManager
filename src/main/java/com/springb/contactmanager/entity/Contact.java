package com.springb.contactmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "contact")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contID;
    
    @NotEmpty
    @Size(min = 5, message = "minimum 5 letter should be their")
    private String contName;
    
    private String contSecondName;
    
    private String contWork;
    
   @NotEmpty
   @Email
    private String contEmail;
    
   @Size(min = 9, message = "Incorrect phone number")
    private String contPhone;
    private String contImage;
//    private MultipartFile contImage;
    
 
    @Column(length = 500)
    private String contDesc;

    @ManyToOne
    private User user;

    public Contact() {
    }

    public Contact(int contID, String contName, String contSecondName, String contWork, String contEmail,
            String contPhone, String contImage, String contDesc) {
        this.contID = contID;
        this.contName = contName;
        this.contSecondName = contSecondName;
        this.contWork = contWork;
        this.contEmail = contEmail;
        this.contPhone = contPhone;
        this.contImage = contImage;
        this.contDesc = contDesc;
    }

    

    public int getContID() {
        return contID;
    }

    public void setContID(int contID) {
        this.contID = contID;
    }

    public String getContName() {
        return contName;
    }

    public void setContName(String contName) {
        this.contName = contName;
    }

    public String getContSecondName() {
        return contSecondName;
    }

    public void setContSecondName(String contSecondName) {
        this.contSecondName = contSecondName;
    }

    public String getContWork() {
        return contWork;
    }

    public void setContWork(String contWork) {
        this.contWork = contWork;
    }

    public String getContEmail() {
        return contEmail;
    }

    public void setContEmail(String contEmail) {
        this.contEmail = contEmail;
    }

    public String getContPhone() {
        return contPhone;
    }

    public void setContPhone(String contPhone) {
        this.contPhone = contPhone;
    }

    public String getContImage() {
        return contImage;
    }

    public void setContImage(String contImage) {
        this.contImage = contImage;
    }

    public String getContDesc() {
        return contDesc;
    }

    public void setContDesc(String contDesc) {
        this.contDesc = contDesc;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    

//	public MultipartFile getContImage() {
//		return contImage;
//	}
//
//	public void setContImage(MultipartFile contImage) {
//		this.contImage = contImage;
//	}
//
//	@Override
//	public String toString() {
//		return "Contact [contID=" + contID + ", contName=" + contName + ", contSecondName=" + contSecondName
//				+ ", contWork=" + contWork + ", contEmail=" + contEmail + ", contPhone=" + contPhone + ", contImage="
//				+ contImage + ", contDesc=" + contDesc + ", user=" + user + "]";
//	}

	// this toString() method gives error, while printing the object by using contact object 
    // Error: stackOverflow error 

	// @Override
	// public String toString() {
	// 	return "Contact [contID=" + contID + ", contName=" + contName + ", contSecondName=" + contSecondName
	// 			+ ", contWork=" + contWork + ", contEmail=" + contEmail + ", contPhone=" + contPhone + ", contImage="
	// 			+ contImage + ", contDesc=" + contDesc + ", user=" + user + "]";
	// }

    @Override
    public boolean equals(Object obj) {
        return this.contID== ((Contact)obj).getContID();
    }

    @Override
	public String toString() {
		return "Contact [contID=" + contID + ", contName=" + contName + ", contSecondName=" + contSecondName
				+ ", contWork=" + contWork + ", contEmail=" + contEmail + ", contPhone=" + contPhone + ", contImage="
				+ contImage + ", contDesc=" + contDesc + " ]";
	}


}
