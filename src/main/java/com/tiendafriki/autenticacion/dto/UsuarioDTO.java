package com.tiendafriki.autenticacion.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

// Este es el DRTO de creacion y actualizacion de usuairios.
// Aqui deben ir las validaciones jakarta

public class UsuarioDTO {

    @NotBlank(message = "[+] El Rut Del Usuario No Puede Quedar Vacio [>_<] ... ")
    @Pattern(
    regexp = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$|^\\d{7,8}-[\\dkK]$",
    message = "[+] El Formato Del Rut Es Invalido [>_<] ... "
    )
    @Size(max = 12,
    message = "[+] El Rut Debe Tener Maximo 12 Caracteres [>_<] ... ")
    private String rutUsuario;

    @NotBlank(message = "[+] El Nombre No Puede Quedar Vacio [>_<] ... ")
    private String nombre;

    @NotBlank(message = "[+] El Apellido No Puede Quedar Vacio [>_<] ... ")
    private String apellido;
    
    @NotBlank(message = "[+] El Correo No Puede Quedar Vacio [>_<] ... ")
    @Email(message = "[+] El Correo Tiene Que Tener Un Formato Valido [>_<] ... ")
    private String correo;
    
    @NotBlank(message = "[+] La Contraseña No Puede Quedar Vacia [>_<] ... ")

    @Size(min = 8,
    message = "[+] La Contraseña Debe Tener Minimo 8 Caracteres [>_<] ... ")
    private String contraseña;
    
    @NotBlank(message = "[+] El Rol No Puede Quedar Vacio [>_<] ... ")
    private String rol;

}