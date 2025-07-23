package br.com.ifpe.oxefood.api.entregador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.entregador.Entregador;
import br.com.ifpe.oxefood.modelo.entregador.EntregadorService;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/entregador")
@CrossOrigin
@Tag(
    name = "API Entregador",
    description = "API responsável pelos serviços relacionados a entregadores no sistema."
)
public class EntregadorController {

   @Autowired
   private EntregadorService entregadorService;

   @PostMapping
   @Operation(
       summary = "Salvar novo entregador.",
       description = "Cria um novo entregador no sistema com os dados fornecidos."
   )
   public ResponseEntity<Entregador> save(@RequestBody @Valid EntregadorRequest request) {
       Entregador entregador = entregadorService.save(request.build());
       return new ResponseEntity<Entregador>(entregador, HttpStatus.CREATED);
   }

   @GetMapping
   @Operation(
       summary = "Listar todos os entregadores.",
       description = "Retorna uma lista com todos os entregadores cadastrados no sistema."
   )
   public List<Entregador> listarTodos() {
       return entregadorService.listarTodos();
   }

   @GetMapping("/{id}")
   @Operation(
       summary = "Obter entregador por ID.",
       description = "Retorna os dados de um entregador específico com base no ID informado."
   )
   public Entregador obterPorID(@PathVariable Long id) {
       return entregadorService.obterPorID(id);
   }

   @PutMapping("/{id}")
   @Operation(
       summary = "Atualizar entregador por ID.",
       description = "Atualiza os dados de um entregador existente com base no ID fornecido."
   )
   public ResponseEntity<Entregador> update(@PathVariable("id") Long id, @RequestBody @Valid EntregadorRequest request) {
       entregadorService.update(id, request.build());
       return ResponseEntity.ok().build();
   }

   @DeleteMapping("/{id}")
   @Operation(
       summary = "Remover entregador por ID.",
       description = "Remove um entregador do sistema com base no ID fornecido."
   )
   public ResponseEntity<Void> delete(@PathVariable Long id) {
       entregadorService.delete(id);
       return ResponseEntity.ok().build();
   }
}
