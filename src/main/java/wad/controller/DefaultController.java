/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Logi;
import wad.domain.Sivu;
import wad.repository.LogiRepository;
import wad.repository.SivuRepository;

/**
 *
 * @author Jani
 */
@Controller
public class DefaultController {

    @Autowired
    private SivuRepository SR;
    @Autowired
    private LogiRepository LogiRepository;
    @RequestMapping("*")
    public String doDefaultRedirect(Model model) {
        model.addAttribute("sivut", SR.findAll());
        return "index";
    }

    @RequestMapping(value = "/admin/logs")
    public String getLogs(Model model) {
        model.addAttribute("logs", LogiRepository.findAllByOrderByIdDesc());
        return "logs";
    }

}
