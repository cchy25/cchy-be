package hackerthon.cchy.cchy25.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String accessToken = authorizationHeader.substring(7);
                var authentication = authenticationManager
                        .authenticate(new JwtAuthenticationToken(null, accessToken));
                if(authentication.isAuthenticated()){
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (Exception ex) {
            //TODO : UsernanmeNotFount 예외처리 필요

        }
        filterChain.doFilter(request, response);
    }
}
