package com.tiendafriki.autenticacion;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.access.AccessDeniedException;
import com.tiendafriki.autenticacion.dto.ErrorDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import java.util.*;
import java.time.*;

@RestControllerAdvice
public class ManejaErrores {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity <ErrorDTO> Validacion(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map <String, String> Errores = new HashMap <> ();
        ex.getBindingResult().getFieldErrors().forEach(Error ->
            Errores.put(Error.getField(), Error.getDefaultMessage())
        );
        ErrorDTO error = new ErrorDTO(
            LocalDateTime.now(),
            400,
            "[+] Error : 400 Error De Validacion [>_<] ... ",
            Errores,  
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity <ErrorDTO> NoAutorizado(AccessDeniedException ex, HttpServletRequest request) {
        ErrorDTO error = new ErrorDTO(
            LocalDateTime.now(),
            401,
            "[+] Error : 401 Error Acceso Denegado [>_<] ... ",
            null,
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity <ErrorDTO> NoEncontrado(NoResourceFoundException ex, HttpServletRequest request) {
        ErrorDTO error = new ErrorDTO(
            LocalDateTime.now(),
            404,
            "[+] Error : 404 Recurso No Encontrado [>_<] ... ",
            null,
            request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity <ErrorDTO> General(Exception ex, HttpServletRequest request) {
        ErrorDTO error = new ErrorDTO(
            LocalDateTime.now(),
            500,
            "[+] Error : 500 Fallo Interno Del Sistema [>_<] ... ",
           null,
           request.getRequestURI() 
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}