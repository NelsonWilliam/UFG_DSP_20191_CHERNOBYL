package br.com.nelsonwilliam.dsp20191.chernobyl.business.dto;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.CargoEnum;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PessoaDto {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 128)
    private String nome;

    @NotBlank
    private String cargo;

    @Lob
    private String image;

    public static PessoaDto fromPessoa(Pessoa pessoa) {
        PessoaDto pessoaDto = new PessoaDto();
        pessoaDto.setId(pessoa.getId());
        pessoaDto.setNome(pessoa.getNome());
        pessoaDto.setCargo(pessoa.getCargo().getNome());
        pessoaDto.setImage(pessoa.getImage());
        return pessoaDto;
    }

    public Pessoa toPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        pessoa.setCargo(CargoEnum.fromNome(cargo));
        pessoa.setNome(nome);
        pessoa.setImage(getImage());
        return pessoa;
    }

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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
