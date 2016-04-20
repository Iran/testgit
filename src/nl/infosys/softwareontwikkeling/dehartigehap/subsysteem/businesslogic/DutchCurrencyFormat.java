/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.businesslogic;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author maikel
 */
public class DutchCurrencyFormat {
    
    public void DutchMoneyFormat()
    {  
    }
    
    public String getFormattedMoneyString(double money)
    {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(
                new Locale("NL", "nl"));
        DecimalFormat df = (DecimalFormat)formatter;
        df.applyPattern("¤ #,##0.00;¤ -#");
        String moneyString = df.format(money);
        return moneyString;
    }    
}
