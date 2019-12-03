package br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums;

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
