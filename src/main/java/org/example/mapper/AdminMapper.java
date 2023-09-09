package org.example.mapper;

import org.example.dto.AdminDTO;
import org.example.entity.users.Admin;
import org.example.entity.users.enums.Role;
import org.example.security.PasswordHash;

import java.security.NoSuchAlgorithmException;

public class AdminMapper implements BaseMapper<AdminDTO, Admin> {

    PasswordHash passwordHash = new PasswordHash();

    @Override
    public Admin convert(AdminDTO adminDTO) {
        Admin admin = new Admin();
        admin.setFirstName(adminDTO.getFirstName());
        admin.setLastName(adminDTO.getLastName());
        admin.setEmail(adminDTO.getEmail());
        admin.setPassword(adminDTO.getPassword());
        admin.setSignUpDate(adminDTO.getSignUpDate());
        admin.setUserStatus(adminDTO.getUserStatus());
        admin.setWallet(adminDTO.getWallet());
        admin.setRole(Role.ADMIN);
        return admin;
    }

    @Override
    public AdminDTO convert(Admin admin) throws NoSuchAlgorithmException {
        return null;
    }
}
