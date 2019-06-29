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
    

    public Controller(String inst, String allInst){
        this.inst = inst;
        this.allInst = allInst;
        run();
    }

    public void run(){
        System.out.println("-----------------------");
        System.out.println("Instrução: "+inst);

        System.out.println("Etapa 1:");



    }





}