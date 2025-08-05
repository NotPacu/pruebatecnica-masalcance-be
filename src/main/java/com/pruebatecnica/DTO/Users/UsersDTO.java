package com.pruebatecnica.DTO.Users;

import lombok.Data;

@Data
public class UsersDTO {
    String id;
    String name;
    String username;
    String email;
    AddressDTO address;
    String phone;
    String website;
    CompanyDTO company;
}
