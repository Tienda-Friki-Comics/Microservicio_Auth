package com.tiendafriki.autenticacion.model;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @NotBlank(message = "[+] El Rut Del Usuario No Puede Quedar Vacio [>_<] ... ")
    @Max(value = 12, message = "[+] El Rut Debe Ser De 12 Caracteres [>_<] ... ")
    @Column(nullable = false)
    private String RutUsuario;

    @NotBlank(message = "[+] El Nombre No Puede Quedar Vacio [>_<] ... ")
    @Column(nullable = false, length = 100)
    private String Nombre;

    @NotBlank(message = "[+] El Apellido No Puede Quedar Vacio [>_<] ... ")
    @Column(nullable = false, length = 100)
    private String Apellido;

    @NotBlank(message = "[+] El Correo No Puede Quedar Vacio [>_<] ... ")
    @Email(message = "[+] El Correo Tiene Que Tener Un Formato Valido [>_<] ... ")
    @Column(nullable = false, length = 100)
    private String Correo;

    @NotBlank(message = "[+] La Contraseña No Puede Quedar Vacia [>_<] ... ")
    @Min(value = 8, message = "[+] La Contraseña Debe Tener Como Minimo 8 Caracteres [>_<] ... ")
    @Column(nullable = false)
    private String Contraseña;

    @NotBlank(message = "[+] El Rol No Puede Quedar Vacio [>_<] ... ")
    @Column(nullable = false, length = 60)
    private String Rol; 

    @Column(nullable = false)
    private Boolean Activo = true;

}