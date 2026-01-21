package com.iesvdc.dam.demo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iesvdc.dam.demo2.modelos.Direccion;
import com.iesvdc.dam.demo2.repositorios.RepoCodPos;
import com.iesvdc.dam.demo2.repositorios.RepoDireccion;
import com.iesvdc.dam.demo2.repositorios.RepoUsuario;



@Controller
@RequestMapping("/admin/usuario/{idUsuario}/direccion")
public class DireccionController {

    @Autowired
    RepoDireccion repoDireccion;

    @Autowired
    RepoUsuario repoUsuario;

    @Autowired
    RepoCodPos repoCodPos;

    @GetMapping({"", "/"})
    public String findAllByUsuario(Model modelo, @PathVariable("idUsuario") Long idUsuario) {
        var usuario = repoUsuario.findById(idUsuario);
        if (usuario.isEmpty()) {
            modelo.addAttribute("titulo", "Usuario no encontrado");
            modelo.addAttribute("mensaje", "El usuario no existe");
            return "error";
        }
        modelo.addAttribute("usuario", usuario.get());
        modelo.addAttribute("listaDirecciones", repoDireccion.findByUsuario_Id(idUsuario));
        return "Direccion/list";
    }

    @GetMapping("/add")
    public String addDireccionForm(Model modelo, @PathVariable("idUsuario") Long idUsuario) {
        var usuario = repoUsuario.findById(idUsuario);
        if (usuario.isEmpty()) {
            modelo.addAttribute("titulo", "Usuario no encontrado");
            modelo.addAttribute("mensaje", "El usuario no existe");
            return "error";
        }
        modelo.addAttribute("accion", "añadir");
        Direccion d = new Direccion();
        modelo.addAttribute("direccion", d);
        modelo.addAttribute("usuario", usuario.get());
        modelo.addAttribute("listaCodPos", repoCodPos.findAll());
        return "Direccion/create";
    }

    @PostMapping("/add")
    public String addDireccion(@ModelAttribute("direccion") Direccion direccion, @PathVariable("idUsuario") Long idUsuario) {
        var usuario = repoUsuario.findById(idUsuario);
        if (usuario.isEmpty()) {
            return "redirect:/admin/usuario";
        }
        direccion.setUsuario(usuario.get());
        // ensure codPos is managed
        if (direccion.getCodPos() != null && direccion.getCodPos().getCp() != null) {
            var ocp = repoCodPos.findByCp(direccion.getCodPos().getCp());
            ocp.ifPresent(direccion::setCodPos);
        }
        repoDireccion.save(direccion);
        return String.format("redirect:/admin/usuario/%d/direccion", idUsuario);
    }

    @GetMapping("/edit/{idDireccion}")
    public String editDireccionForm(Model modelo, @PathVariable("idUsuario") Long idUsuario, @PathVariable("idDireccion") Integer idDireccion) {
        var usuario = repoUsuario.findById(idUsuario);
        if (usuario.isEmpty()) {
            modelo.addAttribute("titulo", "Usuario no encontrado");
            modelo.addAttribute("mensaje", "El usuario no existe");
            return "error";
        }
        var od = repoDireccion.findById(idDireccion);
        if (od.isEmpty()) {
            modelo.addAttribute("titulo", "Dirección no encontrada");
            modelo.addAttribute("mensaje", "La dirección no existe");
            return "error";
        }
        modelo.addAttribute("accion", "editar");
        modelo.addAttribute("direccion", od.get());
        modelo.addAttribute("usuario", usuario.get());
        modelo.addAttribute("listaCodPos", repoCodPos.findAll());
        return "Direccion/create";
    }

    @PostMapping("/edit/{idDireccion}")
    public String editDireccion(@ModelAttribute("direccion") Direccion direccion, @PathVariable("idUsuario") Long idUsuario, @PathVariable("idDireccion") Integer idDireccion) {
        var usuario = repoUsuario.findById(idUsuario);
        if (usuario.isEmpty()) {
            return "redirect:/admin/usuario";
        }
        direccion.setUsuario(usuario.get());
        direccion.setId(idDireccion);
        if (direccion.getCodPos() != null && direccion.getCodPos().getCp() != null) {
            var ocp = repoCodPos.findByCp(direccion.getCodPos().getCp());
            ocp.ifPresent(direccion::setCodPos);
        }
        repoDireccion.save(direccion);
        return String.format("redirect:/admin/usuario/%d/direccion", idUsuario);
    }

    @GetMapping("/del/{idDireccion}")
    public String delDireccionForm(Model modelo, @PathVariable("idUsuario") Long idUsuario, @PathVariable("idDireccion") Integer idDireccion) {
        var od = repoDireccion.findById(idDireccion);
        if (od.isPresent()) {
            modelo.addAttribute("accion", "borrar");
            modelo.addAttribute("direccion", od.get());
            modelo.addAttribute("usuario", od.get().getUsuario());
            return "Direccion/create";
        }
        modelo.addAttribute("titulo", "Dirección no encontrada");
        modelo.addAttribute("mensaje", "La dirección no existe");
        return "error";
    }

    @PostMapping("/del/{idDireccion}")
    public String delDireccion(@PathVariable("idUsuario") Long idUsuario, @PathVariable("idDireccion") Integer idDireccion) {
        repoDireccion.deleteById(idDireccion);
        return String.format("redirect:/admin/usuario/%d/direccion", idUsuario);
    }

}
