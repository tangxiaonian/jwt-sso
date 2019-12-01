package com.tang.leyou.auth.common;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @Classname JWTHelper
 * @Description [ TODO ]
 * @Author Tang
 * @Date 2019/10/31 19:16
 * @Created by ASUS
 */
public class JwtHelper {

    /**
     私钥
     */
    private static final String PRIVATE_KEY = "7786df7fc3a34e26a61c034d5ec8245d";


    /**
     * MethodName parseJWT
     * Description [ 解析jwt字符串 ]
     * Date 2019/10/31 22:12
     * Param [jwts]
     * return
     **/
    public static Jws<Claims> parseJwt(String jwtString) {

        Key key = generalKey();

        try {

            return Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(jwtString);

        } catch (ExpiredJwtException e) {

            System.out.println( "token 过期了...." );

            return null;
        }

    }

    /**
     * MethodName parseJwt
     * Description [  ]
     * Date 2019/11/28 21:19
     * Param [jwtString, publicKey: 解密的公钥]
     * return
     **/
    public static Jws<Claims> parseJwt(String jwtString, PublicKey publicKey) {

        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(jwtString);
    }

    /**
     * MethodName getInfoFromToken
     * Description [ 获取token信息，并进行解析 ]
     * Date 2019/11/28 21:32
     * Param [token, publicKey]
     * return
     **/

    public static UserInfo getInfoFromToken(String token,PublicKey publicKey) {

        Jws<Claims> claimsJws = null;

        try {

           claimsJws = parseJwt(token, publicKey);

        } catch (ExpiredJwtException e) {

            System.out.println( "token 解析失败...." );

            return null;
        }

        // 自定义逻辑
        String id = claimsJws.getBody().get("id").toString();

        String username = (String) claimsJws.getBody().get("username");

        return new UserInfo(Long.valueOf(id),username);
    }

    /**
     * MethodName createJWT
     * Description [ 生成加密的jwt token ]
     * Date 2019/10/31 22:12
     * Param [map:要封装到body的数据, second:几秒后过期]
     * return jwt string
     **/
    public static String createJwt(Map<String,Object> map, Integer second){

        long currentTimeMillis = Calendar.getInstance().getTimeInMillis();

        JwtBuilder jwtBuilder = Jwts.builder();

        jwtBuilder
                // 设置自定义body数据,可以存放用户的一些信息
                .setClaims(map)

                // iat: 签发时间
                .setIssuedAt(new Date())

                // 设置主体 jwt面向的用户信息
                .setSubject("{'name':'tang'}")
                //  私钥
                .signWith(generalKey(), SignatureAlgorithm.HS256);

        if (second > 0) {

            long expiration = 1000 * second;

            //    设置过期时间
            jwtBuilder.setExpiration(new Date(currentTimeMillis + expiration));

        }

        return jwtBuilder. compact();
    }

    /**
     * MethodName createJwt
     * Description [  ]
     * Date 2019/11/28 21:18
     * Param [map, second, privateKey: 私钥]
     * return
     **/
    public static String createJwt(Map<String,Object> map, Integer second, PrivateKey privateKey){

        long currentTimeMillis = Calendar.getInstance().getTimeInMillis();

        JwtBuilder jwtBuilder = Jwts.builder();

        jwtBuilder
                // 设置自定义body数据,可以存放用户的一些信息
                .setClaims(map)

                // iat: 签发时间
                .setIssuedAt(new Date())

                // 设置主体 jwt面向的用户信息
                .setSubject("{'name':'tang'}")
                //  私钥
                .signWith(privateKey, SignatureAlgorithm.RS256);

        if (second > 0) {

            long expiration = 1000 * second;

            //    设置过期时间
            jwtBuilder.setExpiration(new Date(currentTimeMillis + expiration));

        }

        return jwtBuilder. compact();
    }


    /***
     * MethodName generalKey
     * Description [ 生成秘钥的方法 ]
     * Date 2019/10/31 20:32
     * Param []
     * return Key
     **/
    private static Key generalKey(){

        return Keys.hmacShaKeyFor(PRIVATE_KEY.getBytes());
    }

}