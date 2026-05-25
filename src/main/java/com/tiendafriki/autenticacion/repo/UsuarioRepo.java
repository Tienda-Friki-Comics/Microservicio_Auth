package com.tiendafriki.autenticacion.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tiendafriki.autenticacion.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {

    // =====================================================
    // CORRECCIÓN:
    // ID -> Id
    // porque ahora el atributo es "id"
    // =====================================================

    Optional<Usuario> findById(Integer id);

    List<Usuario> findByNombreIgnoreCase(String nombre);

    Optional<Usuario> findByRutUsuario(String rutUsuario);

    Optional<Usuario> findByCorreo(String correo);

    List<Usuario> findByRolIgnoreCase(String rol);

    Boolean existsByCorreo(String correo);

    Boolean existsByRutUsuario(String rutUsuario);

}