package br.com.fiap3esph.autoescola3esph.infra.config;

import br.com.fiap3esph.autoescola3esph.domain.usuario.Role;
import br.com.fiap3esph.autoescola3esph.domain.usuario.Usuario;
import br.com.fiap3esph.autoescola3esph.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInicialConfig {
    @Bean
    CommandLineRunner criarAdminInicial(UsuarioRepository repository, PasswordEncoder encoder,
                                        @Value("${app.admin.login:admin}") String login,
                                        @Value("${app.admin.password:admin123}") String senha) {
        return args -> {
            if (!repository.existsByLogin(login)) {
                repository.save(new Usuario(login, encoder.encode(senha), Role.ADMIN));
            }
        };
    }
}
