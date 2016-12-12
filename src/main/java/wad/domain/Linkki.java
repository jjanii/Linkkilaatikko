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
public class Linkki extends AbstractPersistable<Long> {

    private String url;
    private String nimi;
    private String kuvaus;
    @ManyToOne
    private Sivu sivu;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSivu(Sivu sivu) {
        this.sivu = sivu;
    }

    public Sivu getSivu() {
        return this.sivu;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    public String getNimi() {
        return this.nimi;
    }

    public String getKuvaus() {
        return this.kuvaus;
    }

    public String getUrl() {
        return this.url;
    }
}
