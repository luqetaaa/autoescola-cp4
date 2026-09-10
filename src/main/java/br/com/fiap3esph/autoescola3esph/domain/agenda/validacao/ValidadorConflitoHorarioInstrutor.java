package br.com.fiap3esph.autoescola3esph.domain.agenda.validacao;

import br.com.fiap3esph.autoescola3esph.domain.agenda.DadosAgendamento;
import br.com.fiap3esph.autoescola3esph.domain.agenda.InstrucaoRepository;
import br.com.fiap3esph.autoescola3esph.domain.agenda.ValidacaoException;
import br.com.fiap3esph.autoescola3esph.domain.agenda.StatusInstrucao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidadorConflitoHorarioInstrutor implements ValidadorAgendamento {
    private final InstrucaoRepository repository;

    @Override
    public void validar(DadosAgendamento dados) {
        boolean instrutorOcupado = repository.existsByInstrutorIdAndDataHoraAndStatus(
                dados.idInstrutor(),
                dados.dataHora(),
                StatusInstrucao.AGENDADA
        );

        if (instrutorOcupado) {
            throw new ValidacaoException("Instrutor indisponível na data/hora escolhida!");
        }
    }
}
