package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums.CargoEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.PessoaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.PessoaRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers.PessoaAssembler;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.infrastructure.ImageService;
import br.com.nelsonwilliam.dsp20191.chernobyl.web.exceptions.InvalidFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PessoaService extends CrudService<Pessoa, PessoaDto, Long, PessoaRepository> {

    @Autowired
    private PessoaAssembler pessoaAssembler;

    public void validar(PessoaDto pessoaDto) throws InvalidFieldException {
        if (CargoEnum.fromNome(pessoaDto.getCargo()) == null) {
            throw new InvalidFieldException("cargo", "cargo inválido");
        }
    }

    public PessoaDto salvar(PessoaDto pessoaDto) {
        return convertEntityToDto(saveDto(pessoaDto));
    }

    public void salvarImagem(Long idPessoa, MultipartFile imgFile) throws IOException {
        Pessoa pessoa = findEntityById(idPessoa);
        String imgString = ImageService.convertImageToString(imgFile);
        pessoa.setImage(imgString);
        saveEntity(pessoa);
    }

    @SuppressWarnings("unused") // Método invocado diretamente nos templates
    public List<String> getNomesCargos() {
        return Arrays.stream(CargoEnum.values()).map(CargoEnum::getNome).collect(Collectors.toList());
    }

    @SuppressWarnings("unused") // Método invocado diretamente nos templates
    public List<PessoaDto> findAllDiretores() {
        return convertEntitiesToDtos(getRepository().findByCargo(CargoEnum.DIRETOR));
    }

    @SuppressWarnings("unused") // Método invocado diretamente nos templates
    public List<PessoaDto> findAllAtores() {
        return convertEntitiesToDtos(getRepository().findByCargo(CargoEnum.ATOR));
    }

    @Override
    protected PessoaDto convertEntityToDto(Pessoa pessoa) {
        return pessoa == null ? null : pessoaAssembler.toDto(pessoa);
    }

    @Override
    protected Pessoa convertDtoToEntity(PessoaDto pessoaDto) {
        return pessoaDto == null ? null : pessoaAssembler.toEntity(pessoaDto);
    }
}
