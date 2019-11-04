package br.com.nelsonwilliam.dsp20191.chernobyl.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.dto.PessoaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.CargoEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.PessoaRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.utils.Utilitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;
    private Collection<Filme> filmes;

    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    public Pessoa findById(Long id) {
        if (id == null)
            return null;
        Optional<Pessoa> exemplo = pessoaRepository.findById(id);
        return exemplo.orElse(null);
    }

    public PessoaDto findByIdDto(Long id) {
        Pessoa pessoa = findById(id);
        if (pessoa == null)
            return null;
        return PessoaDto.fromPessoa(pessoa);
    }

    public CargoEnum findCargoById(Long id) {
        Pessoa pessoa = findById(id);
        return pessoa.getCargo();
    }

    public Collection<Pessoa> findByCargo(CargoEnum cargo) {
        if (cargo == null)
            return Collections.emptyList();
        return pessoaRepository.findByCargo(cargo);
    }

    public void deleteById(Long id) {
        if (id == null)
            return;
        pessoaRepository.deleteById(id);
    }

    public Pessoa save(Pessoa pessoa) {
        if (pessoa == null)
            return null;
        return pessoaRepository.save(pessoa);
    }

    public List<String> getNomesCargos() {
        CargoEnum[] cargos = CargoEnum.values();
        return Arrays.stream(cargos).map(CargoEnum::getNome).collect(Collectors.toList());
    }

    public List<PessoaDto> findAllDto() {
        return findAll().stream().map(PessoaDto::fromPessoa).collect(Collectors.toList());
    }

    public List<PessoaDto> findAllDiretoresDto() {
        return findByCargo(CargoEnum.DIRETOR).stream().map(PessoaDto::fromPessoa).collect(Collectors.toList());
    }

    public List<PessoaDto> findAllAtoresDto() {
        return findByCargo(CargoEnum.ATOR).stream().map(PessoaDto::fromPessoa).collect(Collectors.toList());
    }

    public String calcularRadiacao(List<Double> avaliacoes) {
        double soma = 0, media = 0;
        String resultado = "";

        if (avaliacoes.size() > 0) {
            for (Double aval : avaliacoes) {
                soma += aval;
            }
            media = soma / avaliacoes.size();
            resultado = Double.toString(media);
        } else {
            resultado = "Sem avaliação";
        }

        return resultado;

    }

    public Pessoa alterarImagem(Long id, File img) throws Exception {
        Pessoa pessoa = findById(id);
        String imagem = Utilitario.imageToString(img);
        pessoa.setImage(imagem);
        return save(pessoa);
    }
}
