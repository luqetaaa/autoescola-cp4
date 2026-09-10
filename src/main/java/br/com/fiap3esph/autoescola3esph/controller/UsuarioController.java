package br.com.fiap3esph.autoescola3esph.controller;

import br.com.fiap3esph.autoescola3esph.domain.usuario.*;
import br.com.fiap3esph.autoescola3esph.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder) {
        var dto = service.cadastrar(dados);
        return ResponseEntity.created(uriBuilder.path("/usuarios/{id}").buildAndExpand(dto.id()).toUri()).body(dto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<DadosDetalhamentoUsuario>> listar(@PageableDefault(size = 10, sort = "login") Pageable pageable) {
        return ResponseEntity.ok(service.listar(pageable));
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DadosDetalhamentoUsuario> atualizarPerfil(@RequestBody @Valid DadosAtualizacaoUsuario dados) {
        return ResponseEntity.ok(service.atualizarPerfil(dados));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/minha-senha")
    public ResponseEntity<Void> alterarPropriaSenha(@AuthenticationPrincipal Usuario usuario,
                                                    @RequestBody @Valid DadosAlteracaoSenha dados) {
        service.alterarPropriaSenha(usuario, dados);
        return ResponseEntity.noContent().build();
    }
}
