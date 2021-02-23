package com.mailsoft.security.jwt;

import com.mailsoft.domain.User;
import com.mailsoft.repository.UserRepository;
import com.mailsoft.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TokenProvider {

  private final String secretKey;

  private final long tokenValidityInMilliseconds;

  private final UserDetailsService userService;

  @Autowired
  private UserRepository userRepository;

  public TokenProvider(UserDetailsService userService) {
    this.secretKey = Base64.getEncoder().encodeToString(SecurityConstants.SECRET.getBytes());
    this.tokenValidityInMilliseconds = SecurityConstants.EXPIRATION_TIME;
    //this.tokenValidityInMilliseconds = 60000;  //one minute
    this.userService = userService;
  }

  public String createToken(String username) {
    Date now = new Date();
    Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

    User user = userRepository.findByLoginOrEmail(username);

    return Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(username)
            .setIssuedAt(now).signWith(SignatureAlgorithm.HS512, this.secretKey)
            .setExpiration(validity).claim("id", user.getId())
            .claim("nom", user.getNom())
            .claim("prenom", user.getPrenom())
            .claim("role", user.getRole().getName()).compact();
  }

  public Authentication getAuthentication(String token) {
    String username = Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token)
        .getBody().getSubject();
    UserDetails userDetails = this.userService.loadUserByUsername(username);

    return new UsernamePasswordAuthenticationToken(userDetails, "",
        userDetails.getAuthorities());
  }

}