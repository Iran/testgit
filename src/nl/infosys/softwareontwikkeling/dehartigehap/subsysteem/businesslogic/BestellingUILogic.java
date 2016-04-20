/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.businesslogic;

import java.util.*;
import javax.swing.*;
import java.lang.IllegalArgumentException;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.presentation.AfrekenWindow;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Bestelling;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Klant;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.BestelRegel;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.datastorage.BestellingDAO;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.datastorage.KlantDAO;

/**
 *
 * @author J. Bouman
 */
public class BestellingUILogic {
    private ArrayList<BestellingProductRow> rowList;
    private ArrayList<Bestelling> bestellingenList;
    private JTextField totaalPrijsVeld, totaalBestellingenVeld;
    private Klant klant;
    
    public BestellingUILogic()
    {
        this.rowList = new ArrayList<BestellingProductRow>();
        this.bestellingenList = new ArrayList<Bestelling>();
        //this.klant = new Klant(0, 0,0,"Maikel van den Hout");
        this.klant = (new KlantDAO()).findKlant("Maikel de Jong");
        if (klant == null) throw (new IllegalArgumentException("KLANT Maikel "
                + "de Jong niet in database"));
    }
    
    public void setTotaalPrijsVeld(JTextField _totaalPrijsVeld)
    {
        this.totaalPrijsVeld = _totaalPrijsVeld;
        this.totaalPrijsVeld.setText(getFormattedMoneyString(
                getTotaalPrijsForAllRows()));
    }
    
    public void addRow(BestellingProductRow bpr)
    {
        rowList.add(bpr);
    }
    public BestellingProductRow findRowForMinusButton(JButton minusButton)
    {
        for(BestellingProductRow bpr : rowList)
        {
            if (bpr.getMinusButton() == minusButton)
            {
                return bpr;
            }
        }
        return null;
    }
    
    public BestellingProductRow findRowForPlusButton(JButton plusButton)
    {
        for(BestellingProductRow bpr : rowList)
        {
            if (bpr.getPlusButton() == plusButton)
            {
                return bpr;
            }
        }
        return null;
    }
    
    public double getTotaalPrijsForAllRows()
    {
        double ret = 0.0;
        for(BestellingProductRow bpr : rowList)
        {
            ret += bpr.getTotaalPrijs();
        }
        return ret;
    }
    
    public void handlePlusButtonClick(java.awt.event.ActionEvent evt) 
    {                                                 
        JButton knop = (JButton)evt.getSource();
        BestellingProductRow bpr = findRowForPlusButton(knop);
        
        bpr.setHuidigAantal(bpr.getHuidigAantal() + 1);
        bpr.setHuidigPrijs(bpr.getProductPrijs() * bpr.getHuidigAantal());
    }
    
    public void handleMinusButtonClick(java.awt.event.ActionEvent evt) 
    {                                                 
        JButton knop = (JButton)evt.getSource();
        BestellingProductRow bpr = findRowForMinusButton(knop);
        
        bpr.setHuidigAantal(bpr.getHuidigAantal() - 1);
        bpr.setHuidigPrijs(bpr.getProductPrijs() * bpr.getHuidigAantal());
    }
    
    public void handleBestelButton(java.awt.event.ActionEvent evt)
    {
        Bestelling b = getBestellingFromAllRows();
        
        if (b != null)
        {
            addBestelling(b);
            (new BestellingDAO()).saveBestelling(b, this.klant);
        }
        
        for(BestellingProductRow bpr : rowList)
        {
            bpr.addToTotaal();
            bpr.resetHuidig();
        }
        

        // Pas de totaalPrijsVeld boven de bestelknop aan
        this.totaalPrijsVeld.setText(getFormattedMoneyString(
                getTotaalPrijsForAllRows()));
        
    }
    
    public void handleAfrekenButtonClick(java.awt.event.ActionEvent evt)
    {
        AfrekenWindow win = new AfrekenWindow();
        win.show();
        win.afreken_bestellingen_textarea.setText(getFormattedBestellingenString());
        
    }
    
    public Bestelling getBestellingFromAllRows()
    {
        Bestelling b = new Bestelling();
        boolean huidigRowsAantallenNul = true;
        
        for(BestellingProductRow bpr : rowList)
        {
            if (bpr.getHuidigAantal() > 0)
            {
                huidigRowsAantallenNul = false;
                BestelRegel br = new BestelRegel(this.klant, bpr.getProduct(), 
                        bpr.getHuidigAantal());        
                b.voegToe(br);
            }
        }
        
        // als alle rijen als huidig aantal geselecteerde producten de waarde
        // nul hebben dan doen we een 'null' bestelling retourneren omdat er
        // geen product geselecteerd is.
        if (huidigRowsAantallenNul == true)
            return null;
         
        return b;
    }
    
    public void addBestelling(Bestelling b)
    {
        if (b == null) return;
        this.bestellingenList.add(b);
    }
    
    public String getFormattedBestellingenString()
    {
        String s = "";
        Integer i = 1;
        for(Bestelling b : this.bestellingenList)
        {
            s += "Bestelling ";
            s += i;
            s += " - ";
            s += b.getFormattedDatumString();
            s += "\n--------------------------\n";
            s += b.toString();
            s += "\n";
            i++;
        }
        
        return s;
    }
    
    public String getFormattedMoneyString(double money)
    {
        return (new DutchCurrencyFormat()).getFormattedMoneyString(money);
    }
}
