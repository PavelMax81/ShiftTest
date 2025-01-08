import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileCreator {
    public static ArrayList<String> createOutputFiles(String currentPath, FileParser fileParser) throws IOException {
        String path;
        String fileStartName;

        if(fileParser.resFilePath == null){
            path = currentPath;
        } else {
            path = currentPath + fileParser.resFilePath;
        }
        createFolder(path);

        if(fileParser.resFileCommonName == null){
            fileStartName = "";
        } else {
            fileStartName = fileParser.resFileCommonName;
        }

        ArrayList<String> outputFiles = new ArrayList<>();
        String integersFileName = Path.of(path + "/" + fileStartName + "integers.txt").toString();
        String doublesFileName  = Path.of(path + "/" + fileStartName + "floats.txt").toString();
        String stringsFileName  = Path.of(path + "/" + fileStartName + "strings.txt").toString();
        outputFiles.add(integersFileName);
        outputFiles.add(doublesFileName);
        outputFiles.add(stringsFileName);

        if(printValues(integersFileName, fileParser.getListOfIntegers())) {
            System.out.println("File " + integersFileName + " is modified.");
        }
        if(printValues(doublesFileName, fileParser.getListOfFloats())) {
            System.out.println("File " + doublesFileName + " is modified.");
        }
        if(printValues(stringsFileName, fileParser.getListOfStrings())) {
            System.out.println("File " + stringsFileName + " is modified.");
        }
        return outputFiles;
    }

    private static <T> boolean printValues(String fileName, ArrayList<T> values) throws IOException {
        if(values.size() == 0 | fileName == null)
            return false;

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, ArgStringParser.appendToExistingFiles));

        for(int i = 0; i < values.size(); i++){
            bw.append(values.get(i)+"\n");
        }
        bw.close();
        return true;
    }

    private static void createFolder(String folderName) {
        Path folderPath = Paths.get(folderName);
        try {
            if(!folderPath.toFile().exists()) {
                Files.createDirectories(folderPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
