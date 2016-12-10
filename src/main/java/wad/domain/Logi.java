/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.domain;

import javax.persistence.Entity;
import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 *
 * @author Jani
 */
@Entity
public class Logi extends AbstractPersistable<Long> {
    private String logi;
    
    public void setLogi(String log) {
        this.logi = log;
    }
    
    public String getLogi() {
        return this.logi;
    }
}
