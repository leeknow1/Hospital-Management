package com.example.myhospital.config;

import com.example.myhospital.entity.UsersEntity;
import com.example.myhospital.repository.UsersEntityRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UsersEntityRepository usersEntityRepository;

    public CustomUserDetailService(UsersEntityRepository usersEntityRepository) {
        this.usersEntityRepository = usersEntityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsersEntity user = usersEntityRepository.findByEmail(email);

        if(user==null){
            throw new UsernameNotFoundException("User not found!");
        }

        return new CustomUserDetail(user);
    }
}