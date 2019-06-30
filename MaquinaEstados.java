import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class MaquinaEstados {
    Controller controller;

    private Image imBusca = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/busca.png"));
    private Image imDecodifica = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/decodifica.png"));
    private Image imExecutaBranch = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/executaBranch.png"));
    private Image imExecutaTipoR = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/executaTipoR.png"));

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
                    System.out.println("Estado atual = "+estadoAtual);
                    return imDecodifica;
                    
                case decodifica:
                    acabouInst = controller.executaInstrucao();
                    tipo = controller.getTipo();    

                    switch (tipo) {
                        case "branch":
                            estadoAtual = executaBranch;
                            System.out.println("Estado atual = "+estadoAtual);
                            return imExecutaBranch;

                        case "tipoR":
                            estadoAtual = executaTipoR;
                            System.out.println("Estado atual = "+estadoAtual);
                            return imExecutaTipoR;
                    }
            }
        }
        
        else{
            estadoAtual = busca;
            System.out.println("Instrução finalizada, buscando nova instrução");

            try {
                System.out.println("Estado atual = "+estadoAtual);
                acabouInst = controller.buscaInstrucao();
                return imBusca;
           
            } catch (Exception e) {
                System.out.println("Terminou o programa");
                return imAcabou;
            }
           
        }
        return imAcabou;   
    }

}