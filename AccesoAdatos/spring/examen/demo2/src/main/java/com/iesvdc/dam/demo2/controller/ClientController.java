package com.iesvdc.dam.demo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iesvdc.dam.demo2.modelos.Direccion;
import com.iesvdc.dam.demo2.modelos.Usuario;
import com.iesvdc.dam.demo2.repositorios.RepoCodPos;
import com.iesvdc.dam.demo2.repositorios.RepoDireccion;
import com.iesvdc.dam.demo2.repositorios.RepoUsuario;

@Controller
@RequestMapping("/usuario")
public class ClientController {

    @Autowired
    RepoUsuario repoUsuario;

    @Autowired
    RepoDireccion repoDireccion;

    @Autowired
    RepoCodPos repoCodPos;

    @Autowired
    org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder;

    private Usuario getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        String username = auth.getName();
        return repoUsuario.findByUsername(username).orElse(null);
    }

    @GetMapping("/perfil")
    public String perfil(Model modelo) {
        Usuario u = getCurrentUser();
        if (u == null) return "redirect:/login";
        modelo.addAttribute("usuario", u);
        modelo.addAttribute("listaDirecciones", repoDireccion.findByUsuario_Id(u.getId()));
        modelo.addAttribute("direccion", new Direccion());
        modelo.addAttribute("listaCodPos", repoCodPos.findAll());
        return "users/profile";
    }

    @GetMapping("/mis-direcciones")
    public String misDirecciones() {
        return "redirect:/usuario/perfil";
    }

    @PostMapping("/perfil")
    public String updatePerfil(Usuario usuarioForm) {
        Usuario u = getCurrentUser();
        if (u == null) return "redirect:/login";
        // update allowed fields
        u.setTelefono(usuarioForm.getTelefono());
        u.setNif(usuarioForm.getNif());
        u.setEmail(usuarioForm.getEmail());
        if (usuarioForm.getPassword() != null && !usuarioForm.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(usuarioForm.getPassword()));
        }
        repoUsuario.save(u);
        return "redirect:/usuario/perfil";
    }

    @PostMapping("/perfil/addDireccion")
    public String addDireccion(Direccion direccion) {
        Usuario u = getCurrentUser();
        if (u == null) return "redirect:/login";
        direccion.setUsuario(u);
        if (direccion.getCodPos() != null && direccion.getCodPos().getCp() != null) {
            repoCodPos.findByCp(direccion.getCodPos().getCp()).ifPresent(direccion::setCodPos);
        }
        repoDireccion.save(direccion);
        return "redirect:/usuario/perfil";
    }

}
