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
     * Porcentagem representando o "nível de radiacao" dado a um filme por um usuário;
     */
    private Integer grauRadiacao;

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

    public Integer getGrauRadiacao() {
        return grauRadiacao;
    }

    public void setGrauRadiacao(Integer grauRadiacao) {
        this.grauRadiacao = grauRadiacao;
    }
}
