package com.example.hangouts;

public class Contact {

    private String id;
    private String name;
    private String company;
    private String phone;
    private String email;
    private String note;

    public Contact(String id, String name, String company, String phone, String email, String note) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.phone = phone;
        this.email = email;
        this.note = note;
    }

    // getters, setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

}