/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Service
public class SivuService {

    @Autowired
    private LinkkiRepository linkkiRepository;
    @Autowired
    private SivuRepository sivuRepository;
    @Autowired
    private KommenttiRepository kommenttiRepository;
    @Autowired
    private LogiRepository logiRepository;

    public void luoSivu(String name, String ip) {
        Logi log = new Logi();
        String logi = ip + " loi uuden sivun " + name;
        log.setLogi(logi);
        logiRepository.save(log);
        Sivu sivu = new Sivu();

        sivu.setName(name);
        sivu.setUrl(name);
        sivuRepository.save(sivu);
    }

    public void luoLinkki(String nimi, String url, String kuvaus, String ip, String nimi1) {
        if (!nimi1.isEmpty() && isValidUrl(url)) {
            Linkki link = new Linkki();
            link.setUrl(url);
            link.setKuvaus(kuvaus);
            link.setNimi(nimi1);
            link.setSivu(sivuRepository.findOne(nimi));

            sivuRepository.findOne(nimi).lisaaLinkki(link);
            linkkiRepository.save(link);
            Logi log = new Logi();
            String logi = ip + " lisäsi linkin " + url + " sivulle " + nimi;
            log.setLogi(logi);
            logiRepository.save(log);
        }
    }

    public void luoKommentti(String nimi, String kommentti, String nimimerkki, String ip) {
        if (!kommentti.isEmpty() && !nimimerkki.isEmpty()) {
            Kommentti k = new Kommentti();
            String viesti = kommentti + " - " + nimimerkki;
            k.setKommentti(viesti);
            k.setSivu(sivuRepository.findOne(nimi));

            Logi log = new Logi();
            String logi = ip + " lisäsi kommentin " + viesti + " sivulle " + nimi;
            log.setLogi(logi);
            logiRepository.save(log);
            sivuRepository.findOne(nimi).lisaaKommentti(k);
            kommenttiRepository.save(k);
        }
    }

    public void poistaLinkki(String nimi, Long linkinId, String ip) {
        Logi log = new Logi();
        String logi = ip + " poisti linkin " + linkkiRepository.findOne(linkinId).getUrl() + " sivulta " + nimi;
        log.setLogi(logi);
        logiRepository.save(log);
        linkkiRepository.delete(linkinId);
    }

    public void piilotaEtusivulta(String nimi, String ip) {
        Sivu s = sivuRepository.findOne(nimi);
        s.piilota();
        sivuRepository.save(s);
        Logi log = new Logi();
        String logi = ip + " asetti sivun " + nimi + " pois näkyviltä";
        log.setLogi(logi);
        logiRepository.save(log);
    }

    public void naytaEtusivulla(String nimi, String ip) {
        Sivu s = sivuRepository.findOne(nimi);
        s.nayta();
        sivuRepository.save(s);
        Logi log = new Logi();
        String logi = ip + " asetti sivun " + nimi + " näkyville";
        log.setLogi(logi);
        logiRepository.save(log);
    }

    public boolean isValidUrl(String url) {
        try {
            new URL(url);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
