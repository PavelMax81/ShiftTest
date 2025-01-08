import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FileParser {
    private ArrayList<Long> integers = new ArrayList<>();
    private ArrayList<Double> floats = new ArrayList<>();
    private ArrayList<String> strings = new ArrayList<>();

    String inputFilePath;
    String resFilePath;
    String resFileCommonName;

    public FileParser(String currentPath, String[] stringArgs){
        inputFilePath = stringArgs[0] == null ? "/input_files" : stringArgs[0];
        resFilePath = stringArgs[1];
        resFileCommonName = stringArgs[2];

        ArrayList<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> files = Files.newDirectoryStream(Path.of(currentPath + inputFilePath))) {
            for (Path path : files)
                fileNames.add(path.toString());
        } catch(IOException e){
            System.out.println("Folder "+currentPath+inputFilePath+" does not exist.");
        }

        for(String fileName:fileNames){
            addValuesToLists(fileName);
        }
    }

    public void addValuesToLists(String readFileName){
        try {
            BufferedReader br = new BufferedReader(new FileReader(readFileName));
            String str;

            while(br.ready()){
                str = br.readLine();

                if(str.length() > 0){
                    ArrayList<Object> classAndValue = getClassAndValueAndOf(str);
                    String className = (String)classAndValue.get(0);

                    if(className.equals("Long"))
                        integers.add((Long)classAndValue.get(1));
                    else if(className.equals("Double"))
                        floats.add((Double)classAndValue.get(1));
                    else
                        strings.add(str);
                }
            }
            System.out.println("File "+readFileName+" is successfully read.");
        } catch(IOException e){
            System.out.println("File "+readFileName+" is not found.");
        }
    }

    public ArrayList<Long> getListOfIntegers(){
        return integers;
    }

    public ArrayList<Double> getListOfFloats(){
        return floats;
    }

    public ArrayList<String> getListOfStrings(){
        return strings;
    }

    private ArrayList<Object> getClassAndValueAndOf(String str){
        ArrayList<Object> classAndValue = new ArrayList<>(0);

        Long intValue = toLong(str);
        if(intValue != null) {
            classAndValue.add("Long");
            classAndValue.add(intValue);
            return classAndValue;
        }
        else {
            Double floatValue = toDouble(str);
            if(floatValue != null) {
                classAndValue.add("Double");
                classAndValue.add(floatValue);
                return classAndValue;
            }
        }
        classAndValue.add("String");
        classAndValue.add(str);
        return classAndValue;
    }

    private Long toLong(String str){
        Long value = null;
        try{
            value = Long.parseLong(str);
        } catch(NumberFormatException|NullPointerException e){
        }
        return value;
    }

    private Double toDouble(String str){
        Double value = null;
        try{
            value = Double.parseDouble(str);
        } catch(NumberFormatException|NullPointerException e){
        }
        return value;
    }
}
