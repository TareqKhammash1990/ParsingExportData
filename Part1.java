package ParsingExportData;


/**
 * Write a description of Part1 here.
 * Parsing Export Data
 * @author Tareq Khammash 
 * @version (a version number or a date)
 */

import edu.duke.*;
import org.apache.commons.csv.*;

public class Part1 {
    
    public void tester()
    {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        //String info = countryInfo(parser, "Nauru");
        //System.out.println(info);
        //parser = fr.getCSVParser();
        //cotton and flowers
        //listExportersTwoProducts (parser, "cotton", "flowers");
        //parser = fr.getCSVParser();
        //int num = numberOfExporters(parser, "cocoa");
        //System.out.println(num);
        //parser = fr.getCSVParser();
        bigExporters(parser, "$999,999,999,999");
    }
    
    public String countryInfo(CSVParser parser, String country)
    {
        String information = "NOT FOUND";
        for (CSVRecord record: parser)
        {
               String countryName = record.get("Country");
               if(countryName.equals(country))
               {
                   information = countryName +":" + " "; 
                   information = information + record.get("Exports") + ":"+ " ";
                   information = information + record.get("Value (dollars)");
               }
        }
        
        return information;
    }
    
    public void listExportersTwoProducts (CSVParser parser, String exportItem1, String exportItem2)
    {
        for (CSVRecord record: parser)
        {
               String export = record.get("Exports");
               if(export.contains(exportItem1) && export.contains(exportItem2))
               {
                   System.out.println(record.get("Country"));
               }
        }
    }
    public int numberOfExporters(CSVParser parser, String exportItem)
    {
        int numberOfCountries = 0;
        for (CSVRecord record: parser)
        {
               String export = record.get("Exports");
               if(export.contains(exportItem))
               {
                   numberOfCountries++;
               }
        }
        
        
        return numberOfCountries;
    }
    public void bigExporters(CSVParser parser, String amount)
    {
        for (CSVRecord record: parser)
        {
               String value = record.get("Value (dollars)");
               //int valueintg = Integer.parseInt(value);
               //int amountintg = Integer.parseInt(amount); 
               if(  value.length() > amount.length() )
               {
                  System.out.print(record.get("Country")+ " ");
                  System.out.println(value);
               }
        }
        
    }

}
