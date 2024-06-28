package com.msr.cg.afrimeta.security;


import com.msr.cg.afrimeta.clientUser.ClientUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

public class MyUserPrincipal implements UserDetails {

    private final ClientUser clientUser;

    public MyUserPrincipal(ClientUser clientUser) {
        this.clientUser = clientUser;
    }

    /**
     * l'ajout d'un role suplémentaire doit être impérativement suivit d'un espace
     * exemple role="admin" role="super user"
     *
     * Si l'utilisateur à un role = "admin super",la fonction renvera [ROLE_admin, ROLE_super]
     *
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(StringUtils.tokenizeToStringArray(this.clientUser.getRole(), " "))
                .map(role-> new SimpleGrantedAuthority("ROLE_"+role)).toList();
    }

    @Override
    public String getPassword() {
        return this.clientUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.clientUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.clientUser.isEnable();
    }

    public ClientUser getClientUser() {
        return clientUser;
    }
}
