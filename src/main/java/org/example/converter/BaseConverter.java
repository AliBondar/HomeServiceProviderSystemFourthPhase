package org.example.converter;

import org.example.base.domain.BaseEntity;
import org.example.command.BaseCommand;

import java.security.NoSuchAlgorithmException;

public interface BaseConverter<C extends BaseCommand<Long>, T extends BaseEntity<Long>> {

    T convert (C c) throws NoSuchAlgorithmException;

    C convert(T t) throws NoSuchAlgorithmException;

}