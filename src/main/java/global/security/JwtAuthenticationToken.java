package global.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private String jwt; // credentials
    private UserDetails UserDetails; // principal

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String jwt) {
        super(authorities);
        this.jwt = jwt;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, UserDetails UserDetails) {
        super(authorities);
        this.UserDetails = UserDetails;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }

    @Override
    public Object getPrincipal() {
        return UserDetails;
    }
}
