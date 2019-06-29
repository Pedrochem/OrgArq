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
    int regA;
    int regB;
    int aluOut;
    

    public Controller(String inst, int pc){
        this.inst = inst;
        run(pc);
        
    }



    public void run(int pc){
        System.out.println("---------------------------------");
        System.out.println("Etapa 2: ");
        regA = Integer.parseInt(inst.substring(6,11),2);
        regB = Integer.parseInt(inst.substring(11,16),2);
        aluOut = Integer.parseInt(inst.substring(6),2);
        String operation = decode(inst);
        
        System.out.println("RegA = "+regA+" | RegB = "+regB+" | ALUOut = "+aluOut+" | Operation = "+operation);
        


    }


    public  String decode(String instruction) {
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

    public String getTipo(String op){
        switch (op) {
            case "":
                
                break;
        
            default:
                break;
        }
    }
    
}
