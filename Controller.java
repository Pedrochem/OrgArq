public class Controller{
    private String inst;
    private String allInst;
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
    

    public Controller(String inst, String allInst, int pc){
        this.inst = inst;
        this.allInst = allInst;
        run(pc);
        
    }

    public void run(int pc){
        System.out.println("---------------------------------");
        System.out.println("Etapa 2: ");
        regA = Integer.parseInt(allInst.substring(6,11),2);
        regB = Integer.parseInt(allInst.substring(11,16),2);
        aluOut = Integer.parseInt(allInst.substring(6),2);

        System.out.println("RegA = "+regA+" | RegB = "+regB+" | ALUOut = "+aluOut);
        

       switch (inst) {
            case "addu":
                break;
               
            
           
       
           default:
               break;
       } 



    }





}
