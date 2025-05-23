package br.com.ifpe.oxefood.modelo.cliente;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service //o que torna ela um service é a notação @Service ou @Controler
public class ClienteService {

   @Autowired //Instacia um objeto em tempo de execução, não precisa fazer o new na mão, é mais rapido
   private ClienteRepository repository;

   @Transactional //ou ele roda td em relação ao banco ou não faz nada, se uma delas falhar as outras são desfeitas
   //Recebe um objeto e repassa para o repositorio 
   public Cliente save(Cliente cliente) {

       cliente.setHabilitado(Boolean.TRUE);
       return repository.save(cliente);//cadastra um registo no banco e retorna um objeto que foi cadastrado
   }

   public List<Cliente> listarTodos() {
  
    return repository.findAll();
   }

    public Cliente obterPorID(Long id) {

      return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Cliente clienteAlterado) {

      Cliente cliente = repository.findById(id).get();
      cliente.setNome(clienteAlterado.getNome());
      cliente.setDataNascimento(clienteAlterado.getDataNascimento());
      cliente.setCpf(clienteAlterado.getCpf());
      cliente.setFoneCelular(clienteAlterado.getFoneCelular());
      cliente.setFoneFixo(clienteAlterado.getFoneFixo());
	    
      repository.save(cliente);
    }

    @Transactional
    public void delete(Long id) {

       Cliente cliente = repository.findById(id).get();
       cliente.setHabilitado(Boolean.FALSE);

       repository.save(cliente);
    }

 


}
