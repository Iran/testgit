/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.main;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.presentation.KlantUI;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Bestelling;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Product;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.datastorage.ProductDAO;
import java.util.*;


/**
 *
 * @author Dylan Swek
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
        KlantUI UI = new KlantUI();
        KlantUI.main(args);
    }
    
}
