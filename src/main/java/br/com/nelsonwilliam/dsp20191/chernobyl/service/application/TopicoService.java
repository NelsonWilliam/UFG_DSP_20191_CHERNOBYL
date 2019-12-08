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

/**
 * Serviço responsável por manter a entidade Tópico.
 */
@Service
@Transactional
public class TopicoService extends CrudService<Topico, TopicoDto, Long, TopicoRepository> {

    /**
     * Representa a avaliação de um tópico.
     */
    @Autowired
    private AvaliacaoTopicoService avaliacaoTopicoService;

    /**
     * Representa a autenticação de usuário, para restringir funcionalidades.
     */
    @Autowired
    private AuthService authService;

    /**
     * Responsável pela conversão entre a entidade "Tópico", seu respectivo DTO e vice-versa.
     */
    @Autowired
    private TopicoAssembler topicoAssembler;

    /**
     * Realiza nova avaliação de um tópico ou atualiza uma já existente.
     *
     * @param idTopico Id do tópico a ser avaliado.
     * @param positivo Status da avaliação.
     */
    public void avaliar(long idTopico, boolean positivo) {
        Long idUsuario = authService.getIdUsuario();
        avaliacaoTopicoService.salvar(idUsuario, idTopico, positivo);
    }

    /**
     * Envia um novo tópico de radioatividade ou atualiza um existente. Apenas o usuário que o postou pode atualizá-lo.
     *
     * @param topicoDto Instância de TopicoDto, contendo as informações a serem adicionadas/atualizadas.
     * @return Instância de TopicoDto.
     */
    public TopicoDto salvarSeForDono(TopicoDto topicoDto) {
        Usuario usuario = authService.getUsuario();
        if (topicoDto.getIdAutor() == null)
            topicoDto.setIdAutor(usuario.getId());

        if (!topicoDto.getIdAutor().equals(usuario.getId()) && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        return convertEntityToDto(saveDto(topicoDto));
    }

    /**
     * Remove um tópico postado. Apenas o usuário que a postou pode removê-lo.
     *
     * @param idTopico Id do tópico a ser removido.
     */
    public void deletarSeForDono(Long idTopico) {
        Usuario usuario = authService.getUsuario();
        Topico topico = findEntityById(idTopico);
        if (!topico.getAutor().getId().equals(usuario.getId()) && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        deleteById(idTopico);
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
