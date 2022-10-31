package com.retmix.shop.shop.jwt;

import com.retmix.shop.shop.model.User;
import com.retmix.shop.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsImpl")
public class UserDetailsImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user==null) throw new UsernameNotFoundException("User with email - "+email+" not found!");
        return SecurityUser.fromUser(user);
    }
}
