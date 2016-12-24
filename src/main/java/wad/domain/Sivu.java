/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Jani
 */
@Entity
public class Sivu {

    @Id
    private String url;
    private String salasana;
    private String nimi;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "sivu_linkki_id")
    private List<Linkki> linkit;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "sivu_kommentti_id")
    private List<Kommentti> kommentit;
    private boolean nakyvilla = true;
    @OneToOne(fetch=FetchType.EAGER , cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name = "sivu_ilmianto_id")
    private Ilmianto ilmianto = null;
    
    public void setUrl(String nimi) {
        this.url = nimi;
    }
    
    public void piilota() {
        this.nakyvilla = false;
    }
    
    public void nayta() {
        this.nakyvilla = true;
    }
    
    public boolean getNakyvilla() {
        return this.nakyvilla;
    }

    public void lisaaLinkki(Linkki linkki) {
        if (this.linkit == null) {
            this.linkit = new ArrayList<>();
        }
        this.linkit.add(linkki);
    }

    public List<Linkki> getLinkit() {
        if (this.linkit == null) {
            this.linkit = new ArrayList<>();
        }
        return this.linkit;
    }

    public void lisaaKommentti(Kommentti k) {
        if (this.kommentit == null) {
            this.kommentit = new ArrayList<>();
        }
        this.kommentit.add(k);
    }

    public List<Kommentti> getKommentit() {
        if (this.kommentit == null) {
            this.kommentit = new ArrayList<>();
        }
        Collections.reverse(this.kommentit);
        return this.kommentit;
    }

    public void setName(String nimi) {
        this.nimi = nimi;
    }

    public String getName() {
        return this.nimi;
    }

    public String getUrl() {
        return this.url;
    }
    
    public void setIlmianto(Ilmianto ilmianto) {
        this.ilmianto = ilmianto;
    }
    
    public Ilmianto getIlmianto() {
        return this.ilmianto;
    }
    
    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }
    
    public String getSalasana() {
        return this.salasana;
    }

}
