package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoFilme;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoFilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.AvaliacaoFilmeRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers.AvaliacaoFilmeAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por manter a avaliação de um filme.
 */
@Service
@Transactional
public class AvaliacaoFilmeService extends CrudService<AvaliacaoFilme, AvaliacaoFilmeDto, Long, AvaliacaoFilmeRepository> {

    /**
     * Representa o filme a receber avaliação.
     */
    @Autowired
    private FilmeService filmeService;

    /**
     * Representa o usuário que envia a avaliação.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Responsável pela conversão entre a entidade "Avaliação Filme", seu respectivo DTO e vice-versa.
     */
    @Autowired
    private AvaliacaoFilmeAssembler avaliacaoFilmeAssembler;

    /**
     * Procura uma avaliação de determinado filme enviada por determinado usuário.
     *
     * @param idFilme Id do filme avaliado.
     * @param idUsuario Id do usuário que avaliou.
     * @return Instância de AvaliacaoFilme.
     */
    public AvaliacaoFilme findEntityByFilmeAndUsuario(Long idFilme, Long idUsuario) {
        return getRepository().findByFilme_IdAndUsuario_Id(idFilme, idUsuario).orElse(null);
    }

    /**
     * Procura uma avaliação de determinado filme enviada por determinado usuário.
     *
     * @param idFilme Id do filme avaliado.
     * @param idUsuario Id do usuário que avaliou.
     * @return Instância de AvaliacaoFilmeDto.
     */
    public AvaliacaoFilmeDto findByFilmeAndUsuario(Long idFilme, Long idUsuario) {
        return convertEntityToDto(findEntityByFilmeAndUsuario(idFilme, idUsuario));
    }

    /**
     * Salva uma nova avaliação de um filme ou atualiza uma já existente.
     *
     * @param idUsuario Id do usuário que realiza a avaliação.
     * @param idFilme Id do filme avaliado.
     * @param nota Nota da avaliação, de 1 a 5.
     * @return Instância de AvaliacaoFilmeDto.
     */
    public AvaliacaoFilmeDto salvar(Long idUsuario, Long idFilme, int nota) {
        AvaliacaoFilme aval = new AvaliacaoFilme();
        aval.setUsuario(usuarioService.findEntityById(idUsuario));
        aval.setFilme(filmeService.findEntityById(idFilme));
        aval.setNota(nota);

        AvaliacaoFilme avaliacaoExistente = findEntityByFilmeAndUsuario(idFilme, idUsuario);
        if (avaliacaoExistente != null) {
            aval.setId(avaliacaoExistente.getId());
        }
        return convertEntityToDto(getRepository().save(aval));
    }

    @Override
    protected AvaliacaoFilmeDto convertEntityToDto(AvaliacaoFilme avaliacaoFilme) {
        return avaliacaoFilme == null ? null : avaliacaoFilmeAssembler.toDto(avaliacaoFilme);
    }

    @Override
    protected AvaliacaoFilme convertDtoToEntity(AvaliacaoFilmeDto avaliacaoFilmeDto) {
        return avaliacaoFilmeDto == null ? null :  avaliacaoFilmeAssembler.toEntity(avaliacaoFilmeDto);
    }
}
