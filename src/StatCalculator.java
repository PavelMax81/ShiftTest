import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StatCalculator {
    private static boolean stringDataType = false;
    private static boolean longDataType = false;
    private static boolean doubleDataType = false;
    private static double doubleMin = Double.MAX_VALUE;
    private static double doubleMax = Double.MIN_VALUE;
    private static long longMin = Long.MAX_VALUE;
    private static long longMax = Long.MIN_VALUE;
    private static long longSum = 0;
    private static double doubleSum = 0;
    private static  String minString = "";
    private static String maxString = "";

    public static void calcStatData(ArrayList<String> outputFiles, String statType) {
        if(statType.equals(""))
            return;

        for(String outputFile : outputFiles){
            int elem_number = 0;
            try{
                BufferedReader br = new BufferedReader(new FileReader(outputFile));
                System.out.println("\nStatistics for the file "+outputFile);

                while(br.ready()){
                    elem_number++;
                    String str = br.readLine();
                    if(statType == "full"){
                        if(elem_number == 1) {
                            determineType(str);
                        }
                        calcStatistics(str);
                    }
                }
            } catch(IOException e){
                System.out.println("File "+outputFile+" is not found.");
            }
            printInfo(statType, elem_number);
        }
    }

    private static void printInfo(String statType, int elem_number) {
        if(elem_number == 0)
            return;

        System.out.println("The number of elements: "+ elem_number);

        if(statType == "full") {
            if (longDataType) {
                printFullInfo(true, elem_number, longMin, longMax, longSum, null, null);
            } else if (doubleDataType) {
                printFullInfo(true, elem_number, doubleMin, doubleMax, doubleSum, null, null);
            } else {
                printFullInfo(false, elem_number, longMin, longMax, null, minString, maxString);
            }
        }
    }

    private static void calcStatistics(String str) {
        if(longDataType) {
            calcStatForLong(str);
        } else if(doubleDataType) {
            calcStatForDouble(str);
        } else {
            calcStatForString(str);
        }
    }

    private static void calcStatForString(String str) {
        int strSize = str.length();
        if (strSize < longMin) {
            longMin = strSize;
            minString = str;
        }
        if (strSize > longMax) {
            longMax = strSize;
            maxString = str;
        }
    }

    private static void calcStatForDouble(String str) {
        double doubleValue = toDouble(str);
        doubleSum += doubleValue;
        if (doubleValue < doubleMin) doubleMin = doubleValue;
        if (doubleValue > doubleMax) doubleMax = doubleValue;
    }

    private static void calcStatForLong(String str) {
        long longValue = toLong(str);
        longSum += longValue;
        if (longValue < longMin) longMin = longValue;
        if (longValue > longMax) longMax = longValue;
    }

    private static <T extends Number> void printFullInfo(boolean fileContainsNumbers, int elem_number, T min, T max,
                                                         T sum, String minString, String maxString) {
        if (fileContainsNumbers) {
            System.out.println("The minimal element: " + min);
            System.out.println("The maximal element: " + max);
            System.out.println("The sum of elements: " + sum);
            System.out.println("The average: " + sum.doubleValue() / elem_number);
        } else {
            System.out.println("The shortest string: " + minString + "\n    Size = " + min);
            System.out.println("The longest string: " + maxString + "\n    Size = " + max);
        }
    }

    private static void determineType(String str) {
        stringDataType = false;
        longDataType = false;
        doubleDataType = false;

        longDataType = toLong(str) == null ? false : true;
        if(longDataType){
            return;
        }
        doubleDataType = toDouble(str) == null ? false : true;
        if(doubleDataType){
            return;
        }
        stringDataType = true;
    }

    private static Long toLong(String str){
        try{
            return Long.parseLong(str);
        } catch(NumberFormatException e){
            return null;
        }
    }

    private static Double toDouble(String str){
        try{
            return Double.parseDouble(str);
        } catch(NumberFormatException e){
            return null;
        }
    }
}