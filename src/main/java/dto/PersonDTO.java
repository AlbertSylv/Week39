/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Person;

/**
 *
 * @author baske
 */
public class PersonDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String street;
    private String zip;
    private String city;

    public PersonDTO(Person person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.phone = person.getPhone();
        this.street = person.getAddress().getStreet();
        this.zip = person.getAddress().getZip();
        this.city = person.getAddress().getCity();
    }

    public PersonDTO(String fn, String ln, String phone, String street, String zip, String city) {
        this.firstName = fn;
        this.lastName = ln;
        this.phone = phone;
        this.street = street;
        this.zip = zip;
        this.city = city;
    }

    public PersonDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
