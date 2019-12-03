package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public abstract class CrudService<TEntity, TDto, TId, TRepositoy extends JpaRepository<TEntity, TId>> {

    @Autowired
    private TRepositoy repositoy;

    public TEntity findEntityById(TId id) {
        Optional<TEntity> value = repositoy.findById(id);
        return value.orElse(null);
    }

    public List<TEntity> findAllEntities() {
        return repositoy.findAll();
    }

    public TDto findById(TId id) {
        TEntity entity = findEntityById(id);
        if (entity == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID não encontrado");
        return convertEntityToDto(entity);
    }

    public List<TDto> findByIds(Collection<TId> ids) {
        return ids.stream()
                .map(this::findEntityById)
                .filter(Objects::nonNull)
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public List<TDto> findAll() {
        return convertEntitiesToDtos(findAllEntities());
    }

    public void deleteById(TId id) {
        if (!existsById(id))
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "ID não encontrado");

        repositoy.deleteById(id);
    }

    public boolean existsById(TId id) {
        return repositoy.existsById(id);
    }

    public long count() {
        return repositoy.count();
    }

    protected TEntity saveDto(TDto dto) {
        TEntity entity = convertDtoToEntity(dto);
        return repositoy.save(entity);
    }

    protected TEntity saveEntity(TEntity entity) {
        return repositoy.save(entity);
    }

    protected TRepositoy getRepository() {
        return repositoy;
    }

    protected List<TDto> convertEntitiesToDtos(Collection<TEntity> entities) {
        return entities.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    protected abstract TDto convertEntityToDto(TEntity entity);

    protected abstract TEntity convertDtoToEntity(TDto dto);
}
