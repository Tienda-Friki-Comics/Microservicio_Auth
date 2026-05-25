package com.tiendafriki.autenticacion.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import com.tiendafriki.autenticacion.repo.UsuarioRepo;
import com.tiendafriki.autenticacion.model.Usuario;
import org.springframework.stereotype.Service;
import com.tiendafriki.autenticacion.dto.*;

import java.util.*;

@Service
public class UsuarioService {

    // =====================================================
    // INYECCIÓN DE DEPENDENCIAS
    // =====================================================

    @Autowired
    private UsuarioRepo ur;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // =====================================================
    // LISTAR
    // =====================================================

    public ListarDTO<Usuario> listar() {

        List<Usuario> lu = ur.findAll();

        return new ListarDTO<>(
                lu.size(),
                lu
        );
    }

    // =====================================================
    // BUSCAR POR ID
    // =====================================================

    public Usuario buscarxID(Integer id) {

        return ur.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "[+] Usuario No Encontrado [>_<] ... "
                        )
                );
    }

    // =====================================================
    // BUSCAR POR CORREO
    // =====================================================

    public Usuario buscarxCorreo(String correo) {

        return ur.findByCorreo(correo)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "[+] Usuario No Encontrado [>_<] ... "
                        )
                );
    }

    // =====================================================
    // BUSCAR POR RUT
    // =====================================================

    public Usuario buscarxRutUsuario(String rutUsuario) {

        return ur.findByRutUsuario(rutUsuario)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "[+] Usuario No Encontrado [>_<] ... "
                        )
                );
    }

    // =====================================================
    // BUSCAR POR NOMBRE
    // =====================================================

    public ListarDTO<Usuario> buscarxNombre(String nombre) {

        List<Usuario> lu =
                ur.findByNombreIgnoreCase(nombre);

        return new ListarDTO<>(
                lu.size(),
                lu
        );
    }

    // =====================================================
    // BUSCAR POR ROL
    // =====================================================

    public List<Usuario> buscarxRol(String rol) {

        return ur.findByRolIgnoreCase(rol);
    }

    // =====================================================
    // GUARDAR
    // =====================================================

    public SimpleDTO Guardar(UsuarioDTO dto) {

        // =====================================================
        // VALIDAR CORREO
        // =====================================================

        if (ur.existsByCorreo(dto.getCorreo())) {

            throw new IllegalArgumentException(
                    "[+] Ya Existe Un Usuario Con Ese Correo [>_<] ... "
            );
        }

        // =====================================================
        // VALIDAR RUT
        // =====================================================

        if (ur.existsByRutUsuario(dto.getRutUsuario())) {

            throw new IllegalArgumentException(
                    "[+] Ya Existe Un Usuario Con Este Rut [>_<] ... "
            );
        }

        // =====================================================
        // CREAR USUARIO
        // =====================================================

        Usuario u = new Usuario();

        u.setRutUsuario(dto.getRutUsuario());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setCorreo(dto.getCorreo());

        // =====================================================
        // ENCRIPTAR CONTRASEÑA
        // =====================================================

        u.setContraseña(
                passwordEncoder.encode(dto.getContraseña())
        );

        u.setRol(dto.getRol());
        u.setActivo(true);

        ur.save(u);

        return new SimpleDTO(
                "[+] Usuario Creado Exitosamente [>_<] ... "
        );
    }

    // =====================================================
    // ACTUALIZAR
    // =====================================================

    public SimpleDTO Actualizar(Integer id, UsuarioDTO dto) {

        Usuario u = ur.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "[+] Usuario No Encontrado [>_<] ... "
                        )
                );

        // =====================================================
        // VALIDAR CORREO DUPLICADO
        // =====================================================

        if (!u.getCorreo().equalsIgnoreCase(dto.getCorreo())
                && ur.existsByCorreo(dto.getCorreo())) {

            throw new IllegalArgumentException(
                    "[+] Ya Existe Un Usuario Con Ese Correo [>_<] ... "
            );
        }

        // =====================================================
        // VALIDAR RUT DUPLICADO
        // =====================================================

        if (!u.getRutUsuario().equalsIgnoreCase(dto.getRutUsuario())
                && ur.existsByRutUsuario(dto.getRutUsuario())) {

            throw new IllegalArgumentException(
                    "[+] Ya Existe Un Usuario Con Ese Rut [>_<] ... "
            );
        }

        // =====================================================
        // ACTUALIZAR DATOS
        // =====================================================

        u.setRutUsuario(dto.getRutUsuario());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setCorreo(dto.getCorreo());

        // =====================================================
        // ENCRIPTAR NUEVA CONTRASEÑA
        // =====================================================

        u.setContraseña(
                passwordEncoder.encode(dto.getContraseña())
        );

        u.setRol(dto.getRol());

        ur.save(u);

        return new SimpleDTO(
                "[+] Usuario Actualizado Correctamente [>_<] ... "
        );
    }

    // =====================================================
    // ELIMINAR
    // =====================================================

    public SimpleDTO Eliminar(Integer id) {

        Usuario u = ur.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "[+] Usuario No Encontrado [>_<] ... "
                        )
                );

        ur.delete(u);

        return new SimpleDTO(
                "[+] Usuario Eliminado Correctamente [>_<] ... "
        );
    }

    // =====================================================
    // CAMBIAR ESTADO
    // =====================================================

    public SimpleDTO CambiarEstado(Integer id, Boolean activo) {

        Usuario u = ur.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "[+] Usuario No Encontrado [>_<] ... "
                        )
                );

        u.setActivo(activo);

        ur.save(u);

        String estado =
                activo ? "Activado" : "Desactivado";

        return new SimpleDTO(
                "[+] Usuario " + estado + " Correctamente [>_<] ... "
        );
    }

}