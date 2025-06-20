package br.com.ifpe.oxefood.modelo.cliente;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.util.exception.ClienteException;
import br.com.ifpe.oxefood.util.exception.ProdutoException;
import jakarta.transaction.Transactional;

@Service //o que torna ela um service é a notação @Service ou @Controler
public class ClienteService {

   @Autowired //Instacia um objeto em tempo de execução, não precisa fazer o new na mão, é mais rapido
   private ClienteRepository repository;

   @Autowired //Instacia um objeto em tempo de execução, não precisa fazer o new na mão, é mais rapido
   private EnderecoClienteRepository enderecoClienteRepository;

   @Transactional //ou ele roda td em relação ao banco ou não faz nada, se uma delas falhar as outras são desfeitas
   //Recebe um objeto e repassa para o repositorio 
   public Cliente save(Cliente cliente) {

      if (cliente.getFoneCelular() == null || !cliente.getFoneCelular().startsWith("81")) {
        throw new ClienteException(ClienteException.MSG_TELEFONE_SEM_PREFIXO);
    }

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

     @Transactional
     public EnderecoCliente adicionarEnderecoCliente(Long clienteId, EnderecoCliente endereco) {

       Cliente cliente = this.obterPorID(clienteId);
      
       //Primeiro salva o EnderecoCliente:

       endereco.setCliente(cliente);
       endereco.setHabilitado(Boolean.TRUE);
       enderecoClienteRepository.save(endereco);
      
       //Depois acrescenta o endereço criado ao cliente e atualiza o cliente:

       List<EnderecoCliente> listaEnderecoCliente = cliente.getEnderecos();
      
       if (listaEnderecoCliente == null) {
           listaEnderecoCliente = new ArrayList<EnderecoCliente>();
       }
      
       listaEnderecoCliente.add(endereco);
       cliente.setEnderecos(listaEnderecoCliente);
       repository.save(cliente);
      
       return endereco;
   }

   @Transactional
   public EnderecoCliente atualizarEnderecoCliente(Long id, EnderecoCliente enderecoAlterado) {

       EnderecoCliente endereco = enderecoClienteRepository.findById(id).get();
       endereco.setRua(enderecoAlterado.getRua());
       endereco.setNumero(enderecoAlterado.getNumero());
       endereco.setBairro(enderecoAlterado.getBairro());
       endereco.setCep(enderecoAlterado.getCep());
       endereco.setCidade(enderecoAlterado.getCidade());
       endereco.setEstado(enderecoAlterado.getEstado());
       endereco.setComplemento(enderecoAlterado.getComplemento());

       return enderecoClienteRepository.save(endereco);
   }

   @Transactional
   public void removerEnderecoCliente(Long idEndereco) {

       EnderecoCliente endereco = enderecoClienteRepository.findById(idEndereco).get();
       endereco.setHabilitado(Boolean.FALSE);
       enderecoClienteRepository.save(endereco);

       Cliente cliente = this.obterPorID(endereco.getCliente().getId());
       cliente.getEnderecos().remove(endereco);
       repository.save(cliente);
   }

    public List<EnderecoCliente> listarEnderecosDoCliente(Long clienteId) {
        Cliente cliente = repository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return cliente.getEnderecos();
    }

}
