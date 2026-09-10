package br.com.fiap3esph.autoescola3esph.service;

import br.com.fiap3esph.autoescola3esph.domain.aluno.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository repository;

    @Transactional
    public DadosDetalhamentoAluno cadastrar(DadosCadastroAluno dados) {
        return new DadosDetalhamentoAluno(repository.save(new Aluno(dados)));
    }

    @Transactional(readOnly = true)
    public Page<DadosListagemAluno> listar(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(DadosListagemAluno::new);
    }

    @Transactional(readOnly = true)
    public DadosDetalhamentoAluno detalhar(Long id) {
        return new DadosDetalhamentoAluno(buscar(id));
    }

    @Transactional
    public DadosDetalhamentoAluno atualizar(DadosAtualizacaoAluno dados) {
        Aluno aluno = buscar(dados.id());
        aluno.atualizarInformacoes(dados);
        return new DadosDetalhamentoAluno(aluno);
    }

    @Transactional
    public void excluir(Long id) {
        buscar(id).excluir();
    }

    private Aluno buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new AlunoNotFoundException("ID do aluno informado não existe!"));
    }
}
