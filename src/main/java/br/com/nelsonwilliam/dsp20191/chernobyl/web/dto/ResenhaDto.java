package br.com.nelsonwilliam.dsp20191.chernobyl.web.dto;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoResenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Resenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.AvaliacaoResenhaService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.FilmeService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ResenhaDto {

    private Long id;

    private Long idAutor;

    private Long idFilme;

    @NotBlank
    @Size(min = 1, max = 500)
    private String texto;

    /**
     * Nulo se o usuário ainda não avaliou.
     */
    private Boolean minhaAvaliacao;

    private double mediaAvaliacao;

    public static ResenhaDto fromResenha(Resenha resenha) {
        ResenhaDto resenhaDto = new ResenhaDto();
        resenhaDto.id = resenha.getId();
        resenhaDto.idAutor = resenha.getAutor().getId();
        resenhaDto.idFilme = resenha.getFilme().getId();
        resenhaDto.texto = resenha.getTexto();
        resenhaDto.minhaAvaliacao = null;
        resenhaDto.mediaAvaliacao = -1.0;
        return resenhaDto;
    }

    public static ResenhaDto fromResenha(Resenha resenha, AvaliacaoResenha minhaAvaliacao, AvaliacaoResenhaService avaliacaoResenhaService) {
        ResenhaDto resenhaDto = new ResenhaDto();
        resenhaDto.id = resenha.getId();
        resenhaDto.idAutor = resenha.getAutor().getId();
        resenhaDto.idFilme = resenha.getFilme().getId();
        resenhaDto.texto = resenha.getTexto();
        resenhaDto.minhaAvaliacao = minhaAvaliacao == null ? null : minhaAvaliacao.isPositiva();
        resenhaDto.mediaAvaliacao = avaliacaoResenhaService.calcularPorcentagem(resenha.getId());
        return resenhaDto;
    }

    public Resenha toResenha(FilmeService filmeService, Usuario autor) {
        Resenha resenha = new Resenha();
        resenha.setId(id);
        resenha.setFilme(filmeService.findById(idFilme));
        resenha.setTexto(texto);
        resenha.setAutor(autor);
        return resenha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getMinhaAvaliacao() {
        return minhaAvaliacao;
    }

    public void setMinhaAvaliacao(Boolean minhaAvaliacao) {
        this.minhaAvaliacao = minhaAvaliacao;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public double getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(double mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public Long getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Long idFilme) {
        this.idFilme = idFilme;
    }
}
