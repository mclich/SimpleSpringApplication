package com.github.mclich.ssa.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.stream.Collectors;

public enum Role implements GrantedAuthority
{
    USER, MODERATOR, ADMIN;

    public static String arrayToString(Collection<Role> roles)
    {
        return roles.stream().map(Role::toString).sorted(String::compareToIgnoreCase).collect(Collectors.joining(", "));
    }

    @Override
    public String getAuthority()
    {
        return this.name();
    }

    @Override
    public String toString()
    {
        return StringUtils.capitalize(this.name().toLowerCase());
    }
}