package br.com.ifpe.oxefood.api.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ifpe.oxefood.modelo.cliente.Cliente;
import br.com.ifpe.oxefood.modelo.cliente.ClienteService;


@RestController //Torna uma classe que pode especificar um endPoint
@RequestMapping("/api/cliente")// especifica a url que vou acionar 
@CrossOrigin//poderarr receber requisições de um cliente especifico(FrontEnd react)
public class ClienteController {

   @Autowired
   private ClienteService clienteService;

   @PostMapping
   public ResponseEntity<Cliente> save(@RequestBody ClienteRequest request) {

    //    Cliente ClienteEntrada = request.build();
    //    Cliente cliente = clienteService.save(ClienteEntrada);

       Cliente cliente = clienteService.save(request.build());
       return new ResponseEntity<Cliente>(cliente, HttpStatus.CREATED);
   }
}
