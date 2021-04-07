package com.secury.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        final String JWT_SECRET = "chuoi_bi_mat";

        //catch token from header of request
        String headerAuthorization = request.getHeader("Authorization");
        System.out.println("JWTAuthorizationFilter headerAuthorization: " + headerAuthorization);


        //check token is attached with request?
        //check format (Start by bearer)
        if(headerAuthorization != null && headerAuthorization.startsWith("Bearer")){
            try{
                //Bearer is insteaded of "" to take token
                String token = headerAuthorization.replace("Bearer", "");
                System.out.println("JWTAuthorizationFilter: " + token);

                //take email from token
                String email = Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token)
                        .getBody().getSubject();
                System.out.println("Email after resolve token: "+ email);

                //take information from DB
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authenticationToken = new
                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            catch (ExpiredJwtException e){
                System.out.println("Token expired ");
            }
        }
        chain.doFilter(request, response);
    }

}
