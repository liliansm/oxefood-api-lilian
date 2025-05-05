package br.com.ifpe.oxefood.modelo.produto;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //o que torna ela um service é a notação @Service ou @Controler
public class ProdutoService {

   @Autowired //Instacia um objeto em tempo de execução, não precisa fazer o new na mão, é mais rapido
   private ProdutoRepository repository;

   @Transactional //ou ele roda td em relação ao banco ou não faz nada, se uma delas falhar as outras são desfeitas
   //Recebe um objeto e repassa para o repositorio 
   public Produto save(Produto produto) {

       produto.setHabilitado(Boolean.TRUE);
       return repository.save(produto);//cadastra um registo no banco e retorna um objeto que foi cadastrado
   }

}
