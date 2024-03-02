package com.qaroni.library.application.domain.service;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user = null;

        try {
            //use a rest service to find the user.
            //Spring security provides user login name in authentication.getPrincipal()
            user = userDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        } catch (Exception e) {
            throw new UsernameNotFoundException(String.format("Invalid credentials", authentication.getPrincipal()));
        }

        if (user == null) {
            throw new UsernameNotFoundException(String.format("Invalid credentials", authentication.getPrincipal()));
        } else if (!user.isEnabled()) {
            throw new UsernameNotFoundException(String.format("Not found enabled user for username ", user.getUsername()));
        }
        //check user password stored in authentication.getCredentials() against stored password hash
        if (StringUtils.isBlank(authentication.getCredentials().toString())
                || !passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        Authentication result = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
