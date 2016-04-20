/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain;

import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.
        businesslogic.DutchCurrencyFormat;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.businesslogic.
        DutchDateFormat;
import java.text.DateFormat;
import java.util.*;

/**
 *
 * @author J. Bouman
 */
public class Bestelling {
    private ArrayList<BestelRegel> bestelList;
    private Date datum;
    
    public Bestelling(){
        this.bestelList = new ArrayList<BestelRegel>();
        this.datum = new Date();
    }
    
    public String getFormattedDatumString()
    {
        return (new DutchDateFormat()).getFormattedDatumString(this.datum);
    }
    
    public ArrayList<BestelRegel> getBestelList()
    {
        return this.bestelList;
    }
    
    public void voegToe(BestelRegel br) {
    bestelList.add(br);
}
    
    public void verwijder(BestelRegel br) {
        bestelList.remove(br);
    }
    
    @Override
    public String toString()
    {
        String s = "";
        for(BestelRegel br : bestelList)
        {
            s += String.format("%-10s %-10s %-5d %-10s\n", br.getProductNaam(), 
                    getFormattedMoneyString(br.getProductPrijs()), 
                    br.getAantal(), 
                    getFormattedMoneyString(br.getTotaalPrijs()));
        }
        
        return s;
    }
    
    public String getFormattedMoneyString(double money)
    {
        return (new DutchCurrencyFormat()).getFormattedMoneyString(money);
    }
   
}
