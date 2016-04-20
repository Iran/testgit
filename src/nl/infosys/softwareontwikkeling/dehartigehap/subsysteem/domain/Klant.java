/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain;

/**
 *
 * @author J. Bouman
 */
public class Klant {
    private int klantId,tabletId,tafelId;
    private String naam;
    
    public Klant(int klantId, int tabletId,int tafelId, String naam) {
        this.klantId = klantId;
        this.tabletId = tabletId;
        this.tafelId = tafelId;
        this.naam = naam;
    }
    
    public String getNaam()
    {
        return naam;
    }
    
    public int getKlantId()
    {
        return klantId;
    }
}


