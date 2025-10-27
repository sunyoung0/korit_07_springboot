package com.shoppinglist.shoppinglist2.web;

import com.shoppinglist.shoppinglist2.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountCredentials credentials) {
        // 자격 증명으로 인증 토큰 생성하는 과정
        UsernamePasswordAuthenticationToken creds = new UsernamePasswordAuthenticationToken(credentials.username(), credentials.password());

        // 사용자 인증
        Authentication auth = authenticationManager.authenticate(creds);

        // JWT 생성
        String jwt = jwtService.generateToken(auth.getName());

        // JWT를 'Authorization' 헤더에 담아서 응답
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer ", jwt)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization") // FrontEnd에서 헤더를 읽을 수 있도록 설정
                .build();
    }

}
