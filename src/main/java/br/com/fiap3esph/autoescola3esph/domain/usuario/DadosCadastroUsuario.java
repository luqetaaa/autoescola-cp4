package br.com.fiap3esph.autoescola3esph.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DadosCadastroUsuario(
        @NotBlank String login,
        @NotBlank @Size(min = 6) String senha,
        @NotNull Role perfil) {
}
