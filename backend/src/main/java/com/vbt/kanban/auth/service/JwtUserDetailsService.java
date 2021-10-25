package com.vbt.kanban.auth.service;

import java.util.ArrayList;
import java.util.Optional;

import com.vbt.kanban.entity.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
  @Autowired
  private AuthServiceImpl authService;
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<Auth> user = authService.findByEmail(email);
    if (user.isPresent()) {
      if (!user.get().isActive()) {
         throw new UsernameNotFoundException("User is not actived");
      }
    } else {
       throw new UsernameNotFoundException("User not found");
    }

    return new User(user.get().getEmail(), user.get().getPassword(), new ArrayList<>());
  }

}
