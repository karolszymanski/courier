package com.szymanski.courierapp.service.impl;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.szymanski.courierapp.data.db.entity.User;
import com.szymanski.courierapp.data.db.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userDao;

  @Override
  public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {

    final User dbUser =
        this.userDao.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(login));

    return new org.springframework.security.core.userdetails.User(dbUser.getLogin(),
        dbUser.getPassword(), Collections.emptyList());
  }



}
