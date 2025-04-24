package br.com.ifpe.oxefood.util.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter; //o lombok cria gets e sets, cria Equals e o HashCode
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
@MappedSuperclass
public abstract class EntidadeNegocio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)//ele icrementa o id, porem incrementa como um td, não só por cada tabela
    private Long id;

    @JsonIgnore
    @Column
    private Boolean habilitado;
    
}
