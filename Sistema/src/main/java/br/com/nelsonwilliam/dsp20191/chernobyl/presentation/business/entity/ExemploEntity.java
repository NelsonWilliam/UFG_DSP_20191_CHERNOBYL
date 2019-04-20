package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "exemplo")
public class ExemploEntity {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String nome;

    private String texto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
