package game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileHandler {

    private InputStreamReader ir = new InputStreamReader(System.in);
    private BufferedReader br;
    private FileOutputStream fos;
    private PrintStream ps;
    private FileReader frs;
    private File file;
    public ArrayList<ArrayList<Short>> levels = new ArrayList<ArrayList<Short>>();

    private boolean checkFile(String filename) {
        File myFile = new File(filename);
        boolean b = myFile.exists();

        return b;
    }

    public void loadFile() {

        String line;
        String dir;
        int counter = 0;
        try {
            file = new File("C:\\Users\\H\\AppData\\Local\\Temp");
            File[] matchingFiles = file.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith("javaGameTempFile") && name.endsWith(".tmp");
                }
            });
            if (matchingFiles.length == 0) {
                file = File.createTempFile("javaGameTempFile", ".tmp");
            } else {
                file = matchingFiles[0];
            }
            dir = file.getPath();
            frs = new FileReader(dir);
            br = new BufferedReader(frs);
            while (br.readLine() != null) {
                counter++;
            }
            counter = 0;
            for (counter = 0; (line = br.readLine()) != null; counter++) {
                ArrayList<Short> singleLevel = new ArrayList<Short>();
                String[] level = line.split("\\s*,\\s*");
                for (int i = 0; i < level.length; i++) {
                    singleLevel.add(Short.valueOf(level[i]));
                }
                levels.add(singleLevel);
            }

            frs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveToFile(short[] level) {
        try {
            fos = new FileOutputStream(file);
            ps = new PrintStream(fos);
            String[] strLevel = new String[level.length];
            for (int i = 0; i < level.length; i++) {
                strLevel[i] = String.valueOf(level[i]);
            }
            ps.print(strLevel);
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
