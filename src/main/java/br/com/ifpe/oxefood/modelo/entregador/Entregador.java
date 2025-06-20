package br.com.ifpe.oxefood.modelo.entregador;

import java.time.LocalDate;
import org.hibernate.annotations.SQLRestriction;
import br.com.ifpe.oxefood.util.entity.EntidadeAuditavel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
// cria uma tabela com esse nome
@Table(name = "Entregador")
@SQLRestriction("habilitado = true")
// Builder estanciar os objetos da classe sem fazer um new
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entregador extends EntidadeAuditavel  {
  
   @Column(nullable = false, length = 100)
   private String nome;

   @Column(unique = true)
   private String cpf;

   @Column(unique = true)
   private String rg;

   @Column
   private LocalDate dataNascimento;

   @Column
   private String foneCelular;

   @Column
   private String foneFixo;

   @Column
   private Integer qtdEntregasRealizadas;

   @Column
   private Double valorFrete;

   @Column
   private String enderecoRua;

   @Column
   private String enderecoComplemento;

   @Column
   private String enderecoNumero;

   @Column
   private String enderecoBairro;

   @Column
   private String enderecoCidade;
   
   @Column
   private String enderecoCep;

   @Column
   private String enderecoUf;

   @Column
   private Boolean ativo;

   // @Builder.Default  
   // private Boolean ativo = true;

}

