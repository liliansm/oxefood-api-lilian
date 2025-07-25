package br.com.ifpe.oxefood.modelo.produto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.oxefood.util.exception.ProdutoException;
import jakarta.transaction.Transactional;

@Service //o que torna ela um service é a notação @Service ou @Controler
public class ProdutoService {

   @Autowired //Instacia um objeto em tempo de execução, não precisa fazer o new na mão, é mais rapido
   private ProdutoRepository repository;

   @Transactional //ou ele roda td em relação ao banco ou não faz nada, se uma delas falhar as outras são desfeitas
   //Recebe um objeto e repassa para o repositorio 
   public Produto save(Produto produto){

      if (produto.getValorUnitario() < 20 || produto.getValorUnitario() > 100) {
        throw new ProdutoException(ProdutoException.MSG_VALOR_MINIMO_PRODUTO);
    }

       produto.setHabilitado(Boolean.TRUE);
       return repository.save(produto);//cadastra um registo no banco e retorna um objeto que foi cadastrado
   }

   public List<Produto> listarTodos() {
  
    return repository.findAll();
   }

    public Produto obterPorID(Long id) {

      return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Produto produtoAlterado) {

      Produto produto = repository.findById(id).get();
      produto.setCategoria(produtoAlterado.getCategoria());
      produto.setCodigo(produtoAlterado.getCodigo());
      produto.setTitulo(produtoAlterado.getTitulo());
      produto.setDescricao(produtoAlterado.getDescricao());
      produto.setValorUnitario(produtoAlterado.getValorUnitario());
      produto.setTempoEntregaMinimo(produtoAlterado.getTempoEntregaMinimo());
      produto.setTempoEntregaMaximo(produtoAlterado.getTempoEntregaMaximo());
      
      repository.save(produto);
    }

    @Transactional
    public void delete(Long id) {

       Produto produto = repository.findById(id).get();
       produto.setHabilitado(Boolean.FALSE);

       repository.save(produto);
    }

    public List<Produto> filtrar(String codigo, String titulo, Long idCategoria) {

       List<Produto> listaProdutos = repository.findAll();

       if ((codigo != null && !"".equals(codigo)) &&
           (titulo == null || "".equals(titulo)) &&
           (idCategoria == null)) {
               listaProdutos = repository.consultarPorCodigo(codigo);
       } else if (
           (codigo == null || "".equals(codigo)) &&
           (titulo != null && !"".equals(titulo)) &&
           (idCategoria == null)) {    
               listaProdutos = repository.findByTituloContainingIgnoreCaseOrderByTituloAsc(titulo);
       } else if (
           (codigo == null || "".equals(codigo)) &&
           (titulo == null || "".equals(titulo)) &&
           (idCategoria != null)) {
               listaProdutos = repository.consultarPorCategoria(idCategoria); 
       } else if (
           (codigo == null || "".equals(codigo)) &&
           (titulo != null && !"".equals(titulo)) &&
           (idCategoria != null)) {
               listaProdutos = repository.consultarPorTituloECategoria(titulo, idCategoria); 
       }

       return listaProdutos;
}


}
