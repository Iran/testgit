/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.businesslogic;

import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Product;
import javax.swing.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 *
 * @author J. Bouman
 */
public class BestellingProductRow {
    private Product product;
    private JButton plusButton, minusButton;
    private JLabel productNaamLabel, productPrijsLabel, huidigAantalLabel, 
            huidigPrijsLabel, totaalLabel;
    Double huidigPrijs, totaalPrijs;
    int huidigAantal, totaalAantal;
    
    public BestellingProductRow(Product _product, JButton _plusButton, JButton _minusButton, 
                JLabel _productNaamLabel, JLabel _productPrijsLabel, 
                JLabel _huidigAantalLabel, JLabel _huidigPrijsLabel, JLabel _totaalLabel)
    {
        this.product = _product;
        this.plusButton = _plusButton;
        this.minusButton = _minusButton;
        this.productNaamLabel = _productNaamLabel;
        this.productPrijsLabel = _productPrijsLabel;
        this.huidigAantalLabel = _huidigAantalLabel;
        this.huidigPrijsLabel = _huidigPrijsLabel;
        this.totaalLabel = _totaalLabel;
        
        setHuidigPrijs(0.0);
        setHuidigAantal(0);
        setTotaalPrijs(0.0);
        setTotaalAantal(0);
        this.productNaamLabel.setText(this.product.getNaam());
        
        this.productPrijsLabel.setText(getFormattedMoneyString(
                getProductPrijs()));
        updateTotaalLabel();
    }
    
    public JButton getMinusButton(){
        return this.minusButton;
    }
 
    public JButton getPlusButton(){
        return this.plusButton;
    }
    
    public JLabel getProductNaamLabel()
    {
        return this.productNaamLabel;
    }
 
    public JLabel getProductPrijsLabel()
    {
        return this.productPrijsLabel;
    }
  
    public JLabel getHuidigAantalLabel()
    {
        return this.huidigAantalLabel;
    }

    public JLabel getHuidigPrijsLabel()
    {
        return this.huidigPrijsLabel;
    }

    public JLabel getTotaalLabel()
    {
        return this.totaalLabel;
    }
    
    public Product getProduct()
    {
        return this.product;
    }
    
    public void setHuidigAantal(int newAantal)
    {
        if (newAantal < 0)
            return;
                    
        this.huidigAantal = newAantal;
        Integer temp = newAantal;
        this.huidigAantalLabel.setText(temp.toString());
    }
    public int getHuidigAantal()
    {
        return this.huidigAantal;
    }
        
    public void setHuidigPrijs(double newPrijs)
    {
        if (newPrijs < 0.0)
            return;
                    
        this.huidigPrijsLabel.setText(getFormattedMoneyString(newPrijs));
    }
    
    public double getHuidigPrijs()
    {
        return this.huidigPrijs;
    }
    
    public double getProductPrijs()
    {
        return this.product.getPrijs();
    }
    
    public double getTotaalPrijs()
    {
        return this.totaalPrijs;
    }
    
    public void setTotaalPrijs(double newTotaal)
    {
        this.totaalPrijs = newTotaal;
        updateTotaalLabel();
    }
    
    public int getTotaalAantal()
    {
        return this.totaalAantal;
    }
    
    public void setTotaalAantal(int newTotaal)
    {
        this.totaalAantal = newTotaal;
        updateTotaalLabel();
    }
    
    public void resetHuidig()
    {
        setHuidigAantal(0);
        setHuidigPrijs(0.0);
    }
    
    public void addToTotaal()
    {
        setTotaalAantal(getTotaalAantal() + getHuidigAantal());
        setTotaalPrijs(getTotaalAantal() * getProductPrijs());
    }
    
    public String getFormattedTotaal()
    {
        return String.format("%-12s %-15s %s", this.product.getNaam(), 
                getTotaalAantal(), getFormattedMoneyString(getTotaalPrijs()));
    }
    
    public void setTotaalLabel(String text)
    {
        this.totaalLabel.setText(text);
    }
    
    public void updateTotaalLabel()
    {
        setTotaalLabel(getFormattedTotaal());
    }
    
    public void setProductNaamLabel(String text)
    {
        this.productNaamLabel.setText(text);
    }
    
    public String getFormattedMoneyString(double money)
    {
        return (new DutchCurrencyFormat()).getFormattedMoneyString(money);
    }
}
