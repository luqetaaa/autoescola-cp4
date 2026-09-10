package br.com.fiap3esph.autoescola3esph.domain.agenda;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record DetalhamentoCancelamento(
        Long id,
        StatusInstrucao status,
        @JsonProperty("motivo_cancelamento") MotivoCancelamento motivoCancelamento,
        @JsonProperty("data_cancelamento") @JsonFormat(pattern = "dd/MM/yyyy - HH:mm") LocalDateTime dataCancelamento) {
    public DetalhamentoCancelamento(Instrucao instrucao) {
        this(instrucao.getId(), instrucao.getStatus(), instrucao.getMotivoCancelamento(), instrucao.getDataCancelamento());
    }
}
