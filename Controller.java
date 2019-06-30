public class Controller{
    
    private String inst;
    private boolean pcEscCond;
    private boolean pcEsc;
    private boolean louD;
    private boolean lerMem;
    private boolean escMem;
    private boolean memParaReg;
    private boolean irEsc;
    private boolean fontePc;
    private boolean ulaOp;
    private boolean ulaFonteB;
    private boolean ulaFonteA;
    private boolean escReg;
    private boolean regDest;
    private Ula ula;
    int regA;
    int regB;
    int aluOut;
    
    public Controller(String inst, int pc){
        ula = new Ula();
        this.inst = inst;
        run(pc);
    }

    public void run(int pc){
        //ETAPA 2
        System.out.println("---------------------------------");
        System.out.println("Etapa 2: ");
        regA = Integer.parseInt(inst.substring(6,11),2);
        regB = Integer.parseInt(inst.substring(11,16),2);
        aluOut = Integer.parseInt(inst.substring(6),2);
        String operation = decode(inst);
        
        System.out.println("RegA = " + regA + " | RegB = " + regB + " | ALUOut = " + aluOut + " | Operation = " + operation);
        
        //ETAPA 3
        String tipo = getTipo(operation);
        switch(tipo){
            case "tipo_r":
                



                break;
            case "tipo_i":
                break;
            case "jump":
                break;
            case "store":
                break;
            case "load":
                break;
            case "branch":
                break;
            default:
                break;
        }
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
