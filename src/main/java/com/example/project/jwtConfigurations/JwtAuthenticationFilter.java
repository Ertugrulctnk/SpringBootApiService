package com.example.project.jwtConfigurations;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        // 1. Adım: Gelen istekteki token'ı alıyoruz.
        String token = jwtUtil.extractToken(request);  // Token almak için extractToken metodu kullanılıyor.

        // 2. Adım: Eğer token varsa ve geçerliyse, token'ı doğruluyoruz.
        if (token != null && jwtUtil.isTokenValid(token)) {
            // 3. Adım: Token geçerliyse, token'dan kullanıcı adını alıyoruz.
            String username = jwtUtil.extractUsername(token);  // Kullanıcı adını token'dan alıyoruz.

            // 4. Adım: Kullanıcıyı veritabanından yüklüyoruz (UserDetails ile).
            var userDetails = userDetailsService.loadUserByUsername(username);

            // 5. Adım: Kullanıcıyı güvenlik bağlamına (SecurityContext) ekliyoruz.
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);  // Authentication'ı SecurityContext'e set ediyoruz.
        }

        // 6. Adım: Filtreyi bir sonraki filtreye geçiriyoruz.
        filterChain.doFilter(request, response);
    }
}
