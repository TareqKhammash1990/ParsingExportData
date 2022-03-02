package ParsingExportData;


/**
 * Write a description of Part2 here.
 * Parsing Weather Data
 * @author Tareq Khammash
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;
public class Part2 
{
     public CSVRecord lowestCalculator(CSVRecord currentRow,CSVRecord lowest, String s)
    {
           if(lowest == null)
           {
              lowest = currentRow;
           }
           else
           {
              
              if(s=="Humidity")
              {
                  if(!currentRow.get("Humidity").equals("N/A"))
                  {
                    Double currNum = Double.parseDouble(currentRow.get(s));
                    Double lowestNum = Double.parseDouble(lowest.get(s));
                    if (currNum < lowestNum)
                    {
                        lowest = currentRow;
                    }
                  }
              }
              if(s == "TemperatureF")
              {
                  if(!currentRow.get("TemperatureF").equals( "-9999"))
                  {
                      Double currNum = Double.parseDouble(currentRow.get(s));
                      Double lowestNum = Double.parseDouble(lowest.get(s));
                      if (currNum < lowestNum)
                      {
                          lowest = currentRow;
                      }
                  }
              }
              
           }
           return lowest;
    }
    public CSVRecord coldestHourInFile(CSVParser parser)
    {
        CSVRecord coldestSoFar = null;
        
        for(CSVRecord currentRow: parser)
        {
            coldestSoFar = lowestCalculator(currentRow, coldestSoFar, "TemperatureF");
        }
        return coldestSoFar;
    }
    public void testColdestHourInFile()
    {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldestTempInDay = coldestHourInFile(parser);
        System.out.println(coldestTempInDay.get("TemperatureF") + "  This temperature occured at " + coldestTempInDay.get("TimeEDT"));
        
    }
    public String fileWithColdestTemperature()
    {
        String fileName = null;
        CSVRecord coldestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles())
        {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord current = coldestHourInFile(parser);
            if(coldestSoFar == null)
            {
                coldestSoFar = current;
            }
            else
            {
                double currTemp = Double.parseDouble(current.get("TemperatureF"));
                double coldestTemp = Double.parseDouble(coldestSoFar.get("TemperatureF"));
               if(currTemp!= -9999 && currTemp < coldestTemp)
               {
                    coldestSoFar = current;
                    fileName = f.getName();
               }
            }
        }
        //System.out.println(fileName);
        return fileName;
    }
    public void printTempsColdestDay(CSVParser parser)
    {
       System.out.println("All the Temperatures on the coldest day were: ");
       for(CSVRecord currentRow: parser)
        {
            String time = currentRow.get("DateUTC");
            String temperature = currentRow.get("TemperatureF");
            System.out.print(time + ": ");
            System.out.println(temperature);
        }
    }
    public void testFileWithColdestTemperature()
    {
        String fileName = fileWithColdestTemperature();
        System.out.println("Coldest day was in file " +fileName);
        FileResource fr = new FileResource();
        CSVRecord coldestTempInDay = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest Temperature on that day was " + coldestTempInDay.get("TemperatureF"));
        printTempsColdestDay(fr.getCSVParser());
        
    }
    public double averageTemperatureInFile(CSVParser parser)
    {
        double avgTemp =0;
        avgTemp = avgCalculator(parser,0); 
        return avgTemp;
    }
    public void testAverageTemperatureInFile()
    {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avgTemp = averageTemperatureInFile(parser);
        System.out.print("Average temperature in file is ");
        System.out.println(avgTemp); 
    }
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value)
    {
        double avgTemp =0;
        double sum = 0;
        double num = 0;
        int counter = 0;
        avgTemp = avgCalculator(parser,value);
        return avgTemp;
    }
    public double avgCalculator(CSVParser parser, int value)
    {
        double avgNum = 0;
        double num = 0;
        double sum = 0;
        int counter = 0;
        for (CSVRecord currentRow:parser)
        {
            if((Double.parseDouble(currentRow.get("TemperatureF")) != -9999) && (Double.parseDouble(currentRow.get("Humidity")) >= value))
            {
                num = Double.parseDouble(currentRow.get("TemperatureF"));
                sum = sum + num; 
                counter++;
            }
        }
        if(sum==0)
        {
            avgNum = 0;
        }
        else
        {
            avgNum = sum / counter;
        }
        return avgNum;
    }
    public void testAverageTemperatureWithHighHumidityInFile()
    {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avgTempWithHumid = averageTemperatureWithHighHumidityInFile(parser, 80);
        if (avgTempWithHumid == 0)
        {
            System.out.println("No temperatures with that humidity");
        }
        else
        {
            System.out.print("Average Temp when high Humidity is ");
            System.out.println(avgTempWithHumid);
        }
    }
    public CSVRecord lowestHumidityInFile(CSVParser parser)
    {
        CSVRecord lowestHumidity = null ;
        //CSVRecord lowestHumidityRecord = null;
        for (CSVRecord currentRow: parser)
        {
           lowestHumidity = lowestCalculator(currentRow, lowestHumidity, "Humidity");
        }
        return lowestHumidity;
    }
   
    public void testLowestHumidityInFile()
    {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.print("Lowest Humidity was ");
        System.out.print(csv.get("Humidity") + " ");
        System.out.println(csv.get("DateUTC"));
    }
    public CSVRecord lowestHumidityInManyFiles()
    {
        CSVRecord lowestHumidity = null;
        DirectoryResource dr = new DirectoryResource();
        
        for (File f :dr.selectedFiles())
        {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord currHumid = lowestHumidityInFile(parser);
            lowestHumidity = lowestCalculator(currHumid,lowestHumidity, "Humidity");
        }
        return lowestHumidity;
    }
    public void testLowestHumidityInManyFiles()
    {
        CSVRecord lowestHumidity = lowestHumidityInManyFiles();
        System.out.print("Lowest Humidity was ");
        System.out.print(lowestHumidity.get("Humidity"));
        System.out.print(" at ");
        System.out.println(lowestHumidity.get("DateUTC"));
    }
}
