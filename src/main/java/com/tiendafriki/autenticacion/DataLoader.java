package com.tiendafriki.autenticacion;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tiendafriki.autenticacion.model.Usuario;
import com.tiendafriki.autenticacion.repo.UsuarioRepo;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(
            UsuarioRepo usuarioRepo,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            if (usuarioRepo.count() == 0) {

                Usuario u1 = new Usuario();

                u1.setRutUsuario("11.111.111-1");
                u1.setNombre("Tulio");
                u1.setApellido("Trivinio");
                u1.setCorreo("tulio.trivinio@gmail.com");

                u1.setContraseña(
                        passwordEncoder.encode("12345678")
                );

                u1.setRol("CLIENTE");
                u1.setActivo(true);

                usuarioRepo.save(u1);

                // =====================================================

                Usuario u2 = new Usuario();

                u2.setRutUsuario("22.222.222-2");
                u2.setNombre("Juan Carlos");
                u2.setApellido("Bodoque");
                u2.setCorreo("juan.bodoque@gmail.com");

                u2.setContraseña(
                        passwordEncoder.encode("12345678")
                );

                u2.setRol("CLIENTE");
                u2.setActivo(true);

                usuarioRepo.save(u2);

                // =====================================================

                Usuario u3 = new Usuario();

                u3.setRutUsuario("33.333.333-3");
                u3.setNombre("Patricia Ana");
                u3.setApellido("Tufillo");
                u3.setCorreo("patricia.tufillo@gmail.com");

                u3.setContraseña(
                        passwordEncoder.encode("12345678")
                );

                u3.setRol("CLIENTE");
                u3.setActivo(true);

                usuarioRepo.save(u3);

                // =====================================================

                Usuario u4 = new Usuario();

                u4.setRutUsuario("44.444.444-4");
                u4.setNombre("Policarpo");
                u4.setApellido("Alveldanio");
                u4.setCorreo("poli.alveldanio.tufillo@gmail.com");

                u4.setContraseña(
                        passwordEncoder.encode("12345678")
                );

                u4.setRol("CLIENTE");
                u3.setActivo(true);

                usuarioRepo.save(u4);


            }

        };

    }

}
