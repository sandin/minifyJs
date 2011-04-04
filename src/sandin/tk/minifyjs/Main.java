package sandin.tk.minifyjs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Main {

    private static void printUsage() {
        System.err.println("Usage: java -jar minify.jar [-o outfile] script.js");
    }
    
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String input = null;
        String output = null;
        // TODO: 解析args
        for (int i = 0, l = args.length; i < l; i++) {
            if (args[i].equals("-o")) {
                output = args[i+1];
            }
            if (i == l-1) {
                input = args[i];
            }
        }
        output = (output != null) ? output : input.replace(".js", ".min.js");
        System.out.println(input);
        System.out.println(output);
        if (input == null || output == null || input == output ) {
            printUsage();
            System.exit(-1);
        }

        File inputFile = new File(input);
        File outputFile = JsParser.copyChmod(inputFile, new File(output));
        JsParser parser = new JsParser(inputFile, outputFile);
    }
    
    public static HashMap<String, String> argsParser(String args) {
        HashMap<String, String> options = new HashMap<String, String>();
        return options;
    }
    
    

}
