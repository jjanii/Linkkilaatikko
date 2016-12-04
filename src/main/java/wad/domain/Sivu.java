/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Jani
 */
@Entity
public class Sivu {

    @Id
    private String url;
    private String nimi;
    @OneToMany
    private List<Linkki> linkit;

    public void setUrl(String nimi) {
        this.url = nimi;
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

    public void setName(String nimi) {
        this.nimi = nimi;
    }

    public String getName() {
        return this.nimi;
    }

    public String getUrl() {
        return this.url;
    }

}
