import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Memory {
    private String[] mem;
    private int instructionSize;
    private int pc;

    public HashMap<String, Integer> labels;

    public Memory(String fileName) throws FileNotFoundException {
        int size = 600;
	mem = new String[size];
        labels = new HashMap<String, Integer>();

	pc = 400; // 200 slots for stack

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
        instructionSize = i;
        f.close();
	while(instructionSize > pc)
		pc++;
    }
    public int getInstructionSize(){
        return instructionSize;
    }

    public String fetch(int pos){
        return mem[pos];
    }

    public int getPc(){
	return mem[pc];
    }

    public void incPc(){
	pc++;
    }

}
