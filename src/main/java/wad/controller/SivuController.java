/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.net.URL;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.Kommentti;
import wad.domain.Linkki;
import wad.domain.Logi;
import wad.domain.Sivu;
import wad.repository.KommenttiRepository;
import wad.repository.LinkkiRepository;
import wad.repository.LogiRepository;
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
    @Autowired
    private KommenttiRepository KR;
    @Autowired
    private LogiRepository LogiRepository;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String sivu(Model model, @PathVariable String name, HttpServletRequest request) {
        if (SR.findOne(name) != null) {
            model.addAttribute("linkit", SR.findOne(name).getLinkit());
            model.addAttribute("name", name);
            model.addAttribute("kommentit", SR.findOne(name).getKommentit());
            return "sivu";
        }
        String ip = request.getRemoteAddr();
        Logi log = new Logi();
        String logi = ip + " loi uuden sivun " + name;
        log.setLogi(logi);
        LogiRepository.save(log);
        Sivu sivu = new Sivu();

        sivu.setName(name);
        sivu.setUrl(name);
        SR.save(sivu);

        return "redirect:/" + sivu.getUrl();
    }

    @RequestMapping(value = "/{nimi}/add", method = RequestMethod.POST)
    public String post(@PathVariable String nimi, @RequestParam String url, HttpServletRequest request, @RequestParam String kuvaus, @RequestParam String nimi1) {
        Linkki link = new Linkki();
        link.setUrl(url);
        link.setKuvaus(kuvaus);
        link.setNimi(nimi1);
        link.setSivu(SR.findOne(nimi));

        if (link.isValid()) {
            SR.findOne(nimi).lisaaLinkki(link);
            LR.save(link);

            String ip = request.getRemoteAddr();
            Logi log = new Logi();
            String logi = ip + " lisäsi linkin " + url + " sivulle " + nimi;
            log.setLogi(logi);
            LogiRepository.save(log);

        }
        return "redirect:/" + nimi;
    }

    @RequestMapping(value = "/{nimi}/kommentti", method = RequestMethod.POST)
    public String kommentti(@PathVariable String nimi, @RequestParam String kommentti, @RequestParam String nimimerkki) {
        Kommentti k = new Kommentti();
        String viesti = kommentti + " - " + nimimerkki;
        k.setKommentti(viesti);
        k.setSivu(SR.findOne(nimi));

        SR.findOne(nimi).lisaaKommentti(k);
        KR.save(k);

        return "redirect:/" + nimi;
    }

    @RequestMapping(value = "/{nimi}/delete/{linkki}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String nimi, @PathVariable Long linkki, HttpServletRequest request) {
        LR.delete(linkki);
        String ip = request.getRemoteAddr();
        Logi log = new Logi();
        String logi = ip + " poisti linkin " + LR.findOne(linkki).getUrl() + " sivulta " + nimi;
        log.setLogi(logi);
        LogiRepository.save(log);
        return "redirect:/" + nimi;
    }

}
