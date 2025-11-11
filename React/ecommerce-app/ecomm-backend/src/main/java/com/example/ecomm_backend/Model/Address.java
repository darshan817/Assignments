package com.example.ecomm_backend.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Address {
    @Id
    int ID;
    int userID;
    String street;
    String city;
    String state;
    String zipCode;
    String country;
    String addressType; 
    String phoneNumber;
    String email;
    String date;
    String time;
    String name;
}
