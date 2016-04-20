/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.datastorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.domain.Product;

/**
 *
 * @author J. Bouman
 */
public class ProductDAO {
    
    public ProductDAO()
    {
    }
    
    public ArrayList<Product> loadProducts()
    {
        ArrayList<Product> products = new ArrayList<>();
        
        // First open a database connnection
        DatabaseConnection connection = new DatabaseConnection();
        if(connection.openConnection())
        {
            // If a connection was successfully setup, execute the SELECT statement.
            ResultSet resultset = connection.executeSQLSelectStatement(
                "SELECT * FROM product;");

            if(resultset != null)
            {
                try
                {
                    while(resultset.next())
                    {
                        // The value for the CopyID in the row is ignored
                        // for this POC: no Copy objects are loaded. Having the
                        // Loan objects without the Copy objects will do fine
                        // to determine whether the owning Member can be removed.
                        String productNaam = resultset.getString("naam");
                        double productPrijs = resultset.getDouble("prijs");
                        int productId = resultset.getInt("productid");

                        Product p = new Product(productId, productNaam, productPrijs);
                        products.add(p);
                   }
                }
                catch(SQLException e)
                {
                    System.out.println(e);
                    products.clear();
                }
            }
            // else an error occurred leave array list empty.

            // We had a database connection opened. Since we're finished,
            // we need to close it.
            connection.closeConnection();
        }
        
        return products;
    }
}
