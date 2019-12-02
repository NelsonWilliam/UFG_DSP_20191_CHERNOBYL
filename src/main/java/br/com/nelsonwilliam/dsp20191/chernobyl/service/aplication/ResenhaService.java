package br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoResenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Resenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.PapelEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.web.dto.ResenhaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.ResenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResenhaService {

    @Autowired
    private ResenhaRepository resenhaRepository;

    @Autowired
    private ResenhaService resenhaService;

    @Autowired
    private AvaliacaoResenhaService avaliacaoResenhaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FilmeService filmeService;

    public List<Resenha> findAll() {
        return resenhaRepository.findAll();
    }

    public void deleteById(Long id) {
        resenhaRepository.deleteById(id);
    }

    public Long save(ResenhaDto resenhaDto, Principal principal) throws AccessDeniedException {

        Usuario usuario = usuarioService.autorizarUsuario(principal);

        Resenha resenha = resenhaDto.toResenha(filmeService, usuario);
        if (resenha.getAutor().getId() != usuario.getId() && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new AccessDeniedException("Usuário não autorizado");
        }

        return resenhaRepository.save(resenha).getId();
    }

    public Resenha findById(Long id) {
        Optional<Resenha> resenha = resenhaRepository.findById(id);
        return resenha.orElse(null);
    }

    public Collection<Resenha> findByFilme(Long idFilme) {
        return resenhaRepository.findByFilme_Id(idFilme);
    }

    public ResenhaDto autorizarEditar(Long idResenha, Principal principal) throws AccessDeniedException{
        Usuario usuario = usuarioService.autorizarUsuario(principal);
        Resenha resenha = findById(idResenha);
        if (resenha.getAutor().getId() != usuario.getId() && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new AccessDeniedException("Usuário não autorizado");
        }

        return ResenhaDto.fromResenha(resenha);
    }

    public List<ResenhaDto> getResenhasDtos(@PathVariable Long idFilme, Long idUsuario) {
        Collection<Resenha> resenhas = resenhaService.findByFilme(idFilme);
        Usuario usuario = usuarioService.findById(idUsuario);
        List<ResenhaDto> resenhasDto = new ArrayList<>();
        if (resenhas != null)
            for (Resenha res : resenhas) {
                AvaliacaoResenha avalResenha = usuario == null ? null : avaliacaoResenhaService.findByResenhaAndUsuario(res.getId(), usuario.getId());
                ResenhaDto dto = ResenhaDto.fromResenha(res, avalResenha, avaliacaoResenhaService);
                if (dto != null)
                    resenhasDto.add(dto);
            }
        return resenhasDto;
    }

}
