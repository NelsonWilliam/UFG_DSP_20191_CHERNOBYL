package br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Representa a avaliação de um tópico de radioatividade postado por um usuário, na página de um filme.
 */
@Entity
@Table(name = "avaltopico")
public class AvaliacaoTopico {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Representa se a avaliação de um usuário ao tópico foi positiva ou negativa.
     */
    private boolean positiva;

    /**
     * Usuário que avalia o tópico postado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    /**
     * Tópico de radioatividade de um filme, a ser avaliado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Topico topico;

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

    public Topico getTopico() {
        return topico;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
    }

    public boolean isPositiva() {
        return positiva;
    }

    public void setPositiva(boolean positiva) {
        this.positiva = positiva;
    }

}
