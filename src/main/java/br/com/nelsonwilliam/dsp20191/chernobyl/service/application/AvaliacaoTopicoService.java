package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoTopico;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoTopicoDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.AvaliacaoTopicoRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers.AvaliacaoTopicoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por manter a avaliação de um tópico de radioatividade.
 */
@Service
@Transactional
public class AvaliacaoTopicoService extends CrudService<AvaliacaoTopico, AvaliacaoTopicoDto, Long, AvaliacaoTopicoRepository> {

    @Autowired
    private AvaliacaoTopicoRepository avaliacaoTopicoRepository;

    /**
     * Representa o tópico de radioatividade a receber a avaliação.
     */
    @Autowired
    private TopicoService topicoService;

    /**
     * Representa o usuário a avaliar o tópico de radioatividade.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Responsável pela conversão entre a entidade "Avaliação Tópico", seu respectivo DTO e vice-versa.
     */
    @Autowired
    private AvaliacaoTopicoAssembler avaliacaoTopicoAssembler;

    /**
     * Procura um tópico de radioatividade enviado por determinado usuário.
     *
     * @param idTopico Id do tópico a ser buscado.
     * @param idUsuario Id do usuário que que enviou o tópico.
     * @return Instância de AvaliacaoTopico.
     */
    public AvaliacaoTopico findEntityByTopicoAndUsuario(Long idTopico, Long idUsuario) {
        return avaliacaoTopicoRepository.findByTopico_IdAndUsuario_Id(idTopico, idUsuario).orElse(null);
    }

    /**
     * Procura um tópico de radioatividade enviado por determinado usuário.
     *
     * @param idTopico Id do tópico a ser buscado.
     * @param idUsuario Id do usuário que que enviou o tópico.
     * @return Instância de AvaliacaoTopicoDto.
     */
    public AvaliacaoTopicoDto findByTopicoAndUsuario(Long idTopico, Long idUsuario) {
        return convertEntityToDto(findEntityByTopicoAndUsuario(idTopico, idUsuario));
    }

    /**
     * Salva uma nova avaliação de tópico ou atualiza uma já existente.
     *
     * @param idUsuario Id do usuário que realiza a avaliação.
     * @param idTopico Id do tópico a ser avaliado.
     * @param positivo Status de avaliação do tópico.
     * @return Instância de AvaliacaoTopicoDto.
     */
    public AvaliacaoTopicoDto salvar(Long idUsuario, Long idTopico, boolean positivo) {
        AvaliacaoTopico aval = new AvaliacaoTopico();
        aval.setUsuario(usuarioService.findEntityById(idUsuario));
        aval.setTopico(topicoService.findEntityById(idTopico));
        aval.setPositiva(positivo);

        AvaliacaoTopico avaliacaoExistente = findEntityByTopicoAndUsuario(idTopico, idUsuario);
        if (avaliacaoExistente != null) {
            aval.setId(avaliacaoExistente.getId());
        }
        return convertEntityToDto(avaliacaoTopicoRepository.save(aval));
    }

    @Override
    protected AvaliacaoTopicoDto convertEntityToDto(AvaliacaoTopico avaliacaoTopico) {
        return avaliacaoTopico == null ? null : avaliacaoTopicoAssembler.toDto(avaliacaoTopico);
    }

    @Override
    protected AvaliacaoTopico convertDtoToEntity(AvaliacaoTopicoDto avaliacaoTopicoDto) {
        return avaliacaoTopicoDto == null ? null : avaliacaoTopicoAssembler.toEntity(avaliacaoTopicoDto);
    }
}
