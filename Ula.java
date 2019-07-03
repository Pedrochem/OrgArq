// This unit implements the MIPS ULA
// lui, ori, addu, addiu, lw, sw, beq, slt, j, and, sll, srl

// Inputs: ULAOp, ULASrcA, ULASrcB 
// ULAOp:
//  0: ULA performs an addition
//  1: ULA performs a subtraction
//  2: ULA performs a comparison (beq)

public class Ula{
    private int srcA;
    private int srcB;
    private String op;

    public Ula(){
        this.srcA = 0;
        this.srcB = 0;
        this.op = "";
    }

    public void setSrcA(int srcA){
        this.srcA = srcA;
    }

    public void setSrcB(int srcB){
        this.srcB = srcB;
    }
    
    public void setULAOp(String op){
        this.op = op;
    }

    public String executa(int srcA, int srcB, String op){
        switch(op){
            case "soma": // 000
                return String.valueOf(srcA + srcB);
            case "slt":  // 001
                return (srcA < srcB) ? "1" : "0";
            case "and":  // 010
                return String.valueOf(srcA & srcB);
            case "sub":  // 011
                return String.valueOf(srcA - srcB);
            case "sll":  // 100
                return String.valueOf(srcA << srcB);
            case "srl":  // 101
                return String.valueOf(srcA >> srcB);
            }
        return "";
    }
}
