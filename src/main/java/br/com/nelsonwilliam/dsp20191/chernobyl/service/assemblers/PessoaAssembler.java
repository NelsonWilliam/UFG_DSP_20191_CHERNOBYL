package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.services.AvaliacaoService;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums.CargoEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.PessoaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PessoaAssembler {

    @Autowired
    private AvaliacaoService avaliacaoService;

    public PessoaDto toDto(Pessoa pessoa) {
        PessoaDto pessoaDto = new PessoaDto();
        pessoaDto.setId(pessoa.getId());
        pessoaDto.setNome(pessoa.getNome());
        pessoaDto.setCargo(pessoa.getCargo().getNome());
        pessoaDto.setImage(pessoa.getImage());
        pessoaDto.setMediaAvaliacao(avaliacaoService.calcularMediaAvaliacao(pessoa));
        pessoaDto.setIdsFilmes(
                pessoa.getCargo() == CargoEnum.DIRETOR
                        ? pessoa.getFilmesDirigidos().stream().map(Filme::getId).collect(Collectors.toList())
                        : pessoa.getFilmesAtuados().stream().map(Filme::getId).collect(Collectors.toList()));
        return pessoaDto;
    }

    public Pessoa toEntity(PessoaDto dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(dto.getId());
        pessoa.setCargo(CargoEnum.fromNome(dto.getCargo()));
        pessoa.setNome(dto.getNome());
        pessoa.setImage(dto.getImage());
        return pessoa;
    }

}
