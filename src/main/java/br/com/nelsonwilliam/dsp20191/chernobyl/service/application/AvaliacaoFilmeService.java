package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoFilme;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoFilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.AvaliacaoFilmeRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers.AvaliacaoFilmeAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AvaliacaoFilmeService extends CrudService<AvaliacaoFilme, AvaliacaoFilmeDto, Long, AvaliacaoFilmeRepository> {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AvaliacaoFilmeAssembler avaliacaoFilmeAssembler;

    public AvaliacaoFilme findEntityByFilmeAndUsuario(Long idFilme, Long idUsuario) {
        return getRepository().findByFilme_IdAndUsuario_Id(idFilme, idUsuario).orElse(null);
    }

    public AvaliacaoFilmeDto findByFilmeAndUsuario(Long idFilme, Long idUsuario) {
        return convertEntityToDto(findEntityByFilmeAndUsuario(idFilme, idUsuario));
    }

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
