package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "profissional")
public class Profissional {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "diretor")
    private List<Filme> filmesDirigidos;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "atores")
    private List<Filme> filmesAtuados;

    // NOTA PRA QUEM FOR FAZER AS PRÓXIMAS ENTIDADES e não souber como funciona relacionamento no JPA:
    // ---
    // Veja nas entidades existentes que o Filme tem um atributo pro seu diretor e o Profissional tem um atributo pros
    // seus filmes dirigidos.
    // Em ambos os atributos do relacionamento é precisso que colocar as anotações
    // OneToMany/ManyToOne/ManyToMany/OneToOne corretamente.
    // Em APENAS UM deles tem que colocar o parâmetro "mappedBy" com o nome do OUTRO atributo do relacionamento.
    // -
    // Note também que lá em Filme tem uma lista de premiações. Como nesse caso premiações não é uma entidade,
    // é só um objeto qualquer (string), usa-se a notação "ElementCollection"

}
