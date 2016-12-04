/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.net.URL;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Linkki;
import wad.domain.Sivu;
import wad.repository.LinkkiRepository;
import wad.repository.SivuRepository;

/**
 *
 * @author Jani
 */
@Controller
public class SivuController {

    @Autowired
    private LinkkiRepository LR;
    @Autowired
    private SivuRepository SR;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String sivu(Model model, @PathVariable String name) {
        if (SR.findOne(name) != null) {
            model.addAttribute("linkit", SR.findOne(name).getLinkit());
            model.addAttribute("name", name);
            return "sivu";
        }

        Sivu sivu = new Sivu();

        sivu.setName(name);
        sivu.setUrl(name);
        SR.save(sivu);

        return "redirect:/" + sivu.getUrl();
    }

    @RequestMapping(value = "/{nimi}/add", method = RequestMethod.POST)
    public String post(@PathVariable String nimi, @RequestParam String url, @RequestParam String kuvaus, @RequestParam String nimi1) {
        Linkki link = new Linkki();
        link.setUrl(url);
        link.setKuvaus(kuvaus);
        link.setNimi(nimi1);
        link.setSivu(SR.findOne(nimi));

        if (link.isValid()) {
            SR.findOne(nimi).lisaaLinkki(link);
            LR.save(link);
        }
        return "redirect:/" + nimi;
    }

    @RequestMapping(value = "/{nimi}/delete/{linkki}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String nimi, @PathVariable Long linkki) {
        LR.delete(linkki);
        return "redirect:/" + nimi;
    }

}
