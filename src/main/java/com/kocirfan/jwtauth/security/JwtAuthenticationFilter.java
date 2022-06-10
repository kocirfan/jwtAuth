package com.kocirfan.jwtauth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // giriş yapmak isteyen kullanıcı var mı
    // parent class tan iki metod alacağız
    // ovverride ederek kendi custom filterımızı kuracağız

    private AuthenticationManager authenticationManager;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       //sorguyu attığımız kullanıcı adını ve şifresini alalım
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //jwt token değil
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(token);

//        return super.attemptAuthentication(request, response);
    }

    //login success ise aşşağıdan devam
    // statless -> jwt işin için giriyor
    // successfull authentication olunca kullanıcıya access token döneceğiz bu bir jwt token olacak


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        // önemli burada secret verdiğimiz yer aslında uygulamamızın jwt verify kısmında gerekiyor
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        // bu algoritma ile jwt yaratalım
        String accessToken = JWT.create()
                .withSubject(user.getUsername()) // primary
                        .withExpiresAt(Date.from(Instant.now().plus(1, ChronoUnit.DAYS))) // ne zaman expire olsun? AccessToken -> login olduktan sonra attığımız sorgular için ullandığımız, RefreshToken -> access token expire olduktan sonra client tarafından sağlanır
                        .withIssuer(request.getRequestURL().toString())
                                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                        .sign(algorithm);
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
