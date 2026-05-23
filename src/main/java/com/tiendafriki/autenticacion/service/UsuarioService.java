package com.tiendafriki.autenticacion.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.*;
import com.tiendafriki.autenticacion.repo.UsuarioRepo;
import com.tiendafriki.autenticacion.model.Usuario;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.Service;
import com.tiendafriki.autenticacion.dto.*;
import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepo ur;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${carrito.service.url}")
    private String csurl;

    public ListarDTO <Usuario> listar() {
        List <Usuario> lu = ur.findAll();
        return new ListarDTO <> (lu.size(), lu);
    }

    public Optional <Usuario> buscarxID(Integer id) {
        return ur.findByID(id);
    }

    public Optional <Usuario> buscarxRutUsuario(String rutUsuario) {
        return ur.findByRutUsuario(rutUsuario);
    }

    public ListarDTO <Usuario> buscarxNombre(String nombre) {
        List <Usuario> lu = ur.findByNombreIgnoreCase(nombre);
        return new ListarDTO <> (lu.size(), lu);
    }

    public Optional <Usuario> buscarxRol(String rol) {
        return ur.findByRolIgnoreCase(rol);
    }

    public SimpleDTO Guardar(UsuarioDTO dto) {
        if (ur.existsByCorreo(dto.getCorreo())) {
            return new SimpleDTO("[+] Ya Existe Un Usuario Con Ese Correo : " + dto.getCorreo() + " [>_<] ... ");
        } if (ur.existsByRutUsuario(dto.getRutUsuario())) {
            return new SimpleDTO("[+] Ya Existe Un Usuario Con Este Rut [>_<] ... ");
        }
        Usuario u = new Usuario();
        u.setRutUsuario(dto.getRutUsuario());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setCorreo(dto.getCorreo());
        u.setContraseña(passwordEncoder.encode(dto.getContraseña()));
        u.setRol(dto.getRol());
        u.setActivo(true);
        ur.save(u);
        return new SimpleDTO("[+] Usuario Creado Exitosamente Para : " + dto.getNombre() + " [>_<] ... ");
    }

    public SimpleDTO Actualizar(Integer id, UsuarioDTO dto) {
        Optional <Usuario> lu = ur.findByID(id);
        if (lu.isPresent()) {
            Usuario u = lu.get();
            if (!u.getCorreo().equalsIgnoreCase(dto.getCorreo()) && ur.existsByCorreo(dto.getCorreo())) {
                return new SimpleDTO("[+] Ya Existe Un Usuario Con Este Correo : " + dto.getCorreo() + " [>_<] ... ");
            } if (!u.getRutUsuario().equalsIgnoreCase(dto.getRutUsuario()) && ur.existsByRutUsuario(dto.getRutUsuario())) {
                return new SimpleDTO("[+] Ya Existe Un Usuario Con Este Rut [>_<] ... ");
            }   
            u.setRutUsuario(dto.getRutUsuario());
            u.setNombre(dto.getNombre());
            u.setApellido(dto.getApellido());
            u.setCorreo(dto.getCorreo());
            u.setContraseña(passwordEncoder.encode(dto.getContraseña()));
            u.setRol(dto.getRol());
            ur.save(u);
            return new SimpleDTO("[+] Usuario Actualizado Correctamente [>_<] ... ");
        }
        return new SimpleDTO("[+] Usuario Con El ID : " + id + " No Fue Encontrado [>_<] ... ");
    }

    public SimpleDTO Eliminar(Integer id) {
        Optional <Usuario> lu = ur.findByID(id);
        if (lu.isPresent()) {
            ur.deleteById(id);
            return new SimpleDTO("[+] Usuario Eliminado Correctamente [>_<] ... ");
        }
        return new SimpleDTO("[+] Usuario Del ID : " + id + " No Fue Encontrado [>_<] ... ");
    }

    public SimpleDTO CambiarEstado(Integer id, Boolean activo) {
        Optional <Usuario> lu = ur.findByID(id);
        if (lu.isPresent()) {
            Usuario u = lu.get();
            u.setActivo(activo);
            ur.save(u);
            String Estado = activo ? "Activado" : "Desactivado";
            return new SimpleDTO("[+] Usuario " + Estado + " Correcatamente [>_<] ... ");
        }
        return new SimpleDTO("[+] Usuario Del ID : " + id + " No Fue Encontrado [>_<] ... ");
    }

    public Object obtenerCarrito(Integer usuarioID) {
        Optional <Usuario> lu = ur.findByID(usuarioID);
        if (lu.isEmpty()) {
            return new SimpleDTO("[+] Usuario Del ID : " + usuarioID + " No Fue Encontrado [>_<] ... ");
        }
        String url = csurl + "carrito/buscarxrutusuario" + lu.get().getRutUsuario();
        try {
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            return new SimpleDTO("[+] No Se Pudo Conectar Con El Microservicio Del Carrito [<_<] ... ");
        }
    }

}