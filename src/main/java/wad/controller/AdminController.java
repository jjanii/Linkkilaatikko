/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wad.domain.Logi;
import wad.domain.Sivu;
import wad.repository.LogiRepository;
import wad.repository.SivuRepository;

/**
 *
 * @author Jani
 */
@Controller
public class AdminController {

    @Autowired
    private LogiRepository LogiRepository;
    @Autowired
    private SivuRepository SivuRepository;

    @RequestMapping(value = "/admin/logs")
    public String getLogs(Model model) {
        model.addAttribute("logs", LogiRepository.findAllByOrderByIdDesc());
        return "logs";
    }

    @RequestMapping(value = "/admin/sivut")
    public String listSivut(Model model) {
        model.addAttribute("sivut", SivuRepository.findAll());
        return "sivut";
    }

    @RequestMapping(value = "/admin/sivut/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String id) {
        SivuRepository.delete(id);
        return "redirect:/admin/sivut";
    }
    
    @RequestMapping(value = "/admin/index", method = RequestMethod.GET)
    public String index() {
        return "adminindex";
    }

}
