package com.example.cms;

public class UserProfile {
    public String username;
    public String userid;
    public String useremail;
    public String department;
    public String mobile;
    public String gender;
    public String type;
    public String subject;
    public String specify;
    public String reference_id;

    public UserProfile() {
    }

    public UserProfile(String username, String userid, String department, String mobile, String useremail, String gender, String type) {
        this.username = username;
        this.userid = userid;
        this.department = department;
        this.mobile = mobile;
        this.useremail = useremail;
        this.gender = gender;
        this.type = type;

    }

    public UserProfile(String username, String userid, String department, String mobile, String useremail) {
        this.username = username;
        this.userid = userid;
        this.department = department;
        this.mobile = mobile;
        this.useremail = useremail;
    }

    /*
    public UserProfile(String subject, String specify, String reference_id) {
        this.subject = subject;
        this.specify = specify;
        this.reference_id = reference_id;
    }
    */

    public UserProfile(String subject, String specify) {
        this.subject = subject;
        this.specify = specify;
        this.reference_id = reference_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDepartment() {
        return department;
    }

    public String getMobile() {
        return mobile;
    }

    public String getUseremail() {
        return useremail;
    }

    public String getGender() {
        return gender;
    }

    public String getType() {
        return type;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getSubject() {
        return subject;
    }

    public String getSpecify() {
        return specify;
    }

    public String getReference_id() {
        return reference_id;
    }

}
