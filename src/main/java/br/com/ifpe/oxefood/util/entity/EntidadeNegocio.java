package br.com.ifpe.oxefood.util.entity;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter; //o lombok cria gets e sets, cria Equals e o HashCode
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = { "id" })
public abstract class EntidadeNegocio implements Serializable {

    private Long id;

    private Boolean habilitado;
    
}
