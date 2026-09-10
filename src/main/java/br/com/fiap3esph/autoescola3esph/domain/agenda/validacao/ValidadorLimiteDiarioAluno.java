package br.com.fiap3esph.autoescola3esph.domain.agenda.validacao;

import br.com.fiap3esph.autoescola3esph.domain.agenda.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.domain.agenda.InstrucaoRepository;
import br.com.fiap3esph.autoescola3esph.domain.agenda.StatusInstrucao;
import br.com.fiap3esph.autoescola3esph.domain.agenda.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ValidadorLimiteDiarioAluno implements ValidadorAgendamento {
    private final InstrucaoRepository repository;

    @Override
    public void validar(DadosAgendamento dados) {
        LocalDateTime inicioExpediente = dados.dataHora().withHour(6);
        LocalDateTime fimExpediente = dados.dataHora().withHour(21 - 1);

        boolean reincidencia = repository.existsByAlunoIdAndDataHoraBetweenAndStatus(
                dados.idAluno(),
                inicioExpediente,
                fimExpediente,
                StatusInstrucao.AGENDADA
        );
        if (reincidencia) {
            throw new ValidacaoException("Aluno já possui uma instrução agendada neste dia!");
        }
    }
}
