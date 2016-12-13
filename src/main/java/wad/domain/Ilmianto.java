/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Jani
 */
@Entity
public class Ilmianto extends AbstractPersistable<Long> {
    
    @OneToOne
    Sivu sivu;
    String ip;
    public void setSivu(Sivu sivu, String ip) {
        this.sivu = sivu;
        this.ip = ip;
    }
    
    public Sivu getSivu() {
        return this.sivu;
    }
    
    public String getSivunnimi() {
        return this.sivu.getName();
    }
    
    
    public String getIp() {
        return this.ip;
    }

            
}
