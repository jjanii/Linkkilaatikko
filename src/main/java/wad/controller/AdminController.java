/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Ilmianto;
import wad.domain.Sivu;
import wad.repository.IlmiantoRepository;
import wad.repository.LogiRepository;
import wad.repository.SivuRepository;
import wad.service.AdminService;

/**
 *
 * @author Jani
 */
@Controller
public class AdminController {

    @Autowired
    private LogiRepository logiRepository;
    @Autowired
    private SivuRepository sivuRepository;
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/admin/logs")
    public String getLogs(Model model) {
        model.addAttribute("logs", logiRepository.findAllByOrderByIdDesc());
        return "logs";
    }

    @RequestMapping(value = "/admin/sivut")
    public String listSivut(Model model) {
        model.addAttribute("sivut", sivuRepository.findAll());
        return "sivut";
    }

    @RequestMapping(value = "/admin/sivut/delete/{sivu}/{ilmianto}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String sivu, @PathVariable Long ilmianto) {
        adminService.poistaIlmiannettuSivu(sivu, ilmianto);
        return "redirect:/admin/ilmiannot";
    }

    @RequestMapping(value = "/admin/sivut/delete/{sivu}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String sivu) {
        adminService.poistaSivu(sivu);
        return "redirect:/admin/sivut";
    }

    @RequestMapping(value = "/admin/index", method = RequestMethod.GET)
    public String index() {
        return "adminindex";
    }

    @RequestMapping(value = "/admin/ilmiannot", method = RequestMethod.GET)
    public String listIlmiannot(Model model) {
        List<Ilmianto> ilmiannot = new ArrayList<>();
        for (Sivu s : sivuRepository.findAll()) {
            if (s.getIlmianto() != null) {
                ilmiannot.add(s.getIlmianto());
            }
        }
        model.addAttribute("ilmiannot", ilmiannot);
        return "ilmiannot";
    }

}
