package com.iesvdc.dam.demo2.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iesvdc.dam.demo2.modelos.Usuario;
import com.iesvdc.dam.demo2.repositorios.RepoUsuario;


@Controller
@RequestMapping("/admin/usuario")
public class UsersController {
    @Autowired
    RepoUsuario repoUsuario;

    @Autowired
    org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder;

    @GetMapping({"","/"})
    public String findAll(Model modelo) {
        modelo.addAttribute("titulo", "ListaUsers");
            modelo.addAttribute("listaUsuarios", repoUsuario.findAll());
        return "users/List";
    }




    @GetMapping("/add")
    public String addUsuarioForm(Model modelo) {
        modelo.addAttribute("accion", "añadir");
        modelo.addAttribute("usuario", new Usuario());
        modelo.addAttribute("roles", com.iesvdc.dam.demo2.modelos.Rol.values());
        return "users/create";
    }

    @PostMapping("/add")
    public String addUsuario(@ModelAttribute("usuario") Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }
        repoUsuario.save(usuario);
        return "redirect:/admin/usuario";
    }

    @GetMapping("/edit/{id}")
    public String editUsuarioForm(Model modelo, @PathVariable("id") Long id) {
        var o = repoUsuario.findById(id);
        if (o.isPresent()) {
            modelo.addAttribute("accion", "editar");
            modelo.addAttribute("usuario", o.get());
            modelo.addAttribute("roles", com.iesvdc.dam.demo2.modelos.Rol.values());
            return "users/create";
        }
        modelo.addAttribute("titulo", "Gestión de Usuarios");
        modelo.addAttribute("mensaje", "Ese usuario no existe");
        return "error";
    }

    @PostMapping("/edit/{id}")
    public String editUsuario(@ModelAttribute("usuario") Usuario usuario, @PathVariable("id") Long id) {
        var o = repoUsuario.findById(id);
        if (o.isPresent()) {
            Usuario existente = o.get();
            if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
                usuario.setPassword(existente.getPassword());
            } else {
                usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            }
            usuario.setId(id);
            repoUsuario.save(usuario);
            return "redirect:/admin/usuario";
        }
        return "redirect:/admin/usuario";
    }

    @GetMapping("/del/{id}")
    public String delUsuarioForm(Model modelo, @PathVariable("id") Long id) {
        var o = repoUsuario.findById(id);
        if (o.isPresent()) {
            modelo.addAttribute("accion", "borrar");
            modelo.addAttribute("usuario", o.get());
            return "users/create";
        }
        modelo.addAttribute("titulo", "Gestión de Usuarios");
        modelo.addAttribute("mensaje", "Ese usuario no existe");
        return "error";
    }

    @PostMapping("/del/{id}")
    public String delUsuario(@PathVariable("id") Long id) {
        repoUsuario.deleteById(id);
        return "redirect:/admin/usuario";
    }

}
