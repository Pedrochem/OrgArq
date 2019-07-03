import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Memory {
    private String[] mem;
    private int instructionSize;
    private int startDataAdress;
    private int endDataAdress;
    private boolean temData;
    private int size;
    String dado;

    public Memory(String fileName) throws FileNotFoundException {
        size = 200;
        startDataAdress = 100;
        endDataAdress = 199;
        temData = false;
        instructionSize = 0;
        
        mem = new String[size];
        preencheMem();
        Scanner f = new Scanner(new File(fileName));
        String line;
        String[] campos;
        int i = 0;
        String text = f.nextLine();
        if (!text.equals(".text")){
            System.out.println("O ARQUIVO DEVE COMEÇAR COM .TEXT" + text);
            f.close();
            return;
        }
        // ADICIONANDO INSTRUCOES
        while(f.hasNext()){
            line = f.nextLine();
            if (line.equals(".data")){
                temData = true;
                break;
            }
            mem[i] = line;
            i++;
            if (i==100)
                System.out.println("O programa ta mtu grande, ta ocupando espaco dos dados");
        }
        instructionSize = i;

        // ADICIONANDO .DATA (SE TIVER)
        if (temData){
            while (f.hasNext()) {
                line = f.nextLine();
                campos = line.split(".word");
                dado = campos[1];
                
                for (int j=0;j<dado.length();j++){
                    if (dado.charAt(j)!=(' ')){
                        dado = campos[1].substring(j);
                        break;
                    }
                }
                mem[startDataAdress] = dado;    // DADO ESTÁ EM STRING (MAS É DECIMAL)
                startDataAdress++;
            }
        }
        f.close();
    }
    
    /**
     * Retorna o o tamanho da memória das instruções
     * @return instructionSize
     */
    public int getInstructionSize(){
        return instructionSize;
    }

    /**
     * Imprime a memória
     */
    public void imprimeMem(){
        for (int i=0;i<200;i++){
            if(i<100 && !mem[i].equals(String.valueOf(i)))
                System.out.println("Mem["+i+"]: "+mem[i]);
            else
                System.out.println("Mem["+i+"]: 0x"+String.format("%08x", Integer.parseInt(mem[i]))); //"0x"+String.format("%08x", i);
        }
    }

    /**
     * Preenche a memória
     */
    public void preencheMem(){
        for (int i=0;i<200;i++){
           mem[i] = String.valueOf(i);
        }
    }

    /**
     * Retorna a instrução na posição pos da memória
     * @param pos posição da memória
     * @return instrução na posição pos
     */
    public String fetch(int pos){
        return mem[pos];
    }

    /**
     * Define a instrução da posição address para value
     * @param address Endereço
     * @param value Instrução
     */
    public void set(int address, String value){
        if (address>=100 && address<=199)
            mem[address] = value;
        else
            System.out.println("Deu erro, o maluco passou os dados errados ae");
    }

}
