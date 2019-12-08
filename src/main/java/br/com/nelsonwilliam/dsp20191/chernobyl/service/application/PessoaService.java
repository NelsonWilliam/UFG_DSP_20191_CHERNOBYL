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

/**
 * Serviço responsável por manter a entidade Pessoa.
 */
@Service
@Transactional
public class PessoaService extends CrudService<Pessoa, PessoaDto, Long, PessoaRepository> {

    /**
     * Responsável pela conversão entre a entidade "Pessoa", seu respectivo DTO e vice-versa.
     */
    @Autowired
    private PessoaAssembler pessoaAssembler;

    /**
     * Limita a escolha de um cargo que não seja DIRETOR ou ATOR.
     *
     * @param pessoaDto Instância de PessoaDTO.
     * @throws InvalidFieldException Exceção de campo 'cargo' inválido.
     */
    public void validar(PessoaDto pessoaDto) throws InvalidFieldException {
        if (CargoEnum.fromNome(pessoaDto.getCargo()) == null) {
            throw new InvalidFieldException("cargo", "cargo inválido");
        }
    }

    /**
     * Salva nova pessoa ou atualiza uma já existente.
     *
     * @param pessoaDto Instância de PessoaDto.
     * @return Instância de PessoaDto.
     */
    public PessoaDto salvar(PessoaDto pessoaDto) {
        return convertEntityToDto(saveDto(pessoaDto));
    }

    /**
     * Salva a imagem de perfil de uma pessoa.
     *
     * @param idPessoa
     * @param imgFile Arquivo MultipartFile contendo a imagem.
     * @throws IOException
     */
    public void salvarImagem(Long idPessoa, MultipartFile imgFile) throws IOException {
        Pessoa pessoa = findEntityById(idPessoa);
        String imgString = ImageService.convertImageToString(imgFile);
        pessoa.setImage(imgString);
        saveEntity(pessoa);
    }

    /**
     * Obtém uma lista com o nome dos cargos de Pessoa.
     * Método invocado diretamente nos templates.
     *
     * @return Lista com os cargos.
     */
    @SuppressWarnings("unused")
    public List<String> getNomesCargos() {
        return Arrays.stream(CargoEnum.values()).map(CargoEnum::getNome).collect(Collectors.toList());
    }

    /**
     * Procura todos as Pessoas cadastradas, em que o cargo é DIRETOR.
     * Método invocado diretamente nos templates.
     *
     * @return Lista de diretores.
     */
    @SuppressWarnings("unused")
    public List<PessoaDto> findAllDiretores() {
        return convertEntitiesToDtos(getRepository().findByCargo(CargoEnum.DIRETOR));
    }

    /**
     * Procura todos as Pessoas cadastradas, em que o cargo é ATOR.
     * Método invocado diretamente nos templates.
     *
     * @return Lista de atores.
     */
    @SuppressWarnings("unused")
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
