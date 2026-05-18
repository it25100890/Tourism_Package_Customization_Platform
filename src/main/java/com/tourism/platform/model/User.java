package com.tourism.platform.model;
import java.io.Serializable;

public class User implements Serializable {
    protected String username;
    protected String fullName;
    protected String password;
    protected String phone;
    protected String country;
    protected String birthday;
    protected String nicPassport;
    protected String gender;
    protected String role = "USER";

    public User() {}
    public User(String username, String fullName, String password, String phone, String country, String birthday, String nicPassport, String gender) {
        this.username = username; this.fullName = fullName; this.password = password;
        this.phone = phone; this.country = country; this.birthday = birthday;
        this.nicPassport = nicPassport; this.gender = gender;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }
    public String getNicPassport() { return nicPassport; }
    public void setNicPassport(String nicPassport) { this.nicPassport = nicPassport; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    @Override public String toString() {
        return username + "|" + fullName + "|" + password + "|" + phone + "|" + country + "|" + birthday + "|" + nicPassport + "|" + gender + "|" + role;
    }
}
