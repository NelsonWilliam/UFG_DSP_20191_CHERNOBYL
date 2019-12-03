package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Topico;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums.PapelEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.TopicoDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.TopicoRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers.TopicoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Transactional
public class TopicoService extends CrudService<Topico, TopicoDto, Long, TopicoRepository> {

    @Autowired
    private AvaliacaoTopicoService avaliacaoTopicoService;

    @Autowired
    private AuthService authService;

    @Autowired
    private TopicoAssembler topicoAssembler;

    public void avaliar(long idTopico, boolean positivo) {
        Long idUsuario = authService.getIdUsuario();
        avaliacaoTopicoService.salvar(idUsuario, idTopico, positivo);
    }

    public TopicoDto salvarSeForDono(TopicoDto topicoDto) {
        Usuario usuario = authService.getUsuario();
        if (topicoDto.getIdAutor() == null)
            topicoDto.setIdAutor(usuario.getId());

        if (!topicoDto.getIdAutor().equals(usuario.getId()) && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        return convertEntityToDto(saveDto(topicoDto));
    }

    public void deletarSeForDono(Long id) {
        Usuario usuario = authService.getUsuario();
        Topico topico = findEntityById(id);
        if (!topico.getAutor().getId().equals(usuario.getId()) && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        deleteById(id);
    }

    @Override
    protected TopicoDto convertEntityToDto(Topico topico) {
        return topico == null ? null : topicoAssembler.toDto(topico);
    }

    @Override
    protected Topico convertDtoToEntity(TopicoDto topicoDto) {
        return topicoDto == null ? null : topicoAssembler.toEntity(topicoDto);
    }

}
