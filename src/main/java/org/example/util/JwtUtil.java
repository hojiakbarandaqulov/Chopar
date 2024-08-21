package org.example.util;

import io.jsonwebtoken.*;
import org.example.dto.JwtDTO;
import org.example.enums.ProfileRole;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtUtil {
    private static final int tokenLiveTime = 1000 * 3600 * 96;
    private static final String secretKey = "secretKeydpigerpogijekmldflkvmd;fogvkjndfk.jdfnkjfnmduydghyufgbyugbudgbdufysdgbfsujedbhsduygdhubdnwduyhbdnweuhjbdkuysydfhbwee,usbndbsmebg";

    public static String encode(Long profileId, String username, ProfileRole role) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.issuedAt(new Date());

        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());

        jwtBuilder.signWith(sa, secretKeySpec);

        jwtBuilder.claim("id", profileId);
        jwtBuilder.claim("role", role);
        jwtBuilder.claim("username", username);

        jwtBuilder.expiration(new Date(System.currentTimeMillis() + tokenLiveTime));
        jwtBuilder.issuer("Chopar");
        return jwtBuilder.compact();
    }

    public static JwtDTO decode(String token) {

        SignatureAlgorithm sa = SignatureAlgorithm.HS512;
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), sa.getJcaName());

        JwtParser jwtParser = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build();
        Jws<Claims> jws = jwtParser.parseClaimsJws(token);
        Claims claims = jws.getPayload();

        String id = (String) claims.get("id");
        String username = (String) claims.get("username");
        String role = (String) claims.get("role");
        if (role != null) {
            ProfileRole profileRole = ProfileRole.valueOf(role);
            return new JwtDTO(id, username, profileRole);
        }
        return new JwtDTO(id);
    }

}
