// "Add exception to existing catch clause" "false"
import java.io.*;


public class c1 {
    void f() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("");
            DataInputStream dis = new DataInputStream(fis);
            <caret>throw new IllegalAccessException();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  
        }
    }
}