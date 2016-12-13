/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wad.domain.Logi;
import wad.repository.IlmiantoRepository;
import wad.repository.LogiRepository;
import wad.repository.SivuRepository;

/**
 *
 * @author Jani
 */
@Service
public class AdminService {

    @Autowired
    private SivuRepository sivuRepository;
    @Autowired
    private LogiRepository logiRepository;
    @Autowired
    private IlmiantoRepository ilmiantoRepository;

    public void poistaSivu(String name) {
        Logi log = new Logi();
        log.setLogi("Admin poisti sivun " + name);
        logiRepository.save(log);
        sivuRepository.delete(name);
    }
    
    public void poistaIlmiannettuSivu(String name, Long ilmiantoId) {
        Logi log = new Logi();
        log.setLogi("Admin poisti ilmiannetun sivun " + name);
        logiRepository.save(log);
        ilmiantoRepository.delete(ilmiantoId);
        sivuRepository.delete(name);
    }
    
    
}
