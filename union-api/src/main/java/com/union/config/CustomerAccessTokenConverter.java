package com.union.config;

import com.zz.common.dto.TokenUserDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

public class CustomerAccessTokenConverter extends DefaultAccessTokenConverter {
    private Collection<? extends GrantedAuthority> defaultAuthorities;
    public CustomerAccessTokenConverter() {
        super.setUserTokenConverter(new CustomerUserAuthenticationConverter());
    }
    private class CustomerUserAuthenticationConverter extends DefaultUserAuthenticationConverter {

        @Override
        public Authentication extractAuthentication(Map<String, ?> map) {
            if (map.containsKey(USERNAME)) {
                Collection<? extends GrantedAuthority> authorities = this.getAuthorities(map);
                return new UsernamePasswordAuthenticationToken(TokenUserDTO.toDTO(map), "N/A", authorities);
            } else {
                return null;
            }
        }

        private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
            if (!map.containsKey(AUTHORITIES)) {
                return defaultAuthorities;
            }
            Object authorities = map.get(AUTHORITIES);
            if (authorities instanceof String) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
            }
            if (authorities instanceof Collection) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                        .collectionToCommaDelimitedString((Collection<?>) authorities));
            }
            throw new IllegalArgumentException("Authorities must be either a String or a Collection");
        }

    }

}
