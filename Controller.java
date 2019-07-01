import java.io.FileNotFoundException;

public class Controller {

    private String inst;
    private int pc;
    private int pcAntigo;
    private boolean pcEscCond;
    private boolean pcEsc;
    private String louD;
    private String lerMem;
    private String escMem;
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


    public Controller(String file) throws FileNotFoundException {
        ula = new Ula();
        regs = new Registers();
        mem = new Memory(file);
        pc=0;
        ulaSaida = "";
    }


    public boolean buscaInstrucao(){
        //ETAPA 1
       
        if (pc>=mem.getInstructionSize())
            throw new NullPointerException(); 

        pcEsc = true;
        pcEscCond = false;
        fontePc = "00";

        lerMem = "1";
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
        System.out.println("IR = Mem["+pcAntigo+"] | PC = "+pc + "");
        return false;
    }

    public boolean decodificaInstrucao(){
        //ETAPA 2
        ulaOp = "soma";
        ulaFonteB = "11";
        ulaFonteA = "10";
        chamaUla();     //ALUOut = Branch Adress (Just in case)

        regA = regs.getValue(Integer.parseInt(inst.substring(6,11),2));  //regA = rs (Just in case)
        regB = regs.getValue(Integer.parseInt(inst.substring(11,16),2));  //regB = rt (Just in case)

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
                }

                System.out.println("Saida da ula: "+ulaSaida);
                return false;

            case "tipoI":
                ulaFonteA = "01";
                ulaFonteB = "10";

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
                        ulaOp = "lui";
                        chamaUla();
                        break;
                }
                System.out.println("Saida da ula: "+ulaSaida);
                return false;

            case "jump":
                pcEsc = true;
                fontePc = "10";
                chamaPc();
                System.out.println("Vai realizar um Jump, PC = Edereço do jump ("+pc+")");
                return true;     

            case "store":
                //TODO
                break;
            case "load":
                //TODO
                break;

            case "branch":
                ulaFonteA = "01";
                ulaFonteB = "00";
                ulaOp = "sub";
                
                fontePc = "01";
                pcEscCond = true;
                pcEsc = false;
            
                chamaPc();
                chamaUla();

                if (ulaSaida.equals("0")){ 
                    pc = Integer.parseInt(inst.substring(21),2);
                    System.out.println("O conteúdo dos registradores é igual, logo branch será executado");
                    System.out.println("PC = SaidaUla ("+pc+")");
                }
                else {
                    System.out.println("O conteúdo dos registradores não é igual, logo branch não será executado");
                    System.out.println("PC = PC++ ("+pc+")");
                }
            
                return true;
                
            default:
                //TODO
                return false;
        }
        return false;
    }

    public boolean acessoMemoria(){
        //ETAPA 4
        System.out.println("---------------------------------");
        System.out.println("Etapa 4: ");

        switch (tipo) {
            case "tipoR":
                memParaReg = "0";
                regDest = "1";
                escReg = true;
                chamaRegistradores();
                System.out.println("Adicionando no registrador Reg["+regRd+"] : "+value);
                return true;
                
        
            case "tipoI":
                memParaReg = "0";
                regDest = "0";
                escReg = true;
                chamaRegistradores();
                System.out.println("Adicionando no registrador Reg["+regRd+"] : "+value);
                return true;
                
        }
        
        return false;

    }
    
    public void chamaPc(){
        if(pcEsc!=true && pcEsc!=false)
            pcEsc = false;
        if(pcEscCond!=true && pcEscCond!=false)
            pcEscCond = false;
        if(zero!=true && zero!=false)
            zero = false;
        
        if (pcEsc || (pcEscCond && zero)){
            switch (fontePc) {
                case "00":
                    pc = Integer.parseInt(ulaSaida);
                    break;
                case "01":
                    pc = Integer.parseInt(ulaSaidaAntiga);
                    break;
                case "10":
                    pc = Integer.parseInt(inst.substring(6),2);
                    break;
            }
        }
            
    }

    public void chamaRegistradores(){
        
        if (escReg){
            switch (regDest) {
                case "1":
                    regRd = Integer.parseInt(inst.substring(16,21),2);
                    System.out.println("regRd = "+regRd);
                    break;
            
                case "0":
                    regRd = Integer.parseInt(inst.substring(11, 16),2);
                    break;
            }

            switch (memParaReg){
                case "0":
                    value = Integer.parseInt(ulaSaida);
                    break;
            
                case "1":
                    //TODO
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
        if (lerMem.equals("1")){ 
            switch (louD) {
                case "0":
                    posMem = pc;
                    break;
                case "1":
                    //TODO
                default:
                    break;
            }
        }
        
    }


    public void chamaUla(){
        int src1=0;
        int src2=0;
        
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
                src2 = Integer.parseInt(inst.substring(16),2);
                break;
            case "11":
                src2 = Integer.parseInt(inst.substring(16),2);
                break;
            default:
                break;
        }
        ulaSaidaAntiga = ulaSaida;
        
        ulaSaida = ula.executa(src1, src2, ulaOp);
       
        if (ulaSaida.equals("0"))
            zero = true;
        else
            zero = false;
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
