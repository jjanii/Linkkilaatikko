/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        if (name.equals("admin")) {
            return "redirect:/admin/index";
        }
        
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
    public String kommentti(@PathVariable String nimi, HttpServletRequest request, @RequestParam String kommentti, @RequestParam String nimimerkki) {
        if (kommentti.isEmpty() || nimimerkki.isEmpty()) {
            return "redirect:/" + nimi;
        }
        Kommentti k = new Kommentti();
        String viesti = kommentti + " - " + nimimerkki;
        k.setKommentti(viesti);
        k.setSivu(SR.findOne(nimi));

        String ip = request.getRemoteAddr();
        Logi log = new Logi();
        String logi = ip + " lisäsi kommentin " + viesti + " sivulle " + nimi;
        log.setLogi(logi);
        LogiRepository.save(log);

        SR.findOne(nimi).lisaaKommentti(k);
        KR.save(k);

        return "redirect:/" + nimi;
    }

    @RequestMapping(value = "/{nimi}/delete/{linkki}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String nimi, @PathVariable Long linkki, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Logi log = new Logi();
        String logi = ip + " poisti linkin " + LR.findOne(linkki).getUrl() + " sivulta " + nimi;
        log.setLogi(logi);
        LogiRepository.save(log);
        LR.delete(linkki);
        return "redirect:/" + nimi;
    }

    @RequestMapping(value = "/{nimi}/piilota", method = RequestMethod.POST)
    public String piilota(@PathVariable String nimi, HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        Sivu s = SR.findOne(nimi);
        s.piilota();
        SR.save(s);
        String ip = request.getRemoteAddr();
        Logi log = new Logi();
        String logi = ip + " asetti sivun " + nimi + " pois näkyviltä";
        log.setLogi(logi);
        LogiRepository.save(log);
        redirectAttributes.addFlashAttribute("message", "Sivu piilotettu etusivun listalta");

        return "redirect:/" + nimi;
    }

    @RequestMapping(value = "/{nimi}/nayta", method = RequestMethod.POST)
    public String nayta(@PathVariable String nimi, HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        Sivu s = SR.findOne(nimi);
        s.nayta();
        redirectAttributes.addFlashAttribute("message", "Sivu näkyvillä etusivun listalla");
        SR.save(s);
        String ip = request.getRemoteAddr();
        Logi log = new Logi();
        String logi = ip + " asetti sivun " + nimi + " näkyville";
        log.setLogi(logi);
        LogiRepository.save(log);
        return "redirect:/" + nimi;
    }

}
