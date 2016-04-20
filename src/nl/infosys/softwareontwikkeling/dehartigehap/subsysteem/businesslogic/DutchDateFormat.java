/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.infosys.softwareontwikkeling.dehartigehap.subsysteem.businesslogic;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Date;

/**
 *
 * @author maikel
 */
public class DutchDateFormat {
    public void DutchDateFormat() 
    {
    }
    
    public String getFormattedDatumString(Date d)
    {
        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, 
                DateFormat.LONG, new Locale("NL", "nl"));
        String formattedDate = df.format(d);
        return formattedDate;
    }
}
