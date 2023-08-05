package org.example.converter;

import org.example.command.AdminCommand;
import org.example.entity.users.Admin;
import org.example.security.PasswordHash;

import java.security.NoSuchAlgorithmException;

public class AdminCommandToAdminConverter implements BaseConverter<AdminCommand, Admin> {

    PasswordHash passwordHash = new PasswordHash();

    @Override
    public Admin convert(AdminCommand adminCommand) throws NoSuchAlgorithmException {
        Admin admin = new Admin();
        admin.setFirstName(adminCommand.getFirstName());
        admin.setLastName(adminCommand.getLastName());
        admin.setEmail(adminCommand.getEmail());
        admin.setPassword(passwordHash.createHashedPassword(adminCommand.getPassword()));
        admin.setSignUpDate(adminCommand.getSignUpDate());
        admin.setUserStatus(adminCommand.getUserStatus());
        return admin;
    }

    @Override
    public AdminCommand convert(Admin admin) throws NoSuchAlgorithmException {
        return null;
    }
}
