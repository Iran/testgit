/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.datastorage;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Bestelling;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.BestelRegel;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Klant;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Product;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.datastorage.DatabaseConnection;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.datastorage.KlantDAO;

/**
 *
 * @author J. Bouman
 */
public class BestellingDAO {
    
    // we schrijven bestelregel en bestelling naar Database
    public void saveBestelling(Bestelling b, Klant k)
    {
        DatabaseConnection connection = new DatabaseConnection();
        if(connection.openConnection())
        {
            // Voeg eerst nieuwe record toe aan `bestelling` tabel met waardes
            // bestellingId en klantId, bestellingId wordt automatisch aangemaakt
            // met AUTO_INCREMENT
            
            try
            {
                PreparedStatement prest;
                String query = "INSERT INTO bestelling(klantid) VALUES(" + k.getKlantId() +");";
                prest = connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                prest.executeUpdate();
                ResultSet rs1 = prest.getGeneratedKeys();
                
                if(rs1 != null)
                {        
                    try
                    {
                        if (rs1.next())
                        {
                            int bestellingId = rs1.getInt(1);
                            // Voeg daarna elke bestelregel in met waardes
                            // bestellingId, productId, aantal


                            for(BestelRegel br : b.getBestelList())
                            {
                                Product p = br.getProduct();
                                int productId = p.getProductId();
                                int aantal = br.getAantal();

                                connection.executeSQLInsertStatement(
                                    "INSERT INTO bestelregel(bestellingid,productid,aantal) "
                                            + "VALUES(" + bestellingId +", "+ productId + ", " + aantal + ");");
                            }
                        }

                        connection.closeConnection(); 
                    }
                    catch(SQLException e)
                    {
                            System.out.println(e);
                    }
                }
            }
            catch(SQLException e)
            {
            System.out.println(e);
            }
        }
    }
}
