package com.tiendafriki.autenticacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.tiendafriki.autenticacion.dto.UsuarioDTO;
import com.tiendafriki.autenticacion.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")

public class UsuarioController {

    @Autowired
    private UsuarioService us;

    // =====================================================
    // LISTAR
    // =====================================================

    @GetMapping("/listar")
    public ResponseEntity<?> Listar() {

        return ResponseEntity.ok(
                us.listar()
        );
    }

    // =====================================================
    // BUSCAR POR ID
    // =====================================================

    @GetMapping("/buscarxid/{id}")
    public ResponseEntity<?> buscarxID(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                us.buscarxID(id)
        );
    }

    // =====================================================
    // BUSCAR POR NOMBRE
    // =====================================================

    @GetMapping("/buscarxnombre/{nombre}")
    public ResponseEntity<?> buscarxNombre(
            @PathVariable String nombre
    ) {

        return ResponseEntity.ok(
                us.buscarxNombre(nombre)
        );
    }

    // =====================================================
    // BUSCAR POR RUT
    // =====================================================

    @GetMapping("/buscarxrutusuario/{rutUsuario}")
    public ResponseEntity<?> buscarxRut(
            @PathVariable String rutUsuario
    ) {

        return ResponseEntity.ok(
                us.buscarxRutUsuario(rutUsuario)
        );
    }

    // =====================================================
    // CREAR
    // =====================================================

    @PostMapping("/crear")
    public ResponseEntity<?> Crear(
            @Valid @RequestBody UsuarioDTO dto
    ) {

        return ResponseEntity.status(201).body(
                us.Guardar(dto)
        );
    }

    // =====================================================
    // ACTUALIZAR
    // =====================================================

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> Actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioDTO dto
    ) {

        return ResponseEntity.ok(
                us.Actualizar(id, dto)
        );
    }

    // =====================================================
    // ELIMINAR
    // =====================================================

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> Eliminar(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                us.Eliminar(id)
        );
    }

}