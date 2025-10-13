package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        // 위의 User가 domain 패키지꺼로 잡혀있어서 이렇게 뜸
        org.springframework.security.core.userdetails.User.UserBuilder builder = null;
        if(user.isPresent()) {
            User currentUser = user.get();  // user.get()은 자료형이 Optional, current의 자료형은 User / 존재한다면 Optional을 User 자료형으로 바꿔줘야함(형변환이라고 할 수도 있음)
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(currentUser.getPassword());
            builder.roles(currentUser.getRole());
        } else {
            throw new UsernameNotFoundException("해당 User를 찾을 수 없습니다.");
        }
        return builder.build();
    }


}
