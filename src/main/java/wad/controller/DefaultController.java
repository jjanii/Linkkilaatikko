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
import org.springframework.web.bind.annotation.RequestMapping;
import wad.domain.Sivu;
import wad.repository.SivuRepository;

/**
 *
 * @author Jani
 */
@Controller
public class DefaultController {

    @Autowired
    private SivuRepository sivuRepository;
    @RequestMapping("*")
    public String doDefaultRedirect(Model model) {
        List<Sivu> sivut = new ArrayList<>();
        for (Sivu s : sivuRepository.findAll()) {
            if (s.getNakyvilla()) {
                sivut.add(s);
            }
        }
       
        model.addAttribute("sivut", sivut);
        return "index";
    }

}
