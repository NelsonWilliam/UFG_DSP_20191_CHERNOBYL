package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.FilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.FilmeRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers.FilmeAssembler;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.infrastructure.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Serviço responsável por manter a entidade Filme.
 */
@Service
@Transactional
public class FilmeService extends CrudService<Filme, FilmeDto, Long, FilmeRepository> {

    /**
     * Representa a autenticação de usuário, para restringir funcionalidades.
     */
    @Autowired
    private AuthService authService;

    /**
     * Representa a avaliação de um filme.
     */
    @Autowired
    private AvaliacaoFilmeService avaliacaoFilmeService;

    /**
     * Responsável pela conversão entre a entidade "Filme", seu respectivo DTO e vice-versa.
     */
    @Autowired
    private FilmeAssembler filmeAssembler;

    /**
     * Realiza nova avaliação de um filme ou atualiza uma já existente.
     *
     * @param idFilme Id do filme a ser avaliado.
     * @param nota Nota a ser enviada, de 1 a 5.
     */
    public void avaliar(Long idFilme, int nota) {
        Long idUsuario = authService.getIdUsuario();
        if (idUsuario == null)
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        avaliacaoFilmeService.salvar(idUsuario, idFilme, nota);
    }

    /**
     * Salva um novo filme ou atualiza um já existente.
     *
     * @param filmeDto Instância de FilmeDto, contendo as informações a serem adicionadas/atualizadas.
     * @return Instância de FilmeDto.
     */
    public FilmeDto salvar(FilmeDto filmeDto) {
        return convertEntityToDto(saveDto(filmeDto));
    }

    /**
     * Salva um pôster de um filme.
     *
     * @param imgFile Arquivo MultipartFile contendo a imagem.
     * @param id Id do filme a ser adicionado/atualizado o pôster.
     * @throws IOException Exceção de Input/Output.
     */
    public void salvarImagem(MultipartFile imgFile, Long id) throws IOException {
        Filme filme = findEntityById(id);
        String imgString = ImageService.convertImageToString(imgFile);
        filme.setImage(imgString);
        convertEntityToDto(saveEntity(filme));
    }

    @Override
    protected FilmeDto convertEntityToDto(Filme filme) {
        return filme == null ? null : filmeAssembler.toDto(filme);
    }

    @Override
    protected Filme convertDtoToEntity(FilmeDto filmeDto) {
        return filmeDto == null ? null : filmeAssembler.toEntity(filmeDto);
    }

}
