package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoResenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoResenhaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.AvaliacaoResenhaRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers.AvaliacaoResenhaAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AvaliacaoResenhaService extends CrudService<AvaliacaoResenha, AvaliacaoResenhaDto, Long, AvaliacaoResenhaRepository> {

    @Autowired
    private AvaliacaoResenhaRepository avaliacaoResenhaRepository;

    @Autowired
    private ResenhaService resenhaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AvaliacaoResenhaAssembler avaliacaoResenhaAssembler;

    public AvaliacaoResenha findEntityByResenhaAndUsuario(Long idResenha, Long idUsuario) {
        return avaliacaoResenhaRepository.findByResenha_IdAndUsuario_Id(idResenha, idUsuario).orElse(null);
    }

    public AvaliacaoResenhaDto findByResenhaAndUsuario(Long idResenha, Long idUsuario) {
        return convertEntityToDto(findEntityByResenhaAndUsuario(idResenha, idUsuario));
    }

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
