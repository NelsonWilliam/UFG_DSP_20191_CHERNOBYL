package br.com.nelsonwilliam.dsp20191.chernobyl.web.dto;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoTopico;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Topico;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.AvaliacaoTopicoService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.FilmeService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TopicoDto {

    private Long id;

    private Long idAutor;

    private Long idFilme;

    @NotBlank
    @Size(min = 1, max = 100)
    private String texto;

    /**
     * Nulo se o usuário ainda não avaliou.
     */
    private Boolean minhaAvaliacao;

    private double mediaAvaliacao;

    public static TopicoDto fromTopico(Topico topico) {
        TopicoDto topicoDto = new TopicoDto();
        topicoDto.id = topico.getId();
        topicoDto.idAutor = topico.getAutor().getId();
        topicoDto.idFilme = topico.getFilme().getId();
        topicoDto.texto = topico.getTexto();
        topicoDto.minhaAvaliacao = null;
        topicoDto.mediaAvaliacao = -1.0;
        return topicoDto;
    }

    public static TopicoDto fromTopico(Topico topico, AvaliacaoTopico minhaAvaliacao, AvaliacaoTopicoService avaliacaoTopicoService) {
        TopicoDto topicoDto = new TopicoDto();
        topicoDto.id = topico.getId();
        topicoDto.idAutor = topico.getAutor().getId();
        topicoDto.idFilme = topico.getFilme().getId();
        topicoDto.texto = topico.getTexto();
        topicoDto.minhaAvaliacao = minhaAvaliacao == null ? null : minhaAvaliacao.isPositiva();
        topicoDto.mediaAvaliacao = avaliacaoTopicoService.calcularPorcentagem(topico.getId());
        return topicoDto;
    }

    public Topico toTopico(FilmeService filmeService, Usuario autor) {
        Topico topico = new Topico();
        topico.setId(id);
        topico.setFilme(filmeService.findById(idFilme));
        topico.setTexto(texto);
        topico.setAutor(autor);
        return topico;
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
