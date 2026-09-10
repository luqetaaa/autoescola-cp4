package br.com.fiap3esph.autoescola3esph.service;

import br.com.fiap3esph.autoescola3esph.domain.agenda.ValidacaoException;
import br.com.fiap3esph.autoescola3esph.domain.usuario.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public DadosDetalhamentoUsuario cadastrar(DadosCadastroUsuario dados) {
        if (repository.existsByLogin(dados.login())) {
            throw new ValidacaoException("Login já cadastrado!");
        }
        Usuario usuario = new Usuario(dados.login(), passwordEncoder.encode(dados.senha()), dados.perfil());
        return new DadosDetalhamentoUsuario(repository.save(usuario));
    }

    @Transactional(readOnly = true)
    public Page<DadosDetalhamentoUsuario> listar(Pageable pageable) {
        return repository.findAll(pageable).map(DadosDetalhamentoUsuario::new);
    }

    @Transactional
    public DadosDetalhamentoUsuario atualizarPerfil(DadosAtualizacaoUsuario dados) {
        Usuario usuario = buscar(dados.id());
        usuario.atualizarPerfil(dados.perfil());
        return new DadosDetalhamentoUsuario(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscar(id));
    }

    @Transactional
    public void alterarPropriaSenha(Usuario usuarioAutenticado, DadosAlteracaoSenha dados) {
        Usuario usuario = buscar(usuarioAutenticado.getId());
        if (!passwordEncoder.matches(dados.senhaAtual(), usuario.getSenha())) {
            throw new ValidacaoException("Senha atual incorreta!");
        }
        usuario.alterarSenha(passwordEncoder.encode(dados.novaSenha()));
    }

    private Usuario buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new UsuarioNotFoundException("ID do usuário informado não existe!"));
    }
}
