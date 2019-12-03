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

@Service
@Transactional
public class FilmeService extends CrudService<Filme, FilmeDto, Long, FilmeRepository> {

    @Autowired
    private AuthService authService;

    @Autowired
    private AvaliacaoFilmeService avaliacaoFilmeService;

    @Autowired
    private FilmeAssembler filmeAssembler;

    public void avaliar(Long idFilme, int nota) {
        Long idUsuario = authService.getIdUsuario();
        if (idUsuario == null)
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        avaliacaoFilmeService.salvar(idUsuario, idFilme, nota);
    }

    public FilmeDto salvar(FilmeDto filmeDto) {
        return convertEntityToDto(saveDto(filmeDto));
    }

    public void salvarImagem(MultipartFile file, Long id) throws IOException {
        Filme filme = findEntityById(id);
        String imgString = ImageService.convertImageToString(file);
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
