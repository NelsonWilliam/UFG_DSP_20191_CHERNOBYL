package br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoTopico;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Topico;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.PapelEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.dto.TopicoDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoService topicoService;

    @Autowired AvaliacaoTopicoService avaliacaoTopicoService;

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private UsuarioService usuarioService;

    public List<Topico> findAll() {
        return topicoRepository.findAll();
    }

    public void deleteById(Long id) {
        topicoRepository.deleteById(id);
    }

    public Long save(TopicoDto topicoDto, Principal principal) throws AccessDeniedException {

        Usuario usuario = usuarioService.autorizarUsuario(principal);

        Topico topico = topicoDto.toTopico(filmeService, usuario);
        if (topico.getAutor().getId() != usuario.getId() && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
        return topicoRepository.save(topico).getId();
    }

    public Topico findById(Long id) {
        Optional<Topico> resenha = topicoRepository.findById(id);
        return resenha.orElse(null);
    }

    public Collection<Topico> findByFilme(Long idFilme) {
        return topicoRepository.findByFilme_Id(idFilme);
    }

    public TopicoDto autorizarEditar(Long idTopico, Principal principal){

        Usuario usuario = usuarioService.autorizarUsuario(principal);
        Topico topico = findById(idTopico);
        if (topico.getAutor().getId() != usuario.getId() && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new AccessDeniedException("Usuário não autorizado");
        }

        return TopicoDto.fromTopico(topico);
    }

    public List<TopicoDto> getTopicosDtos(Long idFilme, Long idUsuario) {
        Collection<Topico> topicos = topicoService.findByFilme(idFilme);
        Usuario usuario = usuarioService.findById(idUsuario);
        List<TopicoDto> topicosDtos = new ArrayList<>();
        if (topicos != null)
            for (Topico top : topicos) {
                AvaliacaoTopico avalTop = usuario == null ? null : avaliacaoTopicoService.findByTopicoAndUsuario(top.getId(), usuario.getId());
                TopicoDto dto = TopicoDto.fromTopico(top, avalTop, avaliacaoTopicoService);
                if (dto != null)
                    topicosDtos.add(dto);
            }
        return topicosDtos;
    }

}
