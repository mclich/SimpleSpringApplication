package com.github.mclich.ssa.model;

import com.github.mclich.ssa.Constants;
import com.github.mclich.ssa.model.Status.StatusConverter;
import com.github.mclich.ssa.validation.annotation.Login;
import com.github.mclich.ssa.validation.annotation.Name;
import com.github.mclich.ssa.validation.annotation.Password;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name="user")
public class User implements UserDetails
{
    private @Id @GeneratedValue(strategy=GenerationType.IDENTITY) Long id;
    private @Login String login;
    private @Password String password;
    private @Transient String cPassword;
    private @Email(regexp=Constants.EMAIL_REGEX) String email;
    private @Name String firstName;
    private @Name("surname") String lastName;
    private @NotNull(message="{"+Constants.GENDER_MSG+"}") Boolean gender;
    private @Convert(converter=StatusConverter.class) Status status;

    @ElementCollection(targetClass=Role.class, fetch=FetchType.EAGER)
    @CollectionTable(name="user_role", joinColumns=@JoinColumn(name="user_id"))
    @Column(name="role", nullable=false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public String getFullName()
    {
        return this.firstName+" "+this.lastName;
    }

    @Override
    public String getUsername()
    {
        return this.login;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return this.roles;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return this.status.getValue();
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return this.status.getValue();
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return this.status.getValue();
    }

    @Override
    public boolean isEnabled()
    {
        return this.status.getValue();
    }
}