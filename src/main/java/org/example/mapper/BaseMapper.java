package org.example.mapper;

import org.example.base.domain.BaseEntity;
import org.example.dto.BaseDTO;

import java.security.NoSuchAlgorithmException;

public interface BaseMapper<C extends BaseDTO<Long>, T extends BaseEntity<Long>> {

    T convert (C c) throws NoSuchAlgorithmException;

    C convert(T t) throws NoSuchAlgorithmException;

}