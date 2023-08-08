package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.repository.OfferRepository;
import org.example.service.OfferService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
}
