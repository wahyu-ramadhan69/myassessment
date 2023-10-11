package com.recruitment.myassessment.core.security;

import com.recruitment.myassessment.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtility jwtUtility;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;
        String userName = null;

        try {
            /*
             * Langkah pertama otentikasi token
             */
            if (authorization != null && authorization.startsWith("Bearer ")) {
                token = authorization.substring(7);// memotong setelah kata Bearer+spasi = 7 digit
                userName = jwtUtility.getUsernameFromToken(token);
            }

            /*
             * Jika langkah pertama berhasil (userName nya di dapat dari token maka lanjut
             * ke langkah ini)
             */
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtility.validateToken(token)) {
                    final UserDetails userDetails = new User(userName, "", new ArrayList<>());
                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null,
                            userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredJwtException e) {
            refreshToken(request, response);
        }

        filterChain.doFilter(request, response);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        /*
         * jika masuk ke method ini otomatis akan mengembalikan response ke client
         * dengan error code "X-JWT-777"
         * lalu dari sisi client akan meminta token kembali
         */
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}