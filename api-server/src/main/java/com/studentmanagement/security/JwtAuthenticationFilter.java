package com.studentmanagement.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Lấy header Authorization
        final String authHeader = request.getHeader("Authorization");

        // 2. Nếu không có header hoặc không bắt đầu bằng "Bearer " -> bỏ qua
        // Ko header=ko co token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Trích xuất token (bỏ "Bearer " ở đầu)
        final String jwt = authHeader.substring(7);

        try {
            // 4. Trích xuất username từ token
            final String username = jwtService.extractUsername(jwt);

            // 5. Nếu có username và chưa được xác thực
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 6. Load user từ DB
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // 7. Kiểm tra token có hợp lệ không
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // 8. Tạo Authentication token và nạp vào SecurityContext
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            // Token không hợp lệ hoặc hết hạn -> bỏ qua, để Spring Security xử lý 401
            System.err.println("JWT Filter Error: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
