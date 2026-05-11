package tr.com.huseyinaydin.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class JwtAuthenticatedUser implements UserDetails {

    private final String username;
    private final String jwtToken;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtAuthenticatedUser(String username, String jwtToken, String role) {
        this.username = username;
        this.jwtToken = jwtToken;
        this.authorities = List.of(new SimpleGrantedAuthority(role));
    }

    public String getJwtToken() {
        return jwtToken;
    }

    @Override public String getUsername()               { return username; }
    @Override public String getPassword()               { return null; }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public boolean isAccountNonExpired()      { return true; }
    @Override public boolean isAccountNonLocked()       { return true; }
    @Override public boolean isCredentialsNonExpired()  { return true; }
    @Override public boolean isEnabled()                { return true; }
}
