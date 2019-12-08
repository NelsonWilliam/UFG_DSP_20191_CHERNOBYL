package br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Representa a avaliação de uma resenha postada por um usuário, na página de um filme.
 */
@Entity
@Table(name = "avalresenha")
public class AvaliacaoResenha {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Representa se a avaliação de um usuário à resenha foi positiva ou negativa.
     */
    private boolean positiva;

    /**
     * Usuário que avalia a resenha postada.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    /**
     * Resenha de um filme, a ser avaliada.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Resenha resenha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Resenha getResenha() {
        return resenha;
    }

    public void setResenha(Resenha resenha) {
        this.resenha = resenha;
    }

    public boolean isPositiva() {
        return positiva;
    }

    public void setPositiva(boolean positiva) {
        this.positiva = positiva;
    }

}
