package br.com.ifpe.oxefood.api.funcionario;

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

import br.com.ifpe.oxefood.modelo.acesso.Perfil;
import br.com.ifpe.oxefood.modelo.funcionario.Funcionario;
import br.com.ifpe.oxefood.modelo.funcionario.FuncionarioService;
import br.com.ifpe.oxefood.modelo.funcionario.TipoFuncionario;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/funcionario")
@CrossOrigin
@Tag(
    name = "API Funcionário",
    description = "API responsável pelos serviços relacionados aos funcionários no sistema."
)
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    @Operation(
        summary = "Cadastrar novo funcionário.",
        description = "Cria um novo funcionário no sistema. Define o perfil de acesso de acordo com o tipo (ADMINISTRADOR ou OPERADOR)."
    )
    public ResponseEntity<Funcionario> save(@RequestBody @Valid FuncionarioRequest request) {

        Funcionario funcionarioNovo = request.build();

        if (funcionarioNovo.getTipo().equals(TipoFuncionario.ADMINISTRADOR)) {
            funcionarioNovo.getUsuario().getRoles().add(new Perfil(Perfil.ROLE_FUNCIONARIO_ADMIN));
        } else if (funcionarioNovo.getTipo().equals(TipoFuncionario.OPERADOR)) {
            funcionarioNovo.getUsuario().getRoles().add(new Perfil(Perfil.ROLE_FUNCIONARIO_USER));
        }

        Funcionario funcionario = funcionarioService.save(funcionarioNovo);
        return new ResponseEntity<Funcionario>(funcionario, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(
        summary = "Listar todos os funcionários.",
        description = "Retorna uma lista com todos os funcionários cadastrados no sistema."
    )
    public List<Funcionario> listarTodos() {
        return funcionarioService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obter funcionário por ID.",
        description = "Retorna os dados de um funcionário específico com base no ID fornecido."
    )
    public Funcionario obterPorID(@PathVariable Long id) {
        return funcionarioService.obterPorID(id);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar funcionário por ID.",
        description = "Atualiza os dados de um funcionário existente com base no ID fornecido e nas informações enviadas."
    )
    public ResponseEntity<Funcionario> update(@PathVariable("id") Long id, @RequestBody FuncionarioRequest request) {
        funcionarioService.update(id, request.build());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Remover funcionário por ID.",
        description = "Remove um funcionário do sistema com base no ID fornecido."
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        funcionarioService.delete(id);
        return ResponseEntity.ok().build();
    }
}
