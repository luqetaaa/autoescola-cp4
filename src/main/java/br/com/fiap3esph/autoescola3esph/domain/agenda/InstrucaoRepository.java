package br.com.fiap3esph.autoescola3esph.domain.agenda;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface InstrucaoRepository extends JpaRepository<Instrucao, Long> {
    boolean existsByInstrutorIdAndDataHoraAndStatus(Long idInstrutor, LocalDateTime dataHora, StatusInstrucao status);

    boolean existsByAlunoIdAndDataHoraBetweenAndStatus(Long idAluno, LocalDateTime inicioExpediente, LocalDateTime fimExpediente, StatusInstrucao status);
}
