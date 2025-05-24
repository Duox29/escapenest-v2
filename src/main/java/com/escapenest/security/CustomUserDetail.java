package com.escapenest.security;

import lombok.*;
import lombok.experimental.FieldDefaults;
import com.escapenest.entity.User;
import com.escapenest.model.enums.Gender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomUserDetail implements UserDetails {
    User user;

    public String getNameCurrent() {
        return this.user.getName();
    }
    public Integer getId() {
        return this.user.getId();
    }
    public String getPhoneNumber() {
        return this.user.getPhoneNumber();
    }
      public Gender getGender() {
        return this.user.getGender();
    }
    public LocalDate getBirthDay() {
        return this.user.getBirthDay();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_"+ user.getUserRole().getValue());
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnable();
    }
}
