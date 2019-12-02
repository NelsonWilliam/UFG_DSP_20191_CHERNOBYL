package br.com.nelsonwilliam.dsp20191.chernobyl.service.dto;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.PessoaService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FilmeDto {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 128)
    private String titulo;

    @NotNull
    private Long idDiretor;

    private List<Long> idAtores = new ArrayList<>();

    private List<String> premiacoes = new ArrayList<>();

    private String image;

    public static FilmeDto fromFilme(Filme filme) {
        FilmeDto filmeDto = new FilmeDto();
        filmeDto.setId(filme.getId());
        filmeDto.setTitulo(filme.getTitulo());

        Pessoa diretor = filme.getDiretor();
        filmeDto.setIdDiretor(diretor == null ? null : diretor.getId());

        List<Long> idsAtores = new ArrayList<>();
        Collection<Pessoa> atores = filme.getAtores();
        for (Pessoa ator : atores) {
            idsAtores.add(ator.getId());
        }
        filmeDto.setIdAtores(idsAtores);
        filmeDto.setPremiacoes(filme.getPremiacoes());
        filmeDto.setImage(filme.getImage());
        return filmeDto;
    }

    public Filme toFilme(PessoaService pessoaService) {
        Filme filme = new Filme();
        filme.setId(getId());
        filme.setTitulo(getTitulo());

        filme.setDiretor(pessoaService.findById(getIdDiretor()));

        List<Pessoa> atores = new ArrayList<>();
        if (this.idAtores != null)
            for (Long idAtor : this.idAtores) {
                atores.add(pessoaService.findById(idAtor));
            }
        filme.setAtores(atores);
        filme.setPremiacoes(getPremiacoes());
        filme.setImage(getImage());
        return filme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getIdDiretor() {
        return idDiretor;
    }

    public void setIdDiretor(Long idDiretor) {
        this.idDiretor = idDiretor;
    }

    public List<Long> getIdAtores() {
        return idAtores;
    }

    public void setIdAtores(List<Long> idAtores) {
        this.idAtores = idAtores;
    }

    public List<String> getPremiacoes() {
        return premiacoes;
    }

    public void setPremiacoes(List<String> premiacoes) {
        this.premiacoes = premiacoes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
