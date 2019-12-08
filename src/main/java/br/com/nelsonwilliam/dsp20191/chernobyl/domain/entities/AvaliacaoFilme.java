package br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities;

import javax.persistence.*;

/**
 * Representa a avaliação de um Filme enviada por um usuário.
 */
@Entity
@Table(name = "avalfilme")
public class AvaliacaoFilme {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * Nota atribuída ao filme, de 1 a 5.
     */
    private int nota = 0;

    /**
     * Usuário que realiza a avaliação.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    /**
     * Filme a ser avaliado.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Filme filme;

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

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
