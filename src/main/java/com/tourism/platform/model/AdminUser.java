package com.tourism.platform.model;

// Student 4: Admin Management (Inheritance)
public class AdminUser extends User {
    private String adminRole; // Specific role like 'Manager', 'Editor'

    public AdminUser() {
        super();
        this.role = "ADMIN";
    }

    public AdminUser(String user, String name, String pass, String ph, String country, String bday, String nic, String gender, String role) {
        super(user, name, pass, ph, country, bday, nic, gender);
        this.role = "ADMIN";
        this.adminRole = role;
    }

    public String getAdminRole() { return adminRole; }
    public void setAdminRole(String adminRole) { this.adminRole = adminRole; }

    @Override public String toString() {
        return super.toString() + "|" + adminRole;
    }
}
