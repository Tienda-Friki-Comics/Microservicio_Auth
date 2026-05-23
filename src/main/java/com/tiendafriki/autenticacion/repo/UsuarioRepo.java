package com.tiendafriki.autenticacion.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tiendafriki.autenticacion.model.Usuario;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.List;

@Repository
public interface UsuarioRepo extends JpaRepository <Usuario, Integer> {

    Optional <Usuario> findByID(Integer iD);
    List <Usuario> findByNombreIgnoreCase(String nombre); 
    Optional <Usuario> findByRutUsuario(String rutUsuario); 
    Optional <Usuario> findByCorreo(String correo); 
    Optional <Usuario> findByRolIgnoreCase(String rol); 
    Boolean existsByCorreo(String correo);
    Boolean existsByRutUsuario(String rutUsuario); 

}