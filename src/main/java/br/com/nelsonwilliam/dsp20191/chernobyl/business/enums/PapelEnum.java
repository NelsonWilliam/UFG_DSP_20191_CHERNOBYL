package br.com.nelsonwilliam.dsp20191.chernobyl.business.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum PapelEnum {

    ADMIN,
    USUARIO;

    public String getAuthority() {
        return "ROLE_" + getRole();
    }

    public String getRole() {
        return this.name();
    }
}
