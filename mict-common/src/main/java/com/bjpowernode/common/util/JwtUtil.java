package com.bjpowernode.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class JwtUtil {

    private final String selfKey;

    public JwtUtil(String selfKey) {
        this.selfKey = selfKey;
    }

    public String createJwt(Map<String,Object> data, Integer minute) throws Exception{

        Date curDate = new Date();
        SecretKey secretKey = Keys.hmacShaKeyFor(selfKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder().signWith(secretKey, SignatureAlgorithm.HS256)
                .setExpiration(DateUtils.addMinutes(curDate,minute))
                .setIssuedAt(curDate)
                .setId(UUID.randomUUID().toString().replaceAll("-","").toUpperCase())
                .addClaims(data)
                .compact();
    }

    //读取Jwt
    public Claims readJwt(String jwt)throws Exception {
        SecretKey secretKey = Keys.hmacShaKeyFor(selfKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(jwt).getBody();
    }
}
