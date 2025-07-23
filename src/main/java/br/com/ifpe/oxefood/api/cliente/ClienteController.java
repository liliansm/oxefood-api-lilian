package br.com.ifpe.oxefood.api.cliente;

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

import br.com.ifpe.oxefood.modelo.acesso.UsuarioService;
import br.com.ifpe.oxefood.modelo.cliente.Cliente;
import br.com.ifpe.oxefood.modelo.cliente.ClienteService;
import br.com.ifpe.oxefood.modelo.cliente.EnderecoCliente;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController //Torna uma classe que pode especificar um endPoint
@RequestMapping("/api/cliente")// especifica a url que vou acionar 
@CrossOrigin//poderá receber requisições de um cliente especifico(FrontEnd react)
@Tag(
    name = "API Cliente",
    description = "API responsável pelos servidos de cliente no sistema"
)


public class ClienteController {

   @Autowired
   private ClienteService clienteService;

   @Autowired
    private UsuarioService usuarioService;


   @Operation(
       summary = "Serviço responsável por salvar um cliente no sistema.",
       description = "Exemplo de descrição de um endpoint responsável por inserir um cliente no sistema."
   )
   @PostMapping
   public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteRequest clienteRequest, HttpServletRequest request) {

    //    Cliente ClienteEntrada = request.build();
    //    Cliente cliente = clienteService.save(ClienteEntrada);

       Cliente cliente = clienteService.save(clienteRequest.build(), usuarioService.obterUsuarioLogado(request));

       return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
   }

   @Operation(
       summary = "Serviço responsável por listar todos os cliente do sistema.",
       description = "Exemplo de descrição de um endpoint responsável por litar todos os cliente no sistema."
   )
   @GetMapping
    public List<Cliente> listarTodos() {

        return clienteService.listarTodos();
    }

    @Operation(
       summary = "Serviço responsável por obter cliente por ID.",
       description = "Exemplo de descrição de um endpoint responsável por inserir um cliente no sistema."
   )
    @GetMapping("/{id}")
    public Cliente obterPorID(@PathVariable Long id) {

        return clienteService.obterPorID(id);
    }

    //Esse método recebe o ID do cliente que será alterado e um objeto do tipo ClienteRequest contendo os dados alterados do cliente. No corpo do método, um objeto Cliente é criado com método build() a partir da classe do Request e esse objeto criado é enviado para o service.

@PutMapping("/{id}")
@Operation(
    summary = "Serviço responsável por atualizar um cliente por ID.",
    description = "Atualiza os dados de um cliente existente com base no ID fornecido e nos dados enviados no corpo da requisição."
)
public ResponseEntity<Cliente> update(@PathVariable("id") Long id, @RequestBody @Valid ClienteRequest clienteRequest, HttpServletRequest request) {

   clienteService.update(id, clienteRequest.build(), usuarioService.obterUsuarioLogado(request));
   return ResponseEntity.ok().build();
}

@DeleteMapping("/{id}")
@Operation(
    summary = "Serviço responsável por deletar um cliente por ID.",
    description = "Remove um cliente do sistema com base no ID fornecido."
)
public ResponseEntity<Void> delete(@PathVariable Long id) {

   clienteService.delete(id);
   return ResponseEntity.ok().build();
}

@PostMapping("/endereco/{clienteId}")
@Operation(
    summary = "Serviço responsável por adicionar um endereço a um cliente.",
    description = "Adiciona um novo endereço para um cliente existente no sistema."
)
public ResponseEntity<EnderecoCliente> adicionarEnderecoCliente(@PathVariable("clienteId") Long clienteId, @RequestBody @Valid EnderecoClienteRequest request) {

   EnderecoCliente endereco = clienteService.adicionarEnderecoCliente(clienteId, request.build());
   return new ResponseEntity<EnderecoCliente>(endereco, HttpStatus.CREATED);
}

@PutMapping("/endereco/{enderecoId}")
@Operation(
    summary = "Serviço responsável por atualizar o endereço de um cliente.",
    description = "Atualiza os dados de um endereço existente com base no ID do endereço fornecido."
)
public ResponseEntity<EnderecoCliente> atualizarEnderecoCliente(@PathVariable("enderecoId") Long enderecoId, @RequestBody EnderecoClienteRequest request) {

   EnderecoCliente endereco = clienteService.atualizarEnderecoCliente(enderecoId, request.build());
   return new ResponseEntity<EnderecoCliente>(endereco, HttpStatus.OK);
}

@DeleteMapping("/endereco/{enderecoId}")
@Operation(
    summary = "Serviço responsável por remover o endereço de um cliente.",
    description = "Remove um endereço do sistema com base no ID do endereço fornecido."
)
public ResponseEntity<Void> removerEnderecoCliente(@PathVariable("enderecoId") Long enderecoId) {

   clienteService.removerEnderecoCliente(enderecoId);
   return ResponseEntity.noContent().build();
}

@GetMapping("/endereco/{clienteId}")
@Operation(
    summary = "Serviço responsável por listar todos os endereços de um cliente.",
    description = "Retorna a lista de todos os endereços associados a um cliente específico com base no seu ID."
)
public ResponseEntity<List<EnderecoCliente>> listarEnderecosCliente(@PathVariable("clienteId") Long clienteId) {

  List<EnderecoCliente> enderecos = clienteService.listarEnderecosDoCliente(clienteId);
  return new ResponseEntity<>(enderecos, HttpStatus.OK);
   }
}


