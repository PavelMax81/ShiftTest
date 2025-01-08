import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        String currentPath = new java.io.File(".").getCanonicalPath();
        String[] stringArgs = ArgStringParser.parseArgs(args);
        FileParser fileParser = new FileParser(currentPath, stringArgs);
        ArrayList<String> outputFiles = FileCreator.createOutputFiles(currentPath, fileParser);
        StatCalculator.calcStatData(outputFiles, ArgStringParser.statType);
    }
}