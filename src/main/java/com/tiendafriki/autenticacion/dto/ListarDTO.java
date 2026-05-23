package com.tiendafriki.autenticacion.dto;

import java.util.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListarDTO <T> {

    private Integer Total;
    private List <T> Datos;

}