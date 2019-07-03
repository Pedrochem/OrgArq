public class Registers{
    private int [] reg;
    
    public Registers(){
        reg = new int[32];
        initialize();
    }

    /**
     * Define o valor de um registrador
     * @param regNumber Registrador a ser escrito
     * @param value Valor a ser escrito no registrador
     */
    public void setReg(int regNumber, int value){
        if (regNumber==0){
            System.out.println("Error, cannot modify reg 0");
            return;
        }  
        reg[regNumber]=value;
    }

    /**
     * Retorna o conteúdo de um registrador
     * @param regNumber Número do registrador destino
     * @return Conteúdo do registrador
     */
    public int getValue(int regNumber){
        return reg[regNumber];
    }

    /**
     * Inicializa os registradores com 0
     */
    public void initialize(){
        for (int i = 0; i < 32; i++){
            reg[i] = 0;            
        }
    }

    /**
     * Imprime os registradores
     */
    public void imprimeRegs(){
        for (int i = 0; i < 32; i++){
            System.out.println("Reg[" + i + "]: " + reg[i]);
        }
    }

    
}
