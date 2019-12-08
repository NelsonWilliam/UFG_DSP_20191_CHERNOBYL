package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.services.AvaliacaoService;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums.CargoEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.PessoaDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Responsável pela conversão entre a Entidade "Pessoa" e um Data Transfer Object e vice-versa.
 */
@Component
public class PessoaAssembler {

    @Autowired
    private AvaliacaoService avaliacaoService;

    /**
     * Converte um Data Transfer Object "Pessoa" em uma Entidade "Pessoa".
     *
     * @param pessoaDto Instância de PessoaDto.
     * @return Instância de Pessoa.
     */
    public Pessoa toEntity(PessoaDto pessoaDto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(pessoaDto.getId());
        pessoa.setCargo(CargoEnum.fromNome(pessoaDto.getCargo()));
        pessoa.setNome(pessoaDto.getNome());
        pessoa.setImage(pessoaDto.getImage());
        return pessoa;
    }

    /**
     * Converte uma Entidade "Pessoa" em um Data Transfer Object "Pessoa".
     *
     * @param pessoa Instância de Pessoa.
     * @return Instância de PessoaDto.
     */
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

}
