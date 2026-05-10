package com.example.demo.model;

public class Person {
    private final String id;
    private final String name;
    private final String country;
    private final String jobTitle;
    private final String company;
    private final String email;

    public Person(String id, String name, String country, String jobTitle, String company, String email) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.jobTitle = jobTitle;
        this.company = company;
        this.email = email;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCountry() { return country; }
    public String getJobTitle() { return jobTitle; }
    public String getCompany() { return company; }
    public String getEmail() { return email; }
}
