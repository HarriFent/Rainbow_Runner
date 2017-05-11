package game;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class FileHandler implements Serializable {

    private FileInputStream in;
    private FileOutputStream fos;
    private ObjectInputStream ois;
    private ObjectOutputStream objs;
    private File file;
    private ArrayList<ArrayList<Integer>> levels;

    public FileHandler() {
        levels = new ArrayList<>();
    }

    private boolean checkFile(String filename) {
        File myFile = new File(filename);
        boolean b = myFile.exists();

        return b;
    }

    public void loadFile() {

        String line;
        String dir;
        try {
            file = File.createTempFile("javaGameTempFile", ".tmp");
            File file2 = new File(file.getParent());
            File[] matchingFiles = file2.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("javaGameTempFile") && name.endsWith(".tmp");
                }
            });
            if (matchingFiles.length == 2) {
                if (matchingFiles[0].getPath().equals(file.getPath())) {
                    file.delete();
                    file = matchingFiles[1];
                } else {
                    file.delete();
                    file = matchingFiles[0];
                }
            } else {
                file = matchingFiles[0];
            }
            dir = file.getPath();
            in = new FileInputStream(dir);
            ois = new ObjectInputStream(in);
            String[] strLevel = (String[]) ois.readObject();
            ArrayList<Integer> singleLevel = new ArrayList<>();
            for (String level1 : strLevel) {
                singleLevel.add(Integer.valueOf(level1));
            }
            levels.add(singleLevel);
            ois.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void saveToFile(Object[] level) {
        try {
            fos = new FileOutputStream(file);
            objs = new ObjectOutputStream(fos);
            String[] strLevel = new String[level.length];
            for (int i = 0; i < level.length; i++) {
                strLevel[i] = String.valueOf(level[i]);
            }

            objs.writeObject(strLevel);
            fos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    Object[] getLevel(int index) {
        return levels.get(index).toArray();
    }
}
