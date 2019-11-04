package br.com.nelsonwilliam.dsp20191.chernobyl.business.entity;

import javax.persistence.*;

@Entity
@Table(name = "avalfilme")
public class AvaliacaoFilme {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    private Filme filme;

    /**
     * Porcentagem representando o "nível de radiacao" dado a um filme por um usuário. De 0 (não avaliado) a 5.
     */
    private int grauRadiacao = 0;

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

    public int getGrauRadiacao() {
        return grauRadiacao;
    }

    public void setGrauRadiacao(int grauRadiacao) {
        this.grauRadiacao = grauRadiacao;
    }
}
