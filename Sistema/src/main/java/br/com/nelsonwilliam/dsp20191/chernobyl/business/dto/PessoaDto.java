package br.com.nelsonwilliam.dsp20191.chernobyl.business.dto;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.CargoEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class PessoaDto {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 128)
    private String nome;

    @NotBlank
    private String cargo;

    public static PessoaDto fromPessoa(Pessoa pessoa) {
        PessoaDto pessoaDto = new PessoaDto();
        pessoaDto.setId(pessoa.getId());
        pessoaDto.setNome(pessoa.getNome());
        pessoaDto.setCargo(pessoa.getCargo().getNome());
        return pessoaDto;
    }

    public Pessoa toPessoa() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        pessoa.setCargo(CargoEnum.fromNome(cargo));
        pessoa.setNome(nome);
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

}
