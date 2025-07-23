package br.com.ifpe.oxefood.api.produto;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.produto.Produto;
import br.com.ifpe.oxefood.modelo.produto.ProdutoService;
import br.com.ifpe.oxefood.modelo.produto.categoria.CategoriaProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/produto")
@CrossOrigin
@Tag(
    name = "API Produto",
    description = "API responsável pelos serviços relacionados aos produtos no sistema."
)
public class ProdutoController {

   @Autowired
   private ProdutoService produtoService;

   @Autowired
   private CategoriaProdutoService categoriaProdutoService;

   @PostMapping
   @Operation(
       summary = "Cadastrar novo produto.",
       description = "Cria um novo produto no sistema e associa a uma categoria existente."
   )
   public ResponseEntity<Produto> save(@RequestBody ProdutoRequest request) {
       Produto produtoNovo = request.build();
       produtoNovo.setCategoria(categoriaProdutoService.obterPorID(request.getIdCategoria()));
       Produto produto = produtoService.save(produtoNovo);

       return new ResponseEntity<Produto>(produto, HttpStatus.CREATED);
   }

   @GetMapping
   @Operation(
       summary = "Listar todos os produtos.",
       description = "Retorna uma lista com todos os produtos cadastrados no sistema."
   )
   public List<Produto> listarTodos() {
       return produtoService.listarTodos();
   }

   @GetMapping("/{id}")
   @Operation(
       summary = "Obter produto por ID.",
       description = "Retorna os dados de um produto específico com base no ID fornecido."
   )
   public Produto obterPorID(@PathVariable Long id) {
       return produtoService.obterPorID(id);
   }

   @PutMapping("/{id}")
   @Operation(
       summary = "Atualizar produto por ID.",
       description = "Atualiza os dados de um produto existente com base no ID fornecido."
   )
   public ResponseEntity<Produto> update(@PathVariable("id") Long id, @RequestBody ProdutoRequest request) {
       Produto produto = request.build();
       produto.setCategoria(categoriaProdutoService.obterPorID(request.getIdCategoria()));
       produtoService.update(id, produto);
       return ResponseEntity.ok().build();
   }

   @DeleteMapping("/{id}")
   @Operation(
       summary = "Remover produto por ID.",
       description = "Remove um produto do sistema com base no ID fornecido."
   )
   public ResponseEntity<Void> delete(@PathVariable Long id) {
       produtoService.delete(id);
       return ResponseEntity.ok().build();
   }

   @PostMapping("/filtrar")
   @Operation(
       summary = "Filtrar produtos.",
       description = "Filtra os produtos com base em parâmetros como código, título e ID da categoria."
   )
   public List<Produto> filtrar(
       @RequestParam(value = "codigo", required = false) String codigo,
       @RequestParam(value = "titulo", required = false) String titulo,
       @RequestParam(value = "idCategoria", required = false) Long idCategoria) {
       
       return produtoService.filtrar(codigo, titulo, idCategoria);
   }
}
