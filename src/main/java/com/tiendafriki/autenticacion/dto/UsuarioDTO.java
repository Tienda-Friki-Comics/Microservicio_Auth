package com.tiendafriki.autenticacion.dto;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor 
public class UsuarioDTO {

    private String RutUsuario;
    private String Nombre;
    private String Apellido;
    private String Correo;
    private String Contraseña;
    private String Rol;

}