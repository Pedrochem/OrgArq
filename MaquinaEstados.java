import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class MaquinaEstados {
    Controller controller;

    private Image imBusca = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/busca.png"));
    private Image imDecodifica = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/decodifica.png"));
    private Image imExecutaBranch = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/executaBranch.png"));

    private Image imAcabou = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/acabou.png"));

    private final String busca = "busca";
    private final String decodifica = "decodifica";
    private final String executaTipoR = "executaTipoR";
    private final String executaBranch = "executaBranch";

    private String estadoAtual;
    private boolean acabouInst;
    private String tipo;

    public MaquinaEstados() throws FileNotFoundException {
        estadoAtual = busca;
        controller = new Controller("mipsProgram.mips");
        
    }

    public Image getImBusca(){
        acabouInst = controller.buscaInstrucao();
        return imBusca;
    }

    public Image getProxEstado(){
        if (!acabouInst){
            switch (estadoAtual) {

                case busca:
                    acabouInst = controller.decodificaInstrucao();
                    estadoAtual = decodifica;
                    return imDecodifica;
                    
                case decodifica:
                    acabouInst = controller.executaInstrucao();
                    tipo = controller.getTipo();    

                    switch (tipo) {
                        case "branch":
                            estadoAtual = executaBranch;
                            return imExecutaBranch;
                            
                    
                        default:
                            //TODO
                            return imExecutaBranch;
                    }
    
                default:
                    return imBusca;
            }
        }
        
        else{
            System.out.println("Terminou");
            return imAcabou;
        }
            

        
       
        

    }

}