package br.com.nelsonwilliam.dsp20191.chernobyl.business.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.CargoEnum;

@Entity
@Table(name = "profissional")
public class Profissional {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 2, max = 128)
    private String nome;

    @NotBlank
    private CargoEnum cargo;

    private Double grauRadioatividade;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "diretor")
    private List<Filme> filmesDirigidos;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "atores")
    private List<Filme> filmesAtuados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CargoEnum getCargo() {
        return cargo;
    }

    public void setCargo(CargoEnum cargo) {
        this.cargo = cargo;
    }

    public Double getGrauRadioatividade() {
        return grauRadioatividade;
    }

    public void setGrauRadioatividade(Double grauRadioatividade) {
        this.grauRadioatividade = grauRadioatividade;
    }

    public List<Filme> getFilmesDirigidos() {
        return filmesDirigidos;
    }

    public void setFilmesDirigidos(List<Filme> filmesDirigidos) {
        this.filmesDirigidos = filmesDirigidos;
    }

    public List<Filme> getFilmesAtuados() {
        return filmesAtuados;
    }

    public void setFilmesAtuados(List<Filme> filmesAtuados) {
        this.filmesAtuados = filmesAtuados;
    }

    // NOTA: Relacionamento no JPA:
    // ---
    // Veja nas entidades existentes que o Filme tem um atributo pro seu diretor e o Profissional tem um atributo pros
    // seus filmes dirigidos.
    // Em ambos os atributos do relacionamento é precisso que colocar as anotações
    // OneToMany/ManyToOne/ManyToMany/OneToOne corretamente.
    // Em APENAS UM deles tem que colocar o parâmetro "mappedBy" com o nome do OUTRO atributo do relacionamento.
    // -
    // Note também que lá em Filme tem uma lista de premiações. Como nesse caso premiações não é uma entidade,
    // é só um objeto qualquer (string), usa-se a notação "ElementCollection"

}
