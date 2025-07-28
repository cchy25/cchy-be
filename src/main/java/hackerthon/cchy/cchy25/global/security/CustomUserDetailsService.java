package hackerthon.cchy.cchy25.global.security;

import hackerthon.cchy.cchy25.domain.auth.exception.AuthenticationErrorCode;
import hackerthon.cchy.cchy25.domain.auth.exception.AuthenticationException;
import hackerthon.cchy.cchy25.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findById(Long.parseLong(userId))
                .orElseThrow(()-> new AuthenticationException(AuthenticationErrorCode.INVALID_CREDENTIALS));
    }
}
