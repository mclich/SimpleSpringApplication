package com.github.mclich.ssa.model;

import org.springframework.util.StringUtils;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum Status
{
    ACTIVE(true), INACTIVE(false);

    private final boolean status;

    Status(boolean status)
    {
        this.status=status;
    }

    public boolean getValue()
    {
        return this.status;
    }

    @Override
    public String toString()
    {
        return StringUtils.capitalize(this.name().toLowerCase());
    }

    @Converter
    public static class StatusConverter implements AttributeConverter<Status, Boolean>
    {
        @Override
        public Boolean convertToDatabaseColumn(Status status)
        {
            return status.getValue();
        }

        @Override
        public Status convertToEntityAttribute(Boolean status)
        {
            return status?Status.ACTIVE:Status.INACTIVE;
        }
    }
}