import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Memory {
    private String[] mem;
    public HashMap<String, Integer> labels;

    public Memory(String fileName) throws FileNotFoundException {
        mem = new String[100];
        labels = new HashMap<String, Integer>();

        Scanner f = new Scanner(new File(fileName));
        String line;
        String[] campos;
        int i = 0;
        while(f.hasNext()){
            line = f.next();
            campos = line.split(":");
            if(campos.length > 1){
                labels.put(campos[0], i);
                mem[i] = campos[1];
            } else
                mem[i] = line;
            i++;
        }
        
        f.close();
    }

    public int getSize(){
        return mem.length;
    }
}