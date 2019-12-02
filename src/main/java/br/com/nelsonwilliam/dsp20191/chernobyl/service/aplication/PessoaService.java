package br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication;

import br.com.nelsonwilliam.dsp20191.chernobyl.web.dto.PessoaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.CargoEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.PessoaRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.infrastructure.ImageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.DecimalFormat;
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

    public Long save(PessoaDto pessoaDto) {
        Pessoa pessoa = pessoaDto.toPessoa();
        if (pessoa == null)
            return null;
        return pessoaRepository.save(pessoa).getId();
    }

    private Long savePrivate(Pessoa pessoa){
        return pessoaRepository.save(pessoa).getId();
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
            DecimalFormat df = new DecimalFormat("#.00");
            resultado = Double.toString(Double.parseDouble(df.format(media)));
        } else {
            resultado = "Sem avaliação";
        }

        return resultado;

    }

    public Long alterarImagem(Long id, File img) throws Exception {
        Pessoa pessoa = findById(id);
        String imagem = ImageHandler.imageToString(img);
        pessoa.setImage(imagem);
        return savePrivate(pessoa);
    }
}
