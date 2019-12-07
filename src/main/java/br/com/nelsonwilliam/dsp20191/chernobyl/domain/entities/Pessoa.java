package br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums.CargoEnum;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profissional")
public class Pessoa {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 2, max = 128)
    private String nome;

    @NotNull
    private CargoEnum cargo;

    @Size(max = 1048576)
    private String image = null;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "diretor", orphanRemoval = true)
    private List<Filme> filmesDirigidos = new ArrayList<>();

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
