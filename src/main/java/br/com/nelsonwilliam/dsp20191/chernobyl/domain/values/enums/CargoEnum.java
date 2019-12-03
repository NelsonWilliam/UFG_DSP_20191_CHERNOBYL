package br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums;

public enum CargoEnum {

    ATOR("Ator"),
    DIRETOR("Diretor");

    private String nome;

    private CargoEnum(String nome) {
        this.nome = nome;
    }

    public static CargoEnum fromNome(String nome) {
        CargoEnum[] values = CargoEnum.values();
        for (CargoEnum value : values) {
            if (value.getNome().equals(nome)) {
                return value;
            }
        }
        return null;
    }

    public String getNome() {
        return this.nome;
    }

}
