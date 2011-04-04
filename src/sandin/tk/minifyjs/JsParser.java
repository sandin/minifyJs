package sandin.tk.minifyjs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsParser {
    
    // "console.log(msg);" 必须写在一行, 以分号结束
    private static final String FIREBUG_CONSOLE = "(/{0,2}console\\.(log|debug|info|warn|error|assert|clear|dir|dirxml)\\([^;]*;)";

    public JsParser(File jsFile, File output) throws IOException {
        Pattern pattern = Pattern.compile(FIREBUG_CONSOLE);
        findString(jsFile, pattern, output);
    }
    
    private void findString(File file, Pattern pattern, File output) throws IOException {
        if (! file.exists() ) {
            throw new IOException(file + " is not exists.");
        }
        if (! file.canRead() ) {
            throw new IOException(file + " is not readable.");
        }
        
        BufferedWriter out = new BufferedWriter(new FileWriter(output));
        BufferedReader in = new BufferedReader(new FileReader(file));
        int count = 0;
        while (in.ready()) {
            String line = in.readLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) { 
                count++;
                continue;
            }
            out.write(line + "\n");
            //String newline = matcher.replaceAll("");
            //System.out.println(newline);
        }
        System.out.printf("Success, save %d byte.\n", file.length() - output.length());
        System.out.printf("%d lines has been removed.\n", count); 
        out.close();
        in.close();
    }
    
    /**
     * copy chmod 
     * only change owner part
     * 755 -> 744
     * 
     * @param origin
     * @param copy
     * @return
     */
    public static File copyChmod(File origin, File copy) {
        if (origin.canRead()) {
            copy.setReadable(true);
        }
        if (origin.canWrite()) {
            copy.setWritable(true);
        }
        if (origin.canExecute()) {
            copy.setExecutable(true);
        }
        return copy;
    }
}
