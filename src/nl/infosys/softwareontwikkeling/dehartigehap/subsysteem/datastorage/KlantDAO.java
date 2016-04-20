/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Klant;

/**
 *
 * @author J. Bouman
 */
public class KlantDAO 
{
    
    public KlantDAO() {}
    
    public Klant findKlant(String klantNaam)
    {
        Klant klant = null;
        
        // First open a database connnection
        DatabaseConnection connection = new DatabaseConnection();
        if(connection.openConnection())
        {
            // If a connection was successfully setup, execute the SELECT statement.
            ResultSet resultset = connection.executeSQLSelectStatement(
                "SELECT * FROM klant WHERE klantnaam = \"" + klantNaam + "\";");

            if(resultset != null)
            {
                try
                {
                    // The membershipnumber for a member is unique, so in case the
                    // resultset does contain data, we need its first entry.
                    if(resultset.next())
                    {
                        int tafelId = resultset.getInt("tafelid");
                        int tabletId= resultset.getInt("tabletid");
                        int klantId= resultset.getInt("klantid");
                        
                        klant = new Klant(
                            klantId,
                            tafelId,
                            tabletId,
                            klantNaam);
                        
                    }
                }
                catch(SQLException e)
                {
                    System.out.println(e);
                    klant = null;
                }
            }
            // else an error occurred leave 'member' to null.
            
            // We had a database connection opened. Since we're finished,
            // we need to close it.
            connection.closeConnection(); 
        }
        return klant;
    }
}
