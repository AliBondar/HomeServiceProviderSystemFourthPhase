package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.SubServiceDTO;
import org.example.entity.SubService;
import org.example.mapper.SubServiceMapper;
import org.example.repository.SubServiceRepository;
import org.example.service.SubServiceService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SubServiceServiceImpl implements SubServiceService {

    private final SubServiceRepository subServiceRepository;
    private final  SubServiceMapper subServiceMapper = new SubServiceMapper();

    @Override
    public void save(SubServiceDTO subServiceDTO) {
        SubService subService = subServiceMapper.convert(subServiceDTO);
        subServiceRepository.save(subService);
    }

    @Override
    public void delete(SubServiceDTO subServiceDTO) {
        SubService subService = subServiceMapper.convert(subServiceDTO);
        subServiceRepository.delete(subService);
    }

    @Override
    public SubServiceDTO findById(Long id) {
        Optional<SubService> subService = subServiceRepository.findById(id);
        return subService.map(subServiceMapper::convert).orElse(null);
    }

    @Override
    public List<SubServiceDTO> findAll() {
        List<SubService> subServices = subServiceRepository.findAll();
        List<SubServiceDTO> subServiceDTOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(subServices)) return null;
        else {
            for (SubService subService : subServices){
                subServiceDTOList.add(subServiceMapper.convert(subService));
            }
            return subServiceDTOList;
        }
    }

    @Override
    public Optional<SubService> findByDescriptionAndService(String description, org.example.entity.Service service) {
        try {
            return subServiceRepository.findByDescriptionAndService(description, service);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<SubService> findByExpertId(Long id) {
        return subServiceRepository.findByExpertId(id);
    }

    @Override
    public List<SubService> findSubServicesByServiceId(Long id) {
        return subServiceRepository.findSubServicesByServiceId(id);
    }

    @Override
    public Optional<SubService> findSubServiceByDescription(String description) {
        try {
         return subServiceRepository.findSubServiceByDescription(description);
        }catch (Exception e) {
            return Optional.empty();
        }
    }

}
