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
import wad.repository.SivuRepository;
import wad.service.SivuService;

/**
 *
 * @author Jani
 */
@Controller
public class SivuController {

    @Autowired
    private SivuRepository sivuRepository;
    @Autowired
    private SivuService sivuService;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String sivu(Model model, @PathVariable String name, HttpServletRequest request) {
        if (name.equals("admin")) {
            return "redirect:/admin/index";
        }

        if (sivuRepository.findOne(name) == null) {
            sivuService.luoSivu(name, request.getRemoteAddr());
            return "redirect:/" + name;
        }

        model.addAttribute("linkit", sivuRepository.findOne(name).getLinkit());
        model.addAttribute("name", name);
        model.addAttribute("kommentit", sivuRepository.findOne(name).getKommentit());
        model.addAttribute("sivu", sivuRepository.findOne(name));
        return "sivu";
    }

    @RequestMapping(value = "/{nimi}/add", method = RequestMethod.POST)
    public String post(@PathVariable String nimi, @RequestParam String url, HttpServletRequest request, @RequestParam String kuvaus, @RequestParam String nimi1) {
        sivuService.luoLinkki(nimi, url, kuvaus, request.getRemoteAddr(), nimi1);
        return "redirect:/" + nimi;
    }

    @RequestMapping(value = "/{nimi}/kommentti", method = RequestMethod.POST)
    public String kommentti(@PathVariable String nimi, HttpServletRequest request, @RequestParam String kommentti, @RequestParam String nimimerkki) {
        sivuService.luoKommentti(nimi, kommentti, nimimerkki, request.getRemoteAddr());
        return "redirect:/" + nimi;
    }

    @RequestMapping(value = "/{nimi}/delete/{linkki}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String nimi, @PathVariable Long linkki, HttpServletRequest request) {
        sivuService.poistaLinkki(nimi, linkki, request.getRemoteAddr());
        return "redirect:/" + nimi;
    }

    @RequestMapping(value = "/{nimi}/piilota", method = RequestMethod.POST)
    public String piilota(@PathVariable String nimi, HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        sivuService.piilotaEtusivulta(nimi, request.getRemoteAddr());
        redirectAttributes.addFlashAttribute("message", "Sivu piilotettu etusivun listalta");
        return "redirect:/" + nimi;
    }

    @RequestMapping(value = "/{nimi}/nayta", method = RequestMethod.POST)
    public String nayta(@PathVariable String nimi, HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        sivuService.naytaEtusivulla(nimi, request.getRemoteAddr());
        redirectAttributes.addFlashAttribute("message", "Sivu näkyvillä etusivun listalla");
        return "redirect:/" + nimi;
    }

    @RequestMapping(value = "/{nimi}/ilmianna", method = RequestMethod.POST)
    public String ilmianna(@PathVariable String nimi, HttpServletRequest request, final RedirectAttributes redirectAttributes) {
        if (sivuRepository.findOne(nimi).getIlmianto() == null) {
            sivuService.ilmianna(nimi, request.getRemoteAddr());
            redirectAttributes.addFlashAttribute("message", "Sivu " + nimi + " ilmiannettu");
        } else {
            redirectAttributes.addFlashAttribute("message", "Sivu on jo ilmiannettu");
        }
        return "redirect:/" + nimi;
    }

}
