public class Registers{
    private int [] reg;
    

    public Registers(){
        reg = new int[32];
        reg[0]=0;
    }

    public void setReg(int regNumber, int value){
        if (regNumber==0){
            System.out.println("Error, cannot modify reg 0")
            return;
        }  
        reg[regNumber]=value;
    }

    public int getValue(int regNumber){
        return reg[regNumber];
    }

    public void initialize(){
        for (int i=0;i<32;i++){
            reg[i]=0;            
        }
    }

    
}