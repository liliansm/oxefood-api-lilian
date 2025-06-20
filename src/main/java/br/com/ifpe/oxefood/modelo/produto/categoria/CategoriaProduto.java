package br.com.ifpe.oxefood.modelo.produto.categoria;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.UniqueElements;

import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categoria_produto")
@SQLRestriction("habilitado = true")

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaProduto extends EntidadeAuditavel{
    @UniqueElements(message = "")
    @NotBlank(message = "A descrição da categoria é obrigatória.")
    @Size(max = 100, message = "A descrição da categoria deve ter no máximo {max} caracteres.")
    @Column(nullable = false, length = 100)
    private String descricao;
    
}
