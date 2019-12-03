package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoTopico;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoTopicoDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.AvaliacaoTopicoRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers.AvaliacaoTopicoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AvaliacaoTopicoService extends CrudService<AvaliacaoTopico, AvaliacaoTopicoDto, Long, AvaliacaoTopicoRepository> {

    @Autowired
    private AvaliacaoTopicoRepository avaliacaoTopicoRepository;

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AvaliacaoTopicoAssembler avaliacaoTopicoAssembler;

    public AvaliacaoTopico findEntityByTopicoAndUsuario(Long idTopico, Long idUsuario) {
        return avaliacaoTopicoRepository.findByTopico_IdAndUsuario_Id(idTopico, idUsuario).orElse(null);
    }

    public AvaliacaoTopicoDto findByTopicoAndUsuario(Long idTopico, Long idUsuario) {
        return convertEntityToDto(findEntityByTopicoAndUsuario(idTopico, idUsuario));
    }

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
