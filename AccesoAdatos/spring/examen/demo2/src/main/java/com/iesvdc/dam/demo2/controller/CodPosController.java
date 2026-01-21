package com.iesvdc.dam.demo2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iesvdc.dam.demo2.modelos.CodPos;
import com.iesvdc.dam.demo2.repositorios.RepoCodPos;
import com.iesvdc.dam.demo2.repositorios.RepoProvincia;

@Controller
@RequestMapping("/admin/codpos")
public class CodPosController {
    @Autowired
    RepoCodPos repoCodPos;

    @Autowired
    RepoProvincia repoProvincia;

    @GetMapping({"", "/"})
    public String findAll(Model modelo) {
        modelo.addAttribute("listaCodPos", repoCodPos.findAll());
        return "CodPos/List";
    }

    @GetMapping("/add")
    public String addCodPosForm(Model modelo) {
        modelo.addAttribute("accion", "añadir");
        modelo.addAttribute("codpos", new CodPos());
        modelo.addAttribute("listaProvincias", repoProvincia.findAll());
        return "CodPos/create";
    }

    @PostMapping("/add")
    public String addCodPos(@ModelAttribute("codpos") CodPos codpos) {
        if (codpos.getProvincia() != null && codpos.getProvincia().getCodigo() != null) {
            var op = repoProvincia.findByCodigo(codpos.getProvincia().getCodigo());
            op.ifPresent(codpos::setProvincia);
        }
        repoCodPos.save(codpos);
        return "redirect:/admin/codpos";
    }

    @GetMapping("/edit/{cp}")
    public String editCodPosForm(Model modelo, @PathVariable("cp") Integer cp) {
        var o = repoCodPos.findByCp(cp);
        if (o.isPresent()) {
            modelo.addAttribute("accion", "editar");
            modelo.addAttribute("codpos", o.get());
            modelo.addAttribute("listaProvincias", repoProvincia.findAll());
            return "CodPos/create";
        }
        modelo.addAttribute("titulo", "Gestión de Códigos Postales");
        modelo.addAttribute("mensaje", "Ese código postal no existe");
        return "error";
    }

    @PostMapping("/edit/{cp}")
    public String editCodPos(@ModelAttribute("codpos") CodPos codpos, @PathVariable("cp") Integer cp) {
        // ensure id matches path (cp is PK)
        codpos.setCp(cp);
        if (codpos.getProvincia() != null && codpos.getProvincia().getCodigo() != null) {
            var op = repoProvincia.findByCodigo(codpos.getProvincia().getCodigo());
            op.ifPresent(codpos::setProvincia);
        }
        repoCodPos.save(codpos);
        return "redirect:/admin/codpos";
    }

    @GetMapping("/del/{cp}")
    public String delCodPosForm(Model modelo, @PathVariable("cp") Integer cp) {
        var o = repoCodPos.findByCp(cp);
        if (o.isPresent()) {
            modelo.addAttribute("accion", "borrar");
            modelo.addAttribute("codpos", o.get());
            return "CodPos/create";
        }
        modelo.addAttribute("titulo", "Gestión de Códigos Postales");
        modelo.addAttribute("mensaje", "Ese código postal no existe");
        return "error";
    }

    @PostMapping("/del/{cp}")
    public String delCodPos(@PathVariable("cp") Integer cp) {
        repoCodPos.deleteById(cp);
        return "redirect:/admin/codpos";
    }

}
