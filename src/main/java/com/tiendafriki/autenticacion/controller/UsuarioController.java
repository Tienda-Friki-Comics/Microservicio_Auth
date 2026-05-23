package com.tiendafriki.autenticacion.controller;

import org.springframework.beans.factory.annotation.*;
import com.tiendafriki.autenticacion.repo.UsuarioRepo;
import com.tiendafriki.autenticacion.model.Usuario;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.tiendafriki.autenticacion.dto.*;
import jakarta.validation.*;
import java.util.*;


@RestController
@RequestMapping("/auth")
public class UsuarioController {

    @Autowired
    private UsuarioRepo ur;

    @GetMapping("/listar")
    public ResponseEntity <?> Listar() {
        List <Usuario> lu = ur.findAll();
        if (lu.isEmpty()) {
            return ResponseEntity.status(404).body(
                new SimpleDTO("[+] No Hay Usuarios Resgistrados [-_-] ... ")
            );
        }
        return ResponseEntity.ok(new ListarDTO <> (lu.size(), lu));
    }

    @GetMapping("/buscarxid/{id}")
    public ResponseEntity <?> buscarxID(@PathVariable Integer id) {
        Optional <Usuario> lu = ur.findByID(id);
        if (lu.isPresent()) {
            return ResponseEntity.ok(lu.get());
        }
        return ResponseEntity.status(404).body(new SimpleDTO(
            "[+] Usuario No Encontrado Verifica El ID [>_<] ... "
        ));
    }

    @GetMapping("/buscarxnombre/{nombre}")
    public ResponseEntity <?> buscarxNombre(@PathVariable String nombre) {
        List <Usuario> lu = ur.findByNombreIgnoreCase(nombre);
        if (lu.isEmpty()) {
            return ResponseEntity.status(404).body(new SimpleDTO(
                "[+] No Se Encontro Al Usuario Con Respectivo Nombre [>_<] ... "
            ));
        }
        return ResponseEntity.ok(new ListarDTO <> (lu.size(), lu));
    }

    @GetMapping("buscarxrutusuario")
    public ResponseEntity <?> buscarxRut(@PathVariable String rutUsuario) {
        Optional <Usuario> lu = ur.findByRutUsuario(rutUsuario);
        if (lu.isPresent()) {
            return ResponseEntity.ok(lu.get());
        }
        return ResponseEntity.status(404).body(new SimpleDTO(
            "[+] Usuario No Encontrado Revisa Si El Rut Esta Bien [>_<] ... "
        ));
    }

    @GetMapping("/buscarxcorreo/{correo}")
    public ResponseEntity <?> buscarxCorreo(@PathVariable String correo) {
        Optional <Usuario> lu = ur.findByCorreo(correo);
        if (lu.isPresent()) {
            return ResponseEntity.ok(lu.get());
        }
        return ResponseEntity.status(404).body(new SimpleDTO(
            "[+] Usuario No Fue Encontrado Revisa El Correo [>_<] ... "
        ));
    }

    @PostMapping("/crear")
    public ResponseEntity <?> Crear(@Valid @RequestBody UsuarioDTO dto) {
        if (ur.existsByCorreo(dto.getCorreo())) {
            return ResponseEntity.status(400).body(new SimpleDTO(
                "[+] Ya Existe Un Usuario Con Ese Correo, Intenta Otro [>_<] ... "
            ));
        }

        Usuario u = new Usuario();
        u.setRutUsuario(dto.getRutUsuario());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setCorreo(dto.getCorreo());
        u.setContraseña(dto.getContraseña());
        u.setRol(dto.getRol());
        u.setActivo(true);
        ur.save(u);            
        return ResponseEntity.status(201).body(new SimpleDTO(
            "[+] Usuario Creado Exitosamente [>_<] ... "
        ));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity <?> Actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO dto) {
        Optional <Usuario> lu = ur.findByID(id);
        if (lu.isEmpty()) {
            return ResponseEntity.status(404).body(new SimpleDTO(
                "[+] Usuario No Encontrado Error 404 [>_<] ... "
            ));
        }
        Usuario u = lu.get();
        if (!u.getCorreo().equalsIgnoreCase(dto.getCorreo()) && ur.existsByCorreo(dto.getCorreo())) {
            return ResponseEntity.status(404).body(new SimpleDTO(
                "[+] Ya Existe Este Usuario Con Este Correo [>_<] ... "
            ));
        } if (!u.getRutUsuario().equalsIgnoreCase(dto.getRutUsuario()) && ur.existsByRutUsuario(dto.getRutUsuario())) {
            return ResponseEntity.status(404).body(new SimpleDTO(
                "[+] Ya Existe Este Usuario Con Este Rut Por favor Intenta Otro [>_<] ... "
            ));
        }
        u.setRutUsuario(dto.getRutUsuario());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setCorreo(dto.getCorreo());
        u.setContraseña(dto.getContraseña());
        u.setRol(dto.getRol());
        ur.save(u);
        return ResponseEntity.ok(new SimpleDTO(
            "[+] Usuario Actualizado Correctamente [>_<] ... "
        ));
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity <?> Eliminar(@PathVariable Integer id) {
        Optional <Usuario> lu = ur.findByID(id);
        if (lu.isEmpty()) {
            return ResponseEntity.status(404).body(new SimpleDTO(
                "[+] Usuario No Encontrado Revisa El ID [>_<] ... "
            ));
        }
        ur.deleteById(id);
        return ResponseEntity.ok(new SimpleDTO(
            "[+] Usuario Eliminado Exitosamente Vuelve A La Lista Para Verificarlo [>_<] ... "
        ));
    }

}