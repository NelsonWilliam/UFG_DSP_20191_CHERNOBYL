package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Resenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums.PapelEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.ResenhaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.ResenhaRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers.ResenhaAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Transactional
public class ResenhaService extends CrudService<Resenha, ResenhaDto, Long, ResenhaRepository> {

    @Autowired
    private AvaliacaoResenhaService avaliacaoResenhaService;

    @Autowired
    private AuthService authService;

    @Autowired
    private ResenhaAssembler resenhaAssembler;

    public void avaliar(long idResenha, boolean positivo) {
        Long idUsuario = authService.getIdUsuario();
        avaliacaoResenhaService.salvar(idUsuario, idResenha, positivo);
    }

    public ResenhaDto salvarSeForDono(ResenhaDto resenhaDto) {
        Usuario usuario = authService.getUsuario();
        if (resenhaDto.getIdAutor() == null)
            resenhaDto.setIdAutor(usuario.getId());

        if (!resenhaDto.getIdAutor().equals(usuario.getId()) && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        return convertEntityToDto(saveDto(resenhaDto));
    }

    public void deletarSeForDono(Long id) {
        Usuario usuario = authService.getUsuario();
        Resenha resenha = findEntityById(id);
        if (!resenha.getAutor().getId().equals(usuario.getId()) && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        deleteById(id);
    }

    @Override
    protected ResenhaDto convertEntityToDto(Resenha resenha) {
        return resenha == null ? null :  resenhaAssembler.toDto(resenha);
    }

    @Override
    protected Resenha convertDtoToEntity(ResenhaDto resenhaDto) {
        return resenhaDto == null ? null : resenhaAssembler.toEntity(resenhaDto);
    }

}
