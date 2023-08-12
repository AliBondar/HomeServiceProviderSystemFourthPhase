package org.example.service;

import org.example.entity.Service;

import java.util.Optional;

public interface ServiceService {

    Optional<Service> findServiceByName(String name);
}
