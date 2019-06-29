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

    public static String decode(String instruction) {
        String opcode = instruction.substring(0, 6);

        switch (opcode) {
        case "001111":
            return "lui";

        case "001101":
            return "ori";

        case "000000":
            String funct = instruction.substring(26);
            if (funct.equals("100001"))
                return "addu";

        case " ":
            return " ";

        case "  ":
            return "  ";

        case "   ":
            return "   ";

        }

        return "";

    }

    public static void main(String[] args) throws FileNotFoundException {
        //Testando ADDU 
        Memory mem = new Memory("input.mips");
        int pc = 0;
        String allInst = "00000011000011111100100000100001";
        Controller controller = new Controller(decode(allInst), allInst);
    }
        
}   