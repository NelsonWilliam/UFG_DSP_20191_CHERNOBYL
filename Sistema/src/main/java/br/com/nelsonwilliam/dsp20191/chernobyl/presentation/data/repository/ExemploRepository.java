package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.ExemploEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExemploRepository extends JpaRepository<ExemploEntity, Long> {

    // Esse método é implementado automaticamente pelo Spring Data JPA, com base no nome e nos
    // parâmetros. Veja mais em: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
    List<ExemploEntity> findByNome(String nome);

    void deleteById(Long id);

}
