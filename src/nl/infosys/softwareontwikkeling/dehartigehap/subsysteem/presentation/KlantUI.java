/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.presentation;

import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Bestelling;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.businesslogic.BestellingProductRow;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.businesslogic.BestellingUILogic;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Product;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.datastorage.ProductDAO;
import java.util.*;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author J. Bouman
 */
public class KlantUI extends javax.swing.JFrame {
     BestellingUILogic uilogic;
    /**
     * Creates new form KlantUI
     */
    public KlantUI() {
        
        // In designer aangemaakt UI code wordt heer opgeroepen
        initComponents();

        // Maak onze UI Logic controller aan
        uilogic = new BestellingUILogic();
        
        setupDynamicUI();
        
        // setupStaticUI();
    }

    private void setupDynamicUI() {
        
        // Zorg dat de statische UI niet zichtbaar is
        setStaticUIRowsInvisible();
        
        // Dynamisch producten uit database laden
        ArrayList<Product> pl = (new ProductDAO()).loadProducts();
        
        // grijp de posities en size (bij elkaar bounds) 
        // van alle elementen in de eerste rij van de statische UI (designer)
        
        Rectangle buttonMinBounds = rij1_button_min.getBounds();
        Rectangle labelHuidigProductNaamBounds = 
                rij1_label_huidig_productnaam.getBounds();
        Rectangle labelHuidigProductPrijsBounds = 
                rij1_label_huidig_productprijs.getBounds();
        Rectangle labelHuidigAantalBounds = 
                rij1_label_huidig_aantal.getBounds();
        Rectangle labelHuidigPrijsBounds = rij1_label_huidig_prijs.getBounds();
        Rectangle buttonPlusBounds = rij1_button_plus.getBounds();
        Rectangle labelTotaalBounds = rij1_label_totaal.getBounds();
        
        // Maak nieuwe rij aan voor elk product dat geladen is uit database
        for (Product p : pl)
        {
            // Nieuwe rij met de verzameling van GUI elementen (knoppen, labels
            // etc) voor de rij
            BestellingProductRow r = new BestellingProductRow(p, new JButton("+"),
                    new JButton("-"),  new JLabel(), new JLabel(),
                    new JLabel(), new JLabel(), new JLabel());
            
            // Zet de positie en grootte van elk element in UI gebasseerd op
            // positie en grootte/breedte van elementen in de eerste rij 
            // van statische GUI (in designer)
            r.getMinusButton().setBounds(buttonMinBounds);
            r.getPlusButton().setBounds(buttonPlusBounds);
            r.getProductNaamLabel().setBounds(labelHuidigProductNaamBounds);
            r.getProductPrijsLabel().setBounds(labelHuidigProductPrijsBounds);
            r.getHuidigAantalLabel().setBounds(labelHuidigAantalBounds);
            r.getHuidigPrijsLabel().setBounds(labelHuidigPrijsBounds);
            r.getTotaalLabel().setBounds(labelTotaalBounds);
            
            // Voeg de elementen aan paneel toe
            jPanel1.add(r.getMinusButton());
            jPanel1.add(r.getPlusButton());
            jPanel1.add(r.getProductNaamLabel());
            jPanel1.add(r.getProductPrijsLabel());
            jPanel1.add(r.getHuidigAantalLabel());
            jPanel1.add(r.getHuidigPrijsLabel());
            jPanel1.add(r.getTotaalLabel());
                                
            // Plus knop action handler
            r.getMinusButton().addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    uilogic.handleMinusButtonClick(evt);
                } });
            
            // Min knop action handler
            r.getPlusButton().addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    uilogic.handlePlusButtonClick(evt);
                } });
            
            // zorg dat de elementen 50 pixels lager zitten in de GUI
            buttonMinBounds.y += 50;
            buttonPlusBounds.y += 50;
            labelHuidigProductNaamBounds.y += 50;
            labelHuidigProductPrijsBounds.y += 50;
            labelHuidigAantalBounds.y += 50;
            labelHuidigPrijsBounds.y += 50;
            labelTotaalBounds.y += 50;
            
            // Voeg de rij die we aangemaakt hebben toe aan lijst van rijen
            uilogic.addRow(r);
        }
        
        // Maak dynamische GUI versie aan van de bestelknop
        // We gebruiken de positie en grootte/breedte van de statische UI versie
        // van de bestelknop, en we verlagen de positie van de knop gebasseerd
        // op de hoogte van de laatste rij die dynamisch aangemaakt
        // hiervoor wordt labelTotaalBounds.y + 50 gebruikt
        
        JButton dynamic_button_bestellen = new JButton("Bestellen");
        dynamic_button_bestellen.setBounds(button_bestellen.getBounds());
        dynamic_button_bestellen.setLocation(dynamic_button_bestellen.getX(), 
                labelTotaalBounds.y + 50);
        dynamic_button_bestellen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uilogic.handleBestelButton(evt);
            } });
        jPanel1.add(dynamic_button_bestellen);
        
        // Zelfde verhaal
        JButton dynamic_button_afrekenen = new JButton("Afrekenen");
        dynamic_button_afrekenen.setBounds(button_afrekenen.getBounds());
        dynamic_button_afrekenen.setLocation(dynamic_button_afrekenen.getX(), 
                dynamic_button_bestellen.getY() + 40);
        dynamic_button_afrekenen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uilogic.handleAfrekenButtonClick(evt);
            } });
        jPanel1.add(dynamic_button_afrekenen);
        
        // Zelfde verhaal
        JLabel dynamic_label_totaalprijs = new JLabel("Totaalprijs: ");
        dynamic_label_totaalprijs.setBounds(label_totaalprijs.getBounds());
        dynamic_label_totaalprijs.setLocation(dynamic_label_totaalprijs.getX(), 
                dynamic_button_bestellen.getY() - 50);
        dynamic_label_totaalprijs.setSize(70,25);
        jPanel1.add(dynamic_label_totaalprijs);
        
        // Zelfde verhaal
        JTextField dynamic_textfield_totaalprijs = new JTextField();
        dynamic_textfield_totaalprijs.setBounds(dynamic_label_totaalprijs.getBounds());
        dynamic_textfield_totaalprijs.setLocation(dynamic_label_totaalprijs.getX() + 65, 
                dynamic_label_totaalprijs.getY());
        jPanel1.add(dynamic_textfield_totaalprijs);
        
        
        // Verander grootte van het vesnter gebasseerd op hoogte van de
        // afrekenenknop
        this.setSize(this.getWidth(), dynamic_button_afrekenen.getY() + 90);
        
        uilogic.setTotaalPrijsVeld(dynamic_textfield_totaalprijs);
    }

    private void setupStaticUI() {
        uilogic.setTotaalPrijsVeld(textfield_totaalprijs);
        
        // Maak producten aan
        // Omdat de bestellingen naar de database opgeslagen worden met
        // product id uit de product tabel zal deze het programma niet goed
        // functioneren
        // GEBRUIK DEZE CODE ALLEEN OM DE STATISCHE UI TE TESTEN
        Product friet = new Product(0, "Friet", 2.50);
        Product spaghetti = new Product(1, "Spaghetti", 3.00);
        Product soep = new Product(2, "Soep", 4.00);
        Product cola = new Product(3, "Cola", 1.25);
        Product fanta = new Product(4, "Fanta", 1.25);
        Product water = new Product(5, "Water", 0.80);
        
        
        BestellingProductRow rij1 = new BestellingProductRow(friet, rij1_button_plus,
                rij1_button_min,  rij1_label_huidig_productnaam, rij1_label_huidig_productprijs, 
                rij1_label_huidig_aantal, rij1_label_huidig_prijs, rij1_label_totaal);
        
        BestellingProductRow rij2 = new BestellingProductRow(spaghetti, rij2_button_plus,
                rij2_button_min,  rij2_label_huidig_productnaam, rij2_label_huidig_productprijs, 
                rij2_label_huidig_aantal, rij2_label_huidig_prijs, rij2_label_totaal);
        
        BestellingProductRow rij3 = new BestellingProductRow(soep, rij3_button_plus,
                rij3_button_min,  rij3_label_huidig_productnaam, rij3_label_huidig_productprijs, 
                rij3_label_huidig_aantal, rij3_label_huidig_prijs, rij3_label_totaal);
        
        BestellingProductRow rij4 = new BestellingProductRow(cola, rij4_button_plus,
                rij4_button_min,  rij4_label_huidig_productnaam, rij4_label_huidig_productprijs, 
                rij4_label_huidig_aantal, rij4_label_huidig_prijs, rij4_label_totaal);
        
        BestellingProductRow rij5 = new BestellingProductRow(fanta, rij5_button_plus,
                rij5_button_min,  rij5_label_huidig_productnaam, rij5_label_huidig_productprijs, 
                rij5_label_huidig_aantal, rij5_label_huidig_prijs, rij5_label_totaal);
        
        BestellingProductRow rij6 = new BestellingProductRow(water, rij6_button_plus,
                rij6_button_min,  rij6_label_huidig_productnaam, rij6_label_huidig_productprijs, 
                rij6_label_huidig_aantal, rij6_label_huidig_prijs, rij6_label_totaal);
        
        uilogic.addRow(rij1);
        uilogic.addRow(rij2);
        uilogic.addRow(rij3);
        uilogic.addRow(rij4);
        uilogic.addRow(rij5);
        uilogic.addRow(rij6);
    }

    private void setStaticUIRowsInvisible() {
        // Zet rijen statisch UI elementen ontzichbaar
        rij1_button_min.setVisible(false);
        rij1_button_plus.setVisible(false);
        rij1_label_huidig_productnaam.setVisible(false);
        rij1_label_huidig_productprijs.setVisible(false);
        rij1_label_huidig_aantal.setVisible(false);
        rij1_label_huidig_prijs.setVisible(false);
        rij1_label_totaal.setVisible(false);
        
        rij2_button_min.setVisible(false);
        rij2_button_plus.setVisible(false);
        rij2_label_huidig_productnaam.setVisible(false);
        rij2_label_huidig_productprijs.setVisible(false);
        rij2_label_huidig_aantal.setVisible(false);
        rij2_label_huidig_prijs.setVisible(false);
        rij2_label_totaal.setVisible(false);
        
        rij3_button_min.setVisible(false);
        rij3_button_plus.setVisible(false);
        rij3_label_huidig_productnaam.setVisible(false);
        rij3_label_huidig_productprijs.setVisible(false);
        rij3_label_huidig_aantal.setVisible(false);
        rij3_label_huidig_prijs.setVisible(false);
        rij3_label_totaal.setVisible(false);
        
        rij4_button_min.setVisible(false);
        rij4_button_plus.setVisible(false);
        rij4_label_huidig_productnaam.setVisible(false);
        rij4_label_huidig_productprijs.setVisible(false);
        rij4_label_huidig_aantal.setVisible(false);
        rij4_label_huidig_prijs.setVisible(false);
        rij4_label_totaal.setVisible(false);
        
        rij5_button_min.setVisible(false);
        rij5_button_plus.setVisible(false);
        rij5_label_huidig_productnaam.setVisible(false);
        rij5_label_huidig_productprijs.setVisible(false);
        rij5_label_huidig_aantal.setVisible(false);
        rij5_label_huidig_prijs.setVisible(false);
        rij5_label_totaal.setVisible(false);
        
        rij6_button_min.setVisible(false);
        rij6_button_plus.setVisible(false);
        rij6_label_huidig_productnaam.setVisible(false);
        rij6_label_huidig_productprijs.setVisible(false);
        rij6_label_huidig_aantal.setVisible(false);
        rij6_label_huidig_prijs.setVisible(false);
        rij6_label_totaal.setVisible(false);
        
        button_bestellen.setVisible(false);
        button_afrekenen.setVisible(false);
        label_totaalprijs.setVisible(false);
        textfield_totaalprijs.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        setVisible(false);
        rij1_label_totaal = new javax.swing.JLabel();
        rij2_label_totaal = new javax.swing.JLabel();
        rij3_label_totaal = new javax.swing.JLabel();
        rij4_label_totaal = new javax.swing.JLabel();
        label_totaalprijs = new javax.swing.JLabel();
        rij1_label_huidig_productnaam = new javax.swing.JLabel();
        rij2_label_huidig_productnaam = new javax.swing.JLabel();
        rij3_label_huidig_productnaam = new javax.swing.JLabel();
        rij4_label_huidig_productnaam = new javax.swing.JLabel();
        rij5_label_huidig_productnaam = new javax.swing.JLabel();
        rij6_label_huidig_productnaam = new javax.swing.JLabel();
        rij1_label_huidig_productprijs = new javax.swing.JLabel();
        rij2_label_huidig_productprijs = new javax.swing.JLabel();
        rij3_label_huidig_productprijs = new javax.swing.JLabel();
        rij4_label_huidig_productprijs = new javax.swing.JLabel();
        rij5_label_huidig_productprijs = new javax.swing.JLabel();
        rij6_label_huidig_productprijs = new javax.swing.JLabel();
        button_bestellen = new javax.swing.JButton();
        button_afrekenen = new javax.swing.JButton();
        rij1_button_plus = new javax.swing.JButton();
        rij2_button_plus = new javax.swing.JButton();
        rij3_button_plus = new javax.swing.JButton();
        rij4_button_plus = new javax.swing.JButton();
        rij5_button_plus = new javax.swing.JButton();
        rij6_button_plus = new javax.swing.JButton();
        rij1_button_min = new javax.swing.JButton();
        rij2_button_min = new javax.swing.JButton();
        rij3_button_min = new javax.swing.JButton();
        rij4_button_min = new javax.swing.JButton();
        rij5_button_min = new javax.swing.JButton();
        rij6_button_min = new javax.swing.JButton();
        textfield_totaalprijs = new javax.swing.JTextField();
        rij1_label_huidig_aantal = new javax.swing.JLabel();
        rij1_label_huidig_prijs = new javax.swing.JLabel();
        rij2_label_huidig_aantal = new javax.swing.JLabel();
        rij2_label_huidig_prijs = new javax.swing.JLabel();
        rij3_label_huidig_aantal = new javax.swing.JLabel();
        rij3_label_huidig_prijs = new javax.swing.JLabel();
        rij4_label_huidig_aantal = new javax.swing.JLabel();
        rij4_label_huidig_prijs = new javax.swing.JLabel();
        rij5_label_huidig_prijs = new javax.swing.JLabel();
        rij5_label_huidig_aantal = new javax.swing.JLabel();
        rij6_label_huidig_prijs = new javax.swing.JLabel();
        rij6_label_huidig_aantal = new javax.swing.JLabel();
        rij5_label_totaal = new javax.swing.JLabel();
        rij6_label_totaal = new javax.swing.JLabel();
        label_huidige_bestelling = new javax.swing.JLabel();
        label_totale_bestellingen = new javax.swing.JLabel();
        label_product_naam = new javax.swing.JLabel();
        label_huidige_prijs = new javax.swing.JLabel();
        label_aantal = new javax.swing.JLabel();
        label_prijs_huidig = new javax.swing.JLabel();
        label_totaal_tekst = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        rij1_label_totaal.setText("jLabel1");

        rij2_label_totaal.setText("jLabel2");

        rij3_label_totaal.setText("jLabel3");

        rij4_label_totaal.setText("jLabel4");

        label_totaalprijs.setText("Totaalprijs:");

        rij1_label_huidig_productnaam.setText("bla");

        rij2_label_huidig_productnaam.setText("Spaghetti");

        rij3_label_huidig_productnaam.setText("Soep");

        rij4_label_huidig_productnaam.setText("Cola");

        rij5_label_huidig_productnaam.setText("Fanta");

        rij6_label_huidig_productnaam.setText("Water      ");

        rij1_label_huidig_productprijs.setText("€ 2,50");

        rij2_label_huidig_productprijs.setText("€ 3,00");

        rij3_label_huidig_productprijs.setText("€ 4,00");

        rij4_label_huidig_productprijs.setText("€ 1,25");

        rij5_label_huidig_productprijs.setText("€ 1,25");

        rij6_label_huidig_productprijs.setText("€ 0,80");

        button_bestellen.setText("Bestellen");
        button_bestellen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_bestellenActionPerformed(evt);
            }
        });

        button_afrekenen.setText("Afrekenen");
        button_afrekenen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_afrekenenActionPerformed(evt);
            }
        });

        rij1_button_plus.setText("+");
        rij1_button_plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij1_button_plusActionPerformed(evt);
            }
        });

        rij2_button_plus.setText("+");
        rij2_button_plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij2_button_plusActionPerformed(evt);
            }
        });

        rij3_button_plus.setText("+");
        rij3_button_plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij3_button_plusActionPerformed(evt);
            }
        });

        rij4_button_plus.setText("+");
        rij4_button_plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij4_button_plusActionPerformed(evt);
            }
        });

        rij5_button_plus.setText("+");
        rij5_button_plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij5_button_plusActionPerformed(evt);
            }
        });

        rij6_button_plus.setText("+");
        rij6_button_plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij6_button_plusActionPerformed(evt);
            }
        });

        rij1_button_min.setText("-");
        rij1_button_min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij1_button_minActionPerformed(evt);
            }
        });

        rij2_button_min.setText("-");
        rij2_button_min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij2_button_minActionPerformed(evt);
            }
        });

        rij3_button_min.setText("-");
        rij3_button_min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij3_button_minActionPerformed(evt);
            }
        });

        rij4_button_min.setText("-");
        rij4_button_min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij4_button_minActionPerformed(evt);
            }
        });

        rij5_button_min.setText("-");
        rij5_button_min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij5_button_minActionPerformed(evt);
            }
        });

        rij6_button_min.setText("-");
        rij6_button_min.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rij6_button_minActionPerformed(evt);
            }
        });

        textfield_totaalprijs.setText("jTextField1");

        rij1_label_huidig_aantal.setText("aantal");
        rij1_label_huidig_aantal.setToolTipText("");

        rij1_label_huidig_prijs.setText("prijs");
        rij1_label_huidig_prijs.setToolTipText("");

        rij2_label_huidig_aantal.setText("jLabel5");

        rij2_label_huidig_prijs.setText("jLabel6");

        rij3_label_huidig_aantal.setText("jLabel5");

        rij3_label_huidig_prijs.setText("jLabel6");

        rij4_label_huidig_aantal.setText("jLabel5");

        rij4_label_huidig_prijs.setText("jLabel6");

        rij5_label_huidig_prijs.setText("jLabel6");

        rij5_label_huidig_aantal.setText("jLabel5");

        rij6_label_huidig_prijs.setText("jLabel6");

        rij6_label_huidig_aantal.setText("jLabel5");

        rij5_label_totaal.setText("jLabel1");

        rij6_label_totaal.setText("jLabel1");

        label_huidige_bestelling.setText("HUIDIGE BESTELLING:");

        label_totale_bestellingen.setText("TOTALE BESTELLINGEN:");

        label_product_naam.setText("Product:");

        label_huidige_prijs.setText("Prijs:");

        label_aantal.setText("Aantal:");

        label_prijs_huidig.setText("Huidige prijs:");

        label_totaal_tekst.setText("Product:   Aantal:     Prijs:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(label_huidige_bestelling)
                        .addGap(97, 97, 97))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(rij2_button_min)
                                    .addComponent(rij3_button_min)
                                    .addComponent(rij4_button_min)
                                    .addComponent(rij5_button_min)
                                    .addComponent(rij6_button_min)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(rij1_button_min)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(label_product_naam)
                                .addGap(30, 30, 30)
                                .addComponent(label_huidige_prijs)
                                .addGap(18, 18, 18)
                                .addComponent(label_aantal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_prijs_huidig))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(rij6_label_huidig_productnaam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rij5_label_huidig_productnaam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rij3_label_huidig_productnaam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rij1_label_huidig_productnaam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rij2_label_huidig_productnaam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(rij4_label_huidig_productnaam, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(rij2_label_huidig_productprijs)
                                                .addComponent(rij1_label_huidig_productprijs, javax.swing.GroupLayout.Alignment.TRAILING))
                                            .addComponent(rij3_label_huidig_productprijs, javax.swing.GroupLayout.Alignment.TRAILING))
                                        .addComponent(rij4_label_huidig_productprijs, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(rij5_label_huidig_productprijs, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(rij6_label_huidig_productprijs, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rij3_label_huidig_aantal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rij3_label_huidig_prijs))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rij6_label_huidig_aantal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rij6_label_huidig_prijs))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rij5_label_huidig_aantal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rij5_label_huidig_prijs))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(rij4_label_huidig_aantal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(rij4_label_huidig_prijs))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rij2_label_huidig_aantal)
                                            .addComponent(rij1_label_huidig_aantal, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(rij2_label_huidig_prijs)
                                            .addComponent(rij1_label_huidig_prijs, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rij2_button_plus)
                                            .addComponent(rij3_button_plus)
                                            .addComponent(rij4_button_plus)
                                            .addComponent(rij5_button_plus)
                                            .addComponent(rij6_button_plus)
                                            .addComponent(rij1_button_plus, javax.swing.GroupLayout.Alignment.TRAILING))))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(button_bestellen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(button_afrekenen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rij4_label_totaal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rij3_label_totaal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rij2_label_totaal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rij1_label_totaal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(label_totaalprijs)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textfield_totaalprijs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(rij5_label_totaal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rij6_label_totaal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(17, 17, 17))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(label_totale_bestellingen)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(label_totaal_tekst)
                        .addGap(26, 26, 26))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_huidige_bestelling)
                    .addComponent(label_totale_bestellingen))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_prijs_huidig, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_huidige_prijs, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label_product_naam)
                    .addComponent(label_aantal)
                    .addComponent(label_totaal_tekst))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(rij2_button_min)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rij3_button_min)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rij4_button_min)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rij5_button_min)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rij6_button_min))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rij1_label_totaal)
                            .addComponent(rij1_label_huidig_productnaam)
                            .addComponent(rij1_label_huidig_productprijs)
                            .addComponent(rij1_label_huidig_aantal)
                            .addComponent(rij1_label_huidig_prijs)
                            .addComponent(rij1_button_min)
                            .addComponent(rij1_button_plus))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rij2_label_huidig_aantal)
                                        .addComponent(rij2_label_huidig_prijs))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rij2_label_totaal)
                                        .addComponent(rij2_label_huidig_productnaam)
                                        .addComponent(rij2_label_huidig_productprijs)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rij3_label_huidig_aantal)
                                        .addComponent(rij3_label_huidig_prijs))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rij3_label_totaal)
                                        .addComponent(rij3_label_huidig_productnaam)
                                        .addComponent(rij3_label_huidig_productprijs)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rij4_label_huidig_aantal)
                                        .addComponent(rij4_label_huidig_prijs))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rij4_label_totaal)
                                        .addComponent(rij4_label_huidig_productnaam)
                                        .addComponent(rij4_label_huidig_productprijs)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rij5_label_huidig_aantal)
                                        .addComponent(rij5_label_huidig_prijs))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rij5_label_huidig_productnaam)
                                        .addComponent(rij5_label_huidig_productprijs)
                                        .addComponent(rij5_label_totaal)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rij6_label_huidig_aantal)
                                        .addComponent(rij6_label_huidig_prijs))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(rij6_label_huidig_productnaam)
                                        .addComponent(rij6_label_huidig_productprijs)
                                        .addComponent(rij6_label_totaal))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rij2_button_plus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rij3_button_plus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rij4_button_plus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rij5_button_plus)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rij6_button_plus)))))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_totaalprijs)
                    .addComponent(textfield_totaalprijs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(button_bestellen, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(button_afrekenen, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rij1_button_plusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij1_button_plusActionPerformed
        uilogic.handlePlusButtonClick(evt);
    }//GEN-LAST:event_rij1_button_plusActionPerformed

    private void button_afrekenenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_afrekenenActionPerformed
        uilogic.handleAfrekenButtonClick(evt);
    }//GEN-LAST:event_button_afrekenenActionPerformed

    private void rij1_button_minActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij1_button_minActionPerformed
        uilogic.handleMinusButtonClick(evt);
    }//GEN-LAST:event_rij1_button_minActionPerformed

    private void rij2_button_minActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij2_button_minActionPerformed
        uilogic.handleMinusButtonClick(evt);
    }//GEN-LAST:event_rij2_button_minActionPerformed

    private void rij2_button_plusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij2_button_plusActionPerformed
       uilogic.handlePlusButtonClick(evt);
    }//GEN-LAST:event_rij2_button_plusActionPerformed

    private void rij3_button_minActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij3_button_minActionPerformed
        uilogic.handleMinusButtonClick(evt);
    }//GEN-LAST:event_rij3_button_minActionPerformed

    private void rij3_button_plusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij3_button_plusActionPerformed
       uilogic.handlePlusButtonClick(evt);
    }//GEN-LAST:event_rij3_button_plusActionPerformed

    private void rij4_button_minActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij4_button_minActionPerformed
        uilogic.handleMinusButtonClick(evt);
    }//GEN-LAST:event_rij4_button_minActionPerformed

    private void rij4_button_plusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij4_button_plusActionPerformed
       uilogic.handlePlusButtonClick(evt);
    }//GEN-LAST:event_rij4_button_plusActionPerformed

    private void rij5_button_minActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij5_button_minActionPerformed
        uilogic.handleMinusButtonClick(evt);
    }//GEN-LAST:event_rij5_button_minActionPerformed

    private void rij5_button_plusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij5_button_plusActionPerformed
       uilogic.handlePlusButtonClick(evt);
    }//GEN-LAST:event_rij5_button_plusActionPerformed

    private void rij6_button_minActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij6_button_minActionPerformed
        uilogic.handleMinusButtonClick(evt);
    }//GEN-LAST:event_rij6_button_minActionPerformed

    private void rij6_button_plusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rij6_button_plusActionPerformed
        uilogic.handlePlusButtonClick(evt);
    }//GEN-LAST:event_rij6_button_plusActionPerformed

    private void button_bestellenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_bestellenActionPerformed
        uilogic.handleBestelButton(evt);
    }//GEN-LAST:event_button_bestellenActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
                Bestelling bestelling = new Bestelling();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KlantUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KlantUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KlantUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KlantUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KlantUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton button_afrekenen;
    private javax.swing.JButton button_bestellen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel label_aantal;
    private javax.swing.JLabel label_huidige_bestelling;
    private javax.swing.JLabel label_huidige_prijs;
    private javax.swing.JLabel label_prijs_huidig;
    private javax.swing.JLabel label_product_naam;
    private javax.swing.JLabel label_totaal_tekst;
    private javax.swing.JLabel label_totaalprijs;
    private javax.swing.JLabel label_totale_bestellingen;
    private javax.swing.JButton rij1_button_min;
    private javax.swing.JButton rij1_button_plus;
    private javax.swing.JLabel rij1_label_huidig_aantal;
    private javax.swing.JLabel rij1_label_huidig_prijs;
    private javax.swing.JLabel rij1_label_huidig_productnaam;
    private javax.swing.JLabel rij1_label_huidig_productprijs;
    private javax.swing.JLabel rij1_label_totaal;
    private javax.swing.JButton rij2_button_min;
    private javax.swing.JButton rij2_button_plus;
    private javax.swing.JLabel rij2_label_huidig_aantal;
    private javax.swing.JLabel rij2_label_huidig_prijs;
    private javax.swing.JLabel rij2_label_huidig_productnaam;
    private javax.swing.JLabel rij2_label_huidig_productprijs;
    private javax.swing.JLabel rij2_label_totaal;
    private javax.swing.JButton rij3_button_min;
    private javax.swing.JButton rij3_button_plus;
    private javax.swing.JLabel rij3_label_huidig_aantal;
    private javax.swing.JLabel rij3_label_huidig_prijs;
    private javax.swing.JLabel rij3_label_huidig_productnaam;
    private javax.swing.JLabel rij3_label_huidig_productprijs;
    private javax.swing.JLabel rij3_label_totaal;
    private javax.swing.JButton rij4_button_min;
    private javax.swing.JButton rij4_button_plus;
    private javax.swing.JLabel rij4_label_huidig_aantal;
    private javax.swing.JLabel rij4_label_huidig_prijs;
    private javax.swing.JLabel rij4_label_huidig_productnaam;
    private javax.swing.JLabel rij4_label_huidig_productprijs;
    private javax.swing.JLabel rij4_label_totaal;
    private javax.swing.JButton rij5_button_min;
    private javax.swing.JButton rij5_button_plus;
    private javax.swing.JLabel rij5_label_huidig_aantal;
    private javax.swing.JLabel rij5_label_huidig_prijs;
    private javax.swing.JLabel rij5_label_huidig_productnaam;
    private javax.swing.JLabel rij5_label_huidig_productprijs;
    private javax.swing.JLabel rij5_label_totaal;
    private javax.swing.JButton rij6_button_min;
    private javax.swing.JButton rij6_button_plus;
    private javax.swing.JLabel rij6_label_huidig_aantal;
    private javax.swing.JLabel rij6_label_huidig_prijs;
    private javax.swing.JLabel rij6_label_huidig_productnaam;
    private javax.swing.JLabel rij6_label_huidig_productprijs;
    private javax.swing.JLabel rij6_label_totaal;
    private javax.swing.JTextField textfield_totaalprijs;
    // End of variables declaration//GEN-END:variables
}
