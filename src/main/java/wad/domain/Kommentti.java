/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Jani
 */
@Entity
public class Kommentti extends AbstractPersistable<Long> {
    private String kommentti;
    @ManyToOne
    private Sivu sivu;
    public String getKommentti() {
        return this.kommentti;
    }
    
    public void setKommentti(String k) {
        this.kommentti = k;
    }
    
    public void setSivu(Sivu sivu) {
        this.sivu = sivu;
    }
    
    public Sivu getSivu() {
        return this.sivu;
    }

    
}
