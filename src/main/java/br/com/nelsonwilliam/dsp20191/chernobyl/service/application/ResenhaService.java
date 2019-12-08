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

/**
 * Serviço responsável por manter a entidade Resenha.
 */
@Service
@Transactional
public class ResenhaService extends CrudService<Resenha, ResenhaDto, Long, ResenhaRepository> {

    /**
     * Representa a avaliação de uma resenha.
     */
    @Autowired
    private AvaliacaoResenhaService avaliacaoResenhaService;

    /**
     * Representa a autenticação de usuário, para restringir funcionalidades.
     */
    @Autowired
    private AuthService authService;

    /**
     * Responsável pela conversão entre a entidade "Resenha", seu respectivo DTO e vice-versa.
     */
    @Autowired
    private ResenhaAssembler resenhaAssembler;

    /**
     * Realiza nova avaliação de uma resenha ou atualiza uma já existente.
     *
     * @param idResenha Id da resenha a ser avaliada.
     * @param positivo Status da avaliação.
     */
    public void avaliar(long idResenha, boolean positivo) {
        Long idUsuario = authService.getIdUsuario();
        avaliacaoResenhaService.salvar(idUsuario, idResenha, positivo);
    }

    /**
     * Envia uma nova resenha ou atualiza uma existente. Apenas o usuário que a postou pode atualizá-la.
     *
     * @param resenhaDto Instância de ResenhaDto, contendo as informações a serem adicionadas/atualizadas.
     * @return Instância de ResenhaDto.
     */
    public ResenhaDto salvarSeForDono(ResenhaDto resenhaDto) {
        Usuario usuario = authService.getUsuario();
        if (resenhaDto.getIdAutor() == null)
            resenhaDto.setIdAutor(usuario.getId());

        if (!resenhaDto.getIdAutor().equals(usuario.getId()) && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        return convertEntityToDto(saveDto(resenhaDto));
    }

    /**
     * Remove uma resenha postada. Apenas o usuário que a postou pode removê-la.
     *
     * @param idResenha O id da resenha a ser removida.
     */
    public void deletarSeForDono(Long idResenha) {
        Usuario usuario = authService.getUsuario();
        Resenha resenha = findEntityById(idResenha);
        if (!resenha.getAutor().getId().equals(usuario.getId()) && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
        deleteById(idResenha);
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
