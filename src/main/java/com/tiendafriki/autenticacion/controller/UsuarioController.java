package com.tiendafriki.autenticacion.controller;

import com.tiendafriki.autenticacion.service.UsuarioService;
import com.tiendafriki.autenticacion.model.Usuario;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.tiendafriki.autenticacion.dto.*;
import jakarta.validation.*;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class UsuarioController {

    private final UsuarioService us;

    public UsuarioController(UsuarioService us) {
        this.us = us;
    }

    @GetMapping("/listar")
    public ResponseEntity<?> Listar() {
        ListarDTO<Usuario> resultado = us.listar();
        if (resultado.getTotal() == 0) {
            return ResponseEntity.status(404).body(
                new SimpleDTO("[+] No Hay Usuarios Resgistrados [-_-] ... ")
            );
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/buscarxid/{id}")
    public ResponseEntity<?> buscarxID(@PathVariable Integer id) {
        Optional<Usuario> lu = us.buscarxID(id);
        if (lu.isPresent()) {
            return ResponseEntity.ok(lu.get());
        }
        return ResponseEntity.status(404).body(new SimpleDTO(
            "[+] Usuario No Encontrado Verifica El ID [>_<] ... "
        ));
    }

    @GetMapping("/buscarxnombre/{nombre}")
    public ResponseEntity<?> buscarxNombre(@PathVariable String nombre) {
        ListarDTO<Usuario> resultado = us.buscarxNombre(nombre);
        if (resultado.getTotal() == 0) {
            return ResponseEntity.status(404).body(new SimpleDTO(
                "[+] No Se Encontro Al Usuario Con Respectivo Nombre [>_<] ... "
            ));
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/buscarxrutusuario/{rutUsuario}")
    public ResponseEntity<?> buscarxRut(@PathVariable String rutUsuario) {
        Optional<Usuario> lu = us.buscarxRutUsuario(rutUsuario);
        if (lu.isPresent()) {
            return ResponseEntity.ok(lu.get());
        }
        return ResponseEntity.status(404).body(new SimpleDTO(
            "[+] Usuario No Encontrado Revisa Si El Rut Esta Bien [>_<] ... "
        ));
    }

    @GetMapping("/buscarxcorreo/{correo}")
    public ResponseEntity<?> buscarxCorreo(@PathVariable String correo) {
        Optional<Usuario> lu = us.buscarxCorreo(correo);
        if (lu.isPresent()) {
            return ResponseEntity.ok(lu.get());
        }
        return ResponseEntity.status(404).body(new SimpleDTO(
            "[+] Usuario No Fue Encontrado Revisa El Correo [>_<] ... "
        ));
    }

    @PostMapping("/crear")
    public ResponseEntity<?> Crear(@Valid @RequestBody UsuarioDTO dto) {
        SimpleDTO resultado = us.Guardar(dto);
        boolean exito = resultado.getMensaje().contains("Exitosamente");
        return ResponseEntity.status(exito ? 201 : 400).body(resultado);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> Actualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO dto) {
        SimpleDTO resultado = us.Actualizar(id, dto);
        boolean noEncontrado = resultado.getMensaje().contains("No Fue Encontrado");
        boolean duplicado = resultado.getMensaje().contains("Ya Existe");
        if (noEncontrado || duplicado) {
            return ResponseEntity.status(404).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> Eliminar(@PathVariable Integer id) {
        SimpleDTO resultado = us.Eliminar(id);
        if (resultado.getMensaje().contains("No Fue Encontrado")) {
            return ResponseEntity.status(404).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }

    @PatchMapping("/estado/{id}")
    public ResponseEntity<?> CambiarEstado(@PathVariable Integer id, @RequestParam Boolean activo) {
        SimpleDTO resultado = us.CambiarEstado(id, activo);
        if (resultado.getMensaje().contains("No Fue Encontrado")) {
            return ResponseEntity.status(404).body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/carrito/{id}")
    public ResponseEntity<?> ObtenerCarrito(@PathVariable Integer id) {
        Object resultado = us.obtenerCarrito(id);
        return ResponseEntity.ok(resultado);
    }

}