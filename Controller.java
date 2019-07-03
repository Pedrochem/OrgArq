import java.io.FileNotFoundException;

public class Controller {

    private String inst;
    private int pc;
    private int pcAntigo;
    private boolean pcEscCond;
    private boolean pcEsc;
    private String louD;
    private boolean lerMem;
    private boolean escMem;
    private String memParaReg;
    private String irEsc;
    private String fontePc;
    private String ulaOp;
    private String ulaFonteB;
    private String ulaFonteA;
    private boolean escReg;
    private String regDest;
    private Ula ula;
    private boolean zero;
    private String ulaSaidaAntiga;
    int regA;
    int regB;
    String ulaSaida;
    Memory mem;
    int posMem;
    String operation;
    Registers regs;
    String tipo;
    int reg1;
    int reg2;
    int regRd;
    int value;
    String dadoEscritoMem;
    int adress;
    int mdr;

    public Controller(String file) throws FileNotFoundException {
        ula = new Ula();
        regs = new Registers();
        mem = new Memory(file);

        pc=0;
        ulaSaida = "";
        adress = 0;
        lerMem = false;
        escMem = false;
        escReg = false;
        pcEscCond = false;
        pcEsc = false;
        zero = false;
    }

    public boolean buscaInstrucao(){
        //ETAPA 1
        if (pc >= mem.getInstructionSize())
            throw new NullPointerException();

        pcEsc = true;
        pcEscCond = false;
        fontePc = "00";

        lerMem = true;
        louD = "0";
        irEsc = "1";
        ulaFonteA = "00";
        ulaFonteB = "01";
        ulaOp = "soma";

        pcAntigo = pc;
        chamaMemoria();
        chamaIr();
        chamaUla();
        chamaPc();

        System.out.println("---------------------------------");
        System.out.println("Etapa 1:");
        System.out.println("IR = Mem[" + pcAntigo + "] | PC = " + pc + "");
        regs.imprimeRegs();
        mem.imprimeMem();
        return false;
    }

    public boolean decodificaInstrucao(){
        //ETAPA 2
        ulaOp = "soma";
        ulaFonteB = "11";
        ulaFonteA = "10"; // Nos slides a ulaFonteA nesse caso eh 00...
        chamaUla(); // ALUOut = Branch Adress (Just in case)

        regs.setReg(8, 23);
        regA = regs.getValue(Integer.parseInt(inst.substring(6, 11), 2));  // regA = rs (Just in case)
        regB = regs.getValue(Integer.parseInt(inst.substring(11, 16), 2)); // regB = rt (Just in case)

        System.out.println("regA numero = " + Integer.parseInt(inst.substring(6, 11), 2));
        System.out.println("regB numero = " + Integer.parseInt(inst.substring(11, 16), 2));

        operation = decode(inst);

        System.out.println("---------------------------------");
        System.out.println("Etapa 2: ");
        System.out.println("RegA = " + regA + " | RegB = " + regB + " | ALUOut = " + ulaSaida + " | Operation = " + operation);
        return false;
    }

    public boolean executaInstrucao(){
        //ETAPA 3
        System.out.println("---------------------------------");
        System.out.println("Etapa 3: ");
        tipo = getTipo();
        switch(tipo){
            case "tipoR":
                ulaFonteA = "01";
                ulaFonteB = "00";
                switch (operation) {
                    case "addu":
                        ulaOp = "soma";
                        chamaUla();
                        break;
                    case "and":
                        ulaOp = "and";
                        chamaUla();
                        break;
                    case "sll":
                        ulaOp = "sll";
                        chamaUla();
                        break;
                    case "srl":
                        ulaOp = "srl";
                        chamaUla();
                        break;
                    case "slt":
                        ulaOp = "slt";
                        chamaUla();
                        break;
                }
                System.out.println("Saida da ula: " + ulaSaida);
                return false;
            case "tipoI":
                ulaFonteA = "01";
                ulaFonteB = "11";
                switch (operation) {
                    case "ori":
                        ulaOp = "or";
                        chamaUla();
                        break;
                    case "addiu":
                        ulaOp = "soma";
                        chamaUla();
                        break;
                    case "lui":
                        ulaFonteA = "10";
                        ulaFonteB = "10";
                        ulaOp = "soma";
                        chamaUla();
                        break;
                }
                System.out.println("Saida da ula: " + ulaSaida);
                return false;
            case "jump":
                pcEsc = true;
                fontePc = "10";
                chamaPc();
                System.out.println("Vai realizar um Jump, PC => Edereço do jump (" + pc + ")");
                return true;
            case "store":
                ulaOp = "soma";
                ulaFonteA = "01";
                ulaFonteB = "11"; // Nos slides, ulaFonteB nesse caso eh 10...
                chamaUla();
                System.out.println("Calculando o endereço da memória, UlaOut = "+ulaSaida);
                return false;
            case "load":
                ulaOp = "soma";
                ulaFonteA = "01";
                ulaFonteB = "11"; // Nos slides, ulaFonteB nesse caso eh 10...
                chamaUla();
                System.out.println("Calculando o endereço da memória, UlaOut = "+ulaSaida);
                return false;
            case "branch":
                ulaFonteA = "01";
                ulaFonteB = "00";
                ulaOp = "sub";
                fontePc = "01";
                pcEscCond = true;
                pcEsc = false;
                chamaPc();
                chamaUla();
                if(ulaSaida.equals("0")){
                    pc = Integer.parseInt(inst.substring(21),2);
                    System.out.println("O conteúdo dos registradores é igual, logo branch será executado");
                    System.out.println("PC = SaidaUla (" + pc + ")");
                }
                else{
                    System.out.println("O conteúdo dos registradores não é igual, logo branch não será executado");
                    System.out.println("PC = PC++ (" + pc + ")");
                }
                return true;
        }
        return false;
    }

    public boolean acessoMemoria(){
        // ETAPA 4
        System.out.println("---------------------------------");
        System.out.println("Etapa 4: ");

        switch(tipo) {
            case "tipoR":
                memParaReg = "0";
                regDest = "1";
                escReg = true;
                chamaRegistradores();
                System.out.println("Adicionando no registrador Reg[" + regRd + "] : " + value);
                return true;
            case "tipoI":
                memParaReg = "0";
                regDest = "0";
                escReg = true;
                chamaRegistradores();
                System.out.println("Adicionando no registrador Reg[" + regRd + "] : " + value);
                return true;
            case "store":
                louD = "1";
                escMem = true;
                lerMem = false;
                chamaMemoria();
                return true;
            case "load":
                louD = "1";
                lerMem = true;
                chamaMemoria();
        }
        return false;
    }

     public boolean escreveMemoria(){
        //ETAPA 5
        System.out.println("---------------------------------");
        System.out.println("Etapa 5: ");
        escReg = true;
        regDest = "0";
        memParaReg = "1";
        chamaRegistradores();
        System.out.println("Adicionado no registrador: R[" + regDest + "]" + ": " + value);
        return true;
    }

    public void chamaPc(){
        if (pcEsc || (pcEscCond && zero)){
            switch (fontePc) {
                case "00":
                    pc = Integer.parseInt(ulaSaida);
                    break;
                case "01":
                    pc = Integer.parseInt(ulaSaidaAntiga);
                    break;
                case "10":
                    pc = Integer.parseInt(inst.substring(6), 2);
                    break;
            }
        }
    }

    public void chamaRegistradores(){
        if (escReg){
            switch (regDest) {
                case "1":
                    regRd = Integer.parseInt(inst.substring(16, 21), 2);
                    System.out.println("regRd = " + regRd);
                    break;
                case "0":
                    regRd = Integer.parseInt(inst.substring(11, 16), 2);
                    break;
            }
            switch (memParaReg){
                case "0":
                    value = Integer.parseInt(ulaSaida);
                    break;
                case "1":
                    value = mdr;
                    break;
            }
            regs.setReg(regRd, value);
        }
    }

    public void chamaIr(){
        if (irEsc.equals("1"))
            inst = mem.fetch(posMem);
    }

    public void chamaMemoria(){
        if (lerMem){
            switch (louD) {
                case "0":
                    adress = pc;
                    posMem = adress;     //posMem = adress (??)
                    break;
                case "1":
                    adress = Integer.parseInt(ulaSaida);
                    mdr = Integer.parseInt(mem.fetch(adress));
                    break;
            }
        }
        if (escMem){

            adress = Integer.parseInt(ulaSaida);
            mem.set(adress, String.valueOf(regB));
            System.out.println("Ae ó, to escrevendo "+String.valueOf(regB)+" na posicao "+adress+" da mem");
        }

    }

    public void chamaUla(){
        int src1 = 0;
        int src2 = 0;
        switch (ulaFonteA) {
            case "01":
                src1 = regA;
                break;
            case "00":
                src1 = pc;
                break;
            case "10":
                src1 = 0;
                break;
        }
        switch (ulaFonteB) {
            case "00":
                src2 = regB;
                break;
            case "01":
                src2 = 1;
                break;
            case "10":
                src2 = Integer.parseInt(inst.substring(16) + "0000000000000000", 2);
                break;
            case "11":
                src2 = Integer.parseInt(inst.substring(16),2);
                break;
            default:
                break;
        }
        ulaSaidaAntiga = ulaSaida;
        ulaSaida = ula.executa(src1, src2, ulaOp);
        if (ulaSaida.equals("0")) zero = true;
        else zero = false;
    }

    public String decode(String instruction) {
        String opcode = instruction.substring(0, 6);
        switch (opcode) {
            case "001111":
                return "lui";
            case "001101":
                return "ori";
            case "001000":
                return "addiu";
            case "000000":
                String funct = instruction.substring(26);
                if (funct.equals("100001"))
                    return "addu";
                else if(funct.equals("101011"))
                    return "slt";
                else if(funct.equals("100100"))
                    return "and";
                else if(funct.equals("000000"))
                    return "sll";
                else if(funct.equals("000010"))
                    return "srl";
            case "000010":
                return "jump";
            case "000100":
                return "beq";
            case "101011":
                return "sw";
            case "100011":
                return "lw";
        }
        return "";
    }

    public String getTipo(){
        switch (operation) {
            case "lw":
                return "load";
            case "sw":
                return "store";
            case "beq":
                return "branch";
            case "jump":
                return "jump";
            case "addiu":
            case "lui":
            case "ori":
                return "tipoI";
            default:
                return "tipoR";
        }
    }

}
