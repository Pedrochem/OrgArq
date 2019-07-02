import java.io.FileNotFoundException;

/**************************************************************
 * TRABALHO 2 * ORGANIZACAO E ARQUITETURA DE COMPUTADORES I * ESCOLA
 * POLITECNICA, PUCRS * Q2, 2019 * PEDRO CHEM, RAFAEL ALMEIDA * * This project
 * implements a MIPS simulator. * *
 **************************************************************/

// 1. Open .mips file
// 2. Decode commands (from 'File'.mips)
// 3. Initialize Registers
// 4. Initialize Memory
// 5. Start execution (simulation)
// 6. Show execution on screen.

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Controller controller;
        while (true){
            //Testando ADDU
            String inst = "00000001011010110101100000100001"; //mem.fetch(pc);
            controller =  new Controller("mipsProgram.mips");
            break;
        }   
    }
}   
