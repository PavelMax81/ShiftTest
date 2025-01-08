import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StatCalculator {
    public static void calcStatData(ArrayList<String> outputFiles, String statType) {
        if(statType.equals(""))
            return;

        for(String outputFile : outputFiles){
            boolean fileContainsNumbers = false;
            int elem_number = 0;
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            String minString = "";
            String maxString = "";
            double sum = 0;
            double average = 0;
            Double value;

            try{
                BufferedReader br = new BufferedReader(new FileReader(outputFile));
                System.out.println("\nStatistics for the file "+outputFile);

                while(br.ready()){
                    elem_number++;
                    String str = br.readLine();
                    value = toNumber(str);

                    if(value == null) {
                        fileContainsNumbers = false;
                        value = (double)str.length();
                    } else {
                        fileContainsNumbers = true;
                    }

                    if(statType.equals("full")){
                        if(fileContainsNumbers) {
                            sum += value;
                        }
                        if(value < min) {
                            min = value;
                            if(!fileContainsNumbers)
                                minString = str;
                        }
                        if(value > max) {
                            max = value;
                            if(!fileContainsNumbers)
                                maxString = str;
                        }
                    }
                }
            } catch(IOException e){
                System.out.println("File "+outputFile+" is not found.");
            }
            if(elem_number > 0)
                printInfo(statType, fileContainsNumbers, elem_number, min, max, average, sum, minString, maxString);
        }
    }

    private static void printInfo(String statType, boolean fileContainsNumbers, int elem_number, double min, double max,
                                  double average, double sum, String minString, String maxString) {
        System.out.println("The number of elements: "+ elem_number);

        if(statType.equals("full")) {
            if(fileContainsNumbers){
                System.out.println("The minimal element: "+ min);
                System.out.println("The maximal element: "+ max);

                if(elem_number != 0)
                    average = sum / elem_number;

                System.out.println("The sum of elements: "+ sum);
                System.out.println("The average: "+ average);
            } else {
                System.out.println("The shortest string: "+ minString + "\n    Size = "+min);
                System.out.println("The longest string: "+ maxString + "\n    Size = "+max);
            }
        }
    }

    private static Double toNumber(String str){
        try{
            return Double.parseDouble(str);
        } catch(NumberFormatException e){
            return null;
        }
    }
}
