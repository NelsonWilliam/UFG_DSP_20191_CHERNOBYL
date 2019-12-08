package br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums.CargoEnum;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um profissional participante de algum filme, podendo ser diretor ou ator.
 */
@Entity
@Table(name = "profissional")
public class Pessoa {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 2, max = 128)
    private String nome;

    /**
     * Cargo do profissional, podendo ser diretor ou ator.
     */
    @NotNull
    private CargoEnum cargo;

    /**
     * Foto a representar a pessoa, devendo estar na base 64.
     */
    @Size(max = 1048576)
    private String image = null;

    /**
     * Lista de filmes digiridos pela pessoa "diretor".
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "diretor", orphanRemoval = true)
    private List<Filme> filmesDirigidos = new ArrayList<>();

    /**
     * Lista de filmes atuados pela pessoa "ator";
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "atores")
    private List<Filme> filmesAtuados = new ArrayList<>();

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
