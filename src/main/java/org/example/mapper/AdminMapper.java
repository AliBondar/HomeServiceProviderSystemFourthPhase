package org.example.mapper;

import org.example.dto.AdminDTO;
import org.example.entity.users.Admin;
import org.example.security.PasswordHash;

import java.security.NoSuchAlgorithmException;

public class AdminMapper implements BaseConverter<AdminDTO, Admin> {

    PasswordHash passwordHash = new PasswordHash();

    @Override
    public Admin convert(AdminDTO adminDTO) throws NoSuchAlgorithmException {
        Admin admin = new Admin();
        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(passwordHash.createHashedPassword(adminDTO.getPassword()));
        admin.setSignUpDate(adminDTO.getSignUpDate());
        admin.setUserStatus(adminDTO.getUserStatus());
        return admin;
    }

    @Override
    public AdminDTO convert(Admin admin) throws NoSuchAlgorithmException {
        return null;
    }
}
