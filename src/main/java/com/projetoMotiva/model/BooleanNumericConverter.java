package com.projetoMotiva.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * As tabelas do Oracle guardam boolean como NUMBER(1): 1 = true, 0 = false.
 * Este conversor garante o mapeamento correto em qualquer versão do Oracle.
 */
@Converter
public class BooleanNumericConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean valor) {
        return Boolean.TRUE.equals(valor) ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer valor) {
        return valor != null && valor != 0;
    }
}