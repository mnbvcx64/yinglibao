package com.bjpowernode;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    /**00ca78c1f3ec4fa99571e0b33956df2a**/
    public void testCreateJwt(){
        Map<String, Object> data = new HashMap<>();
        data.put("UserId",1001);
        data.put("name","李四");
        data.put("role","经理");

        String key = "00ca78c1f3ec4fa99571e0b33956df2a";
        SecretKey secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        Date date = new Date();
        String jwt = Jwts.builder().signWith(SignatureAlgorithm.HS256,secretKey)
                .setExpiration(DateUtils.addMinutes(date,1))
                .setIssuedAt(date)
                .setId(UUID.randomUUID().toString())
                .addClaims(data).compact();
        System.out.println(key.equals(jwt));

    }

    public void ReadJwt(){

    }
}
