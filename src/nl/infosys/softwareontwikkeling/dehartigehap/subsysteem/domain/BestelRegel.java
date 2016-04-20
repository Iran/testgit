/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain;

/**
 *
 * @author maikel
 */
public class BestelRegel {
    private Klant klant;
    private Product product;
    private int aantal;
    
    public BestelRegel(Klant _klant, Product _product, int _aantal)
    {
        this.klant = _klant;
        this.product = _product;
        this.aantal = _aantal;
    }
    
    public Klant getKlant()
    {
        return this.klant;
    }
    
    public Product getProduct()
    {
        return this.product;
    }
    
    public String getProductNaam()
    {
        return product.getNaam();
    }
    
    public int getAantal()
    {
        return this.aantal;
    }
    
    public double getProductPrijs()
    {
        return this.product.getPrijs();
    }
    
    public double getTotaalPrijs()
    {
        return getAantal() * getProductPrijs();
    }
}
