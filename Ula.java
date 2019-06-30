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
            case "soma":
                return String.valueOf(srcA + srcB);
            case "slt":
                return (srcA < srcB) ? "1" : "0";
            case "and":
                return String.valueOf(srcA & srcB);
            case "sub":
                return String.valueOf(srcA-srcB);
            case "sll":
                return String.valueOf(srcA << srcB);
            case "srl":
                return String.valueOf(srcA >> srcB);
            
            }
        
        return "";
    }

    public String tipoRShamt(int srcA, String op, int shamt){
        switch(op){
            case "sll":
                return String.valueOf(srcA << shamt);
            case "srl":
                return String.valueOf(srcA >> shamt);
        }
        return "";
    }

}