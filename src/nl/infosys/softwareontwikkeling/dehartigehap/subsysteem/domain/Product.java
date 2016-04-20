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
public class Product {
    private String naam;
    private double prijs;
    private int productId;
    
    public Product(int productId, String naam, double prijs){
        this.naam = naam;
        this.prijs = prijs;
        this.productId = productId;
    }
    
    public String getNaam() {
        return naam;
    }
    
    public int getProductId()
    {
        return productId;
    }
    
    public double getPrijs(){
        return prijs;
    }
}
