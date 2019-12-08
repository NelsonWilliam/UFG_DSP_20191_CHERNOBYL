package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoResenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoResenhaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.AvaliacaoResenhaRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers.AvaliacaoResenhaAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por manter a avaliação da resenha de um filme.
 */
@Service
@Transactional
public class AvaliacaoResenhaService extends CrudService<AvaliacaoResenha, AvaliacaoResenhaDto, Long, AvaliacaoResenhaRepository> {

    @Autowired
    private AvaliacaoResenhaRepository avaliacaoResenhaRepository;

    /**
     * Representa a resenha a receber avaliação.
     */
    @Autowired
    private ResenhaService resenhaService;

    /**
     * Representa o usuário que envia a avaliação.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Responsável pela conversão entre a entidade "Avaliação Resenha", seu respectivo DTO e vice-versa.
     */
    @Autowired
    private AvaliacaoResenhaAssembler avaliacaoResenhaAssembler;

    /**
     * Procura uma resenha enviada por determinado usuário.
     *
     * @param idResenha Id da resenha a ser buscada.
     * @param idUsuario Id do usuário que enviou a resenha.
     * @return Instância de AvaliacaoResenha.
     */
    public AvaliacaoResenha findEntityByResenhaAndUsuario(Long idResenha, Long idUsuario) {
        return avaliacaoResenhaRepository.findByResenha_IdAndUsuario_Id(idResenha, idUsuario).orElse(null);
    }

    /**
     * Procura uma resenha enviada por determinado usuário.
     *
     * @param idResenha Id da resenha a ser buscada.
     * @param idUsuario Id do usuário que enviou a resenha.
     * @return Instância de AvaliacaoResenhaDto.
     */
    public AvaliacaoResenhaDto findByResenhaAndUsuario(Long idResenha, Long idUsuario) {
        return convertEntityToDto(findEntityByResenhaAndUsuario(idResenha, idUsuario));
    }

    /**
     * Salva uma nova avaliação de resenha, ou atualiza uma já existente.
     *
     * @param idUsuario Id do usuário que realiza a avaliação.
     * @param idResenha Id da resenha a ser avaliada.
     * @param positivo Status de avaliação da resenha.
     * @return Instância de AvaliacaoResenhaDto.
     */
    public AvaliacaoResenhaDto salvar(Long idUsuario, Long idResenha, boolean positivo) {
        AvaliacaoResenha avaliacaoResenha = new AvaliacaoResenha();
        avaliacaoResenha.setUsuario(usuarioService.findEntityById(idUsuario));
        avaliacaoResenha.setResenha(resenhaService.findEntityById(idResenha));
        avaliacaoResenha.setPositiva(positivo);

        AvaliacaoResenha avaliacaoExistente = findEntityByResenhaAndUsuario(idResenha, idUsuario);
        if (avaliacaoExistente != null) {
            avaliacaoResenha.setId(avaliacaoExistente.getId());
        }
        return convertEntityToDto(avaliacaoResenhaRepository.save(avaliacaoResenha));
    }

    @Override
    protected AvaliacaoResenhaDto convertEntityToDto(AvaliacaoResenha avaliacaoResenha) {
        return avaliacaoResenha == null ? null : avaliacaoResenhaAssembler.toDto(avaliacaoResenha);
    }

    @Override
    protected AvaliacaoResenha convertDtoToEntity(AvaliacaoResenhaDto avaliacaoResenhaDto) {
        return avaliacaoResenhaDto == null ? null : avaliacaoResenhaAssembler.toEntity(avaliacaoResenhaDto);
    }
}
