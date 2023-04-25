import java.io.*; // used for the classes we need
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ConvertFile
{
    public static void convert(String source, String dest) // given filepath or filename
    {
        // get last 3 chars in input to determine which type
        String sourcetype = source.substring(source.length() -3);
        String desttype = dest.substring(dest.length() -3);

        System.out.println("sourcetype: "+ sourcetype + " desttype: "+desttype + "\t"); //debug

        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(source));  //used to read the text from the source file
            BufferedWriter writer = new BufferedWriter(new FileWriter(dest));  // used to write text to the dest file
            String line = "";

            if ((Objects.equals(sourcetype, "csv")) && (Objects.equals(desttype, "txt"))) // convert from csv to txt
            {
                while ((line = reader.readLine()) != null)  // while source has valid lines aka not Null
                {
                    writer.write(line.replace(",", "\t")); // replace commas with tabs
                    writer.newLine();
                }

                reader.close();
                writer.close();
                System.out.println("File converted successfully from csv to txt.");
            }
            else if ((Objects.equals(sourcetype, "txt"))&& (Objects.equals(desttype, "csv")) ) // convert from txt to csv
            {

                while ((line = reader.readLine()) != null) // while is has valid lines aka not Null
                {
                    writer.write(line.replace("\t", ",")); // replace commas with tabs  //Maybe change to whitespace?
                    writer.newLine();
                }

                reader.close(); // ends bufferedReader
                writer.close();  // ends bufferedReader
                System.out.println("File converted successfully from txt to csv.");
            }
        }

        // neither file type found aka bad input
        catch (IOException e)
        {
            System.out.println("Error 1: An error occurred while converting the file.\n Check the file types");
            e.printStackTrace();
        }
    } // end of convert method
// ----------------------------------------------------------------------------------------------
    public static void normalize(String source) // converts the data to the 'normalized' standard
    {
        String sourcetype = source.substring(source.length() - 3);
        String tempFile = source + ".tmp";

        try {
                BufferedReader reader = new BufferedReader(new FileReader(source));  //used to read the text from the source file
                BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
                String line = "";

                if (Objects.equals(sourcetype, "csv")) // convert from csv to txt
                {
                    while ((line = reader.readLine()) != null)  // while is has valid lines aka not Null
                    {
                        // PERFORM CHECKS FOR CSV LINE
                        // Assuming the input line is stored as a String variable called inputLine

                        String[] cells = line.split(",");

                        String normalizedRow = "";

                        for (String cell : cells)
                        {
                            String normalizedCell = normalizeCell(cell);
                            if (normalizedRow.isEmpty())
                            {
                                normalizedRow = normalizedCell;
                            } else
                            {

                                normalizedRow += "," + normalizedCell;

                            }
                        } // end of For
                        // Write the normalized line to the temporary file
                        writer.write(normalizedRow);
                        writer.newLine();

                    } // end of while

                reader.close();
                writer.close();

                }// end of if
                else if (Objects.equals(sourcetype, "txt")) // convert from csv to txt
                {
                    while ((line = reader.readLine()) != null)  // while is has valid lines aka not Null
                    {
                        // PERFORM CHECKS FOR CSV LINE
                        // Assuming the input line is stored as a String variable called inputLine

                        String[] cells = line.split("\t");

                        String normalizedRow = "";

                        for (String cell : cells)
                        {
                            String normalizedCell = normalizeCell(cell);
                            if (normalizedRow.isEmpty())
                            {
                                normalizedRow = normalizedCell;
                            } else
                            {

                                normalizedRow += "\t" + normalizedCell;

                            }

                        } // end of For

                        // Write the normalized line to the temporary file
                        writer.write(normalizedRow);
                        writer.newLine();

                    } // end of while

                    reader.close();
                    writer.close();
                } // end of if
                else
                {
                    System.out.println("Error 2: An error occurred while normalizing the file.\n Check the file types");
                }


                // Overwrite the original file with the normalized content from the temporary file
                File originalFile = new File(source);
                File temp = new File(tempFile);
                Files.move(temp.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } //end of Try

        catch (IOException e)
        {
            System.out.println("Error 3: An error occurred while normalizing the file.\n Check the file types");
            e.printStackTrace();
        }

    } //End of Normalize method

//-------------------------------------------------------------------------------------------------------------
    public static String normalizeCell(String cell)
    {
        if (cell == null || cell.isEmpty())
        {
            return "N/A";
        } else if (isInteger(cell)) {
            int value = Integer.parseInt(cell);
            String sign = (value >= 0) ? "+" : "-";
            String paddedValue = String.format("%010d", Math.abs(value));
            return sign + paddedValue;
        } else if (isDouble(cell) || isFloat(cell))
        {
            double value = Double.parseDouble(cell);
            String formattedValue = (value > 100 || value < 0.01) ? String.format("%.2e", value) : String.format("%.2f", value);
            return formattedValue;
        } else if (cell.length() > 13)
        {
            return cell.substring(0, 10) + "...";
        } else {
            return cell;
        }
    }
//-------------------------------------------------------------------------------------------------------------
    // Methods used to check the primitive data type of our current cell e.g Int, Double, Float
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    public static  boolean isDouble(String str)
    {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
    public static  boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}//end of ConvertFile class

