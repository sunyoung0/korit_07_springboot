package com.example.demo.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.util.Date;

@Service
public class JwtService {
    static final long EXPIRATION_TIME = 86400000;
    static final String PREFIX = "Bearer";
    static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // HS256 해싱 알고리즘에 맞는 비밀 키(Secret Key)를 자동 생성해서 key 변수에 저장.

    // 서명된 JWT 토큰 생성
    public String getToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)    // JWT 등을 만들 때, 서명을 생성하기 위해 위에서 생성한 비밀 키(key)를 사용함. 서명을 위한 비밀 키를 생성하고, 그 키를 가지고 서명을 함
                .compact();

    }

    // 요청 Header에서 토큰을 파싱하여 사용자 이름 가져오기
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            return Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(PREFIX, "").trim())
                    .getBody()
                    .getSubject();
        }
        return null;
    }
}
