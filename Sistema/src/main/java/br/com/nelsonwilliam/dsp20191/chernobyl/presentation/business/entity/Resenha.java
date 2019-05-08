package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "resenha")
public class Resenha {

    @Id
    @GeneratedValue
    private Long id;

}
