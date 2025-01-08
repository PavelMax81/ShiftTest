public class ArgStringParser {
    public static boolean appendToExistingFiles;
    public static String statType = "";
    public static String[] parseArgs(String[] args){
        String[] stringArgs = new String[3];
        String inputFilePath = null;
        String resFilePath = null;
        String resFileCommonName = null;
        appendToExistingFiles = false;
        boolean statTypeChosen = false;

        for(int i = 0; i < args.length; i++){
            if(args[i].equals("-i")){
                if(i+1 < args.length && args[i+1].charAt(0) != '-') {
                    inputFilePath = args[i + 1];
                    if(inputFilePath.charAt(0)!='\\' && inputFilePath.charAt(0)!='/')
                        inputFilePath = '/' + inputFilePath;
                }
            } else if(args[i].equals("-o")){
                if(i+1 < args.length && args[i+1].charAt(0) != '-') {
                    resFilePath = args[i + 1];
                    if(resFilePath.charAt(0)!='\\' && resFilePath.charAt(0)!='/')
                        resFilePath = '/' + resFilePath;
                }
            } else if(args[i].equals("-p")){
                if(i+1 < args.length && args[i+1].charAt(0) != '-')
                    resFileCommonName = args[i+1];
            } else if(args[i].equals("-a")) {
                appendToExistingFiles = true;
            } else if(!statTypeChosen && args[i].equals("-s")) {
                statType = "short";
                statTypeChosen = true;
            } else if(!statTypeChosen && args[i].equals("-f")) {
                statType = "full";
                statTypeChosen = true;
            }
        }
        stringArgs[0] = inputFilePath;
        stringArgs[1] = resFilePath;
        stringArgs[2] = resFileCommonName;
        return stringArgs;
    }
}
