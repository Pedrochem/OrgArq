import java.io.FileNotFoundException;

public class Controller {

    private String inst;
    private int pc;
    private int pcAntigo;
    private String pcEscCond;
    private String pcEsc;
    private String louD;
    private String lerMem;
    private String escMem;
    private String memParaReg;
    private String irEsc;
    private String fontePc;
    private String ulaOp;
    private String ulaFonteB;
    private String ulaFonteA;
    private String escReg;
    private String regDest;
    private Ula ula;
    int regA;
    int regB;
    String ulaSaida;
    Memory mem;
    int posMem;
    String operation;
    Registers regs;


    public Controller(String file) throws FileNotFoundException {
        ula = new Ula();
        regs = new Registers();
        mem = new Memory(file);
        pc=0;
        buscaInstrucao();
    }


    public void buscaInstrucao(){
        //ETAPA 1
        pcEsc = "1";
        lerMem = "1";
        louD = "0";
        irEsc = "1";
        ulaFonteA = "0";
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
        decodificaInstrucao();
    }

    public void decodificaInstrucao(){
        //ETAPA 2
        ulaOp = "soma";
        ulaFonteB = "11";
        ulaFonteA = "0";
        chamaUla();     //ALUOut = Branch Adress (Just in case)

        regA = regs.getValue(Integer.parseInt(inst.substring(11,16),2));  //regA = rs (Just in case)
        regB = regs.getValue(Integer.parseInt(inst.substring(16,21),2));  //regB = rt (Just in case)
        operation = decode(inst);
        
        System.out.println("---------------------------------");
        System.out.println("Etapa 2: ");
        System.out.println("RegA = " + regA + " | RegB = " + regB + " | ALUOut = " + ulaSaida + " | Operation = " + operation);
        
        executaInstrucao();
    }

    public void executaInstrucao(){
        //ETAPA 3
        String tipo = getTipo(operation);
        switch(tipo){
            case "tipo_r":
                if (operation.equals("addu"))
                    ulaSaida = ula.executa(regA, regB, "soma");
                else if (operation.equals("and"))
                    ulaSaida = ula.executa(regA, regB, "and");
                break;

            case "tipo_i":
                //if (operation.equals("lui")
                    //todo    
                break;
            case "jump":
                break;
            case "store":
                break;
            case "load":
                break;
            case "branch":
                ulaSaida = ula.executa(regA, regB, "sub");
                if (ulaSaida.equals("0"))
                    pc = Integer.parseInt(inst.substring(21),2);
                
                break;
            default:
                break;
        }
        System.out.println("ULA Saida = "+ulaSaida);

    

    
    public void chamaPc(){
        if (pcEsc.equals("1"))
            pc = Integer.parseInt(ulaSaida);
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
            case "1":
                src1 = regA;
                break;
        
            case "0":
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

        switch (ulaOp) {
            case "00":
                
                break;
        
            default:
                break;
        }
        //System.out.println("Ula, OP1:"+src1+" OP2:"+src2);
        ulaSaida = ula.executa(src1, src2, ulaOp);
        //System.out.println("Result: "+ulaSaida);

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
                return "j";
            case "000100":
                return "beq";
            case "101011":
                return "sw";
            case "100011":
                return "lw";
        }
        return "";
    }

    public String getTipo(String op){
        switch (op) {
            case "lw":
                return "load";
            case "sw":
                return "store";
            case "beq":
                return "branch";
            case "j":
                return "jump";
            case "addiu":
            case "lui":
            case "ori":
                return "tipo_i";
            case "sll":
            case "srl":
                return "tipo_r_shamt";
            default:
                return "tipo_r";
        }
    }
    
}
