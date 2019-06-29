// This unit implements the MIPS ALU
// lui, ori, addu, addiu, lw, sw, beq, slt, j, and, sll, srl

// Inputs: ALUOp, ALUSrcA, ALUSrcB 
// ALUOp:
//  0: ALU performs an addition
//  1: ALU performs a subtraction
//  2: ALU performs a comparison (beq)

public class ALU{
    private int SrcA;
    private int SrcB;
    private int Op;

    public void SetSrcA(int SrcA){
        this.SrcA = SrcA;
    }

    public void SetSrcB(int SrcB){
        this.SrcB = SrcB;
    }
    
    public void SetALUOp(int Op){
        this.Op = Op;
    }

    public int Execute(){
        int Result = 0;
        switch(Op){
            case 0:
                Result = SrcA + SrcB;
                break;
            case 1:
                Result = SrcA - SrcB;
            case 2:
                Result = ExecuteFunc();
        }
        return Result;
    }

    private int ExecuteFunc(){
        return 1;
    }

}