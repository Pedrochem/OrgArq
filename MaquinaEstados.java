import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class MaquinaEstados {
    Controller controller;

    private Image imBusca = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/busca.png"));
    private Image imDecodifica = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/decodifica.png"));
    private Image imExecutaBranch = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/executaBranch.png"));
    private Image imExecutaTipoR = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/executaTipoR.png"));
    private Image imExecutaTipoI = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/executaTipoI.png"));
    private Image imExecutaJump = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/executaJump.png"));
    private Image imAcessaMemoriaTipoR = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/acessoMemoriaTipoR.png"));
    private Image imAcessaMemoriaTipoI = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/acessoMemoriaTipoI.png"));

    private Image imAcabou = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/acabou.png"));

    private final String busca = "busca";
    private final String decodifica = "decodifica";
    private final String executa = "executa";
    private final String acessoMemoria = "acessoMemoria";

    private String estadoAtual;
    private boolean acabouInst;
    private String tipo;

    public MaquinaEstados(String arq)throws FileNotFoundException {
        estadoAtual = busca;
        controller = new Controller(arq);
    }
    

    public Image getImBusca(){
        acabouInst = controller.buscaInstrucao();
        System.out.println("Estado atual = "+estadoAtual);
        return imBusca;
    }

    public String getEstadoAtual(){
        return estadoAtual;
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
                    acabouInst = controller.executaInstrucao();    //Controle executa a decodificação,assim podemos sabemos qual o tipo de instrucao para saber pra onde ir
                    tipo = controller.getTipo();    
                    estadoAtual = executa;
                    System.out.println("Estado atual = "+estadoAtual);
                    switch (tipo) {
                        case "branch":
                            return imExecutaBranch;

                        case "tipoR":
                            return imExecutaTipoR;
                        
                        case "jump":                            
                            return imExecutaJump;
                           
                         case "tipoI":     
                             return imExecutaTipoI;
                        // case "lw":
                        //     return imExecutaLw;
                        // case "sw":
                        //     return imExecutaSw;
                    }

                case executa:
                    acabouInst = controller.acessoMemoria();
                    estadoAtual = acessoMemoria;
                    System.out.println("Estado atual = "+estadoAtual);
                    
                    switch (tipo) {
                        case "tipoR":
                            return imAcessaMemoriaTipoR;    
                        case "tipoI":
                            return imAcessaMemoriaTipoI;
                        // case "lw":
                        //     return imAcessaMemoriaLw;
                        // case "sw":
                        //     return imAcessaMemoriaSw;
                    }
            /*  case acessoMemoria:
                    acabouInst = controller.escreveMemoria();
                    estadoAtual = escreveMemoria;
                    System.out.println("Estado atual = "+estadoAtual);
                    return imEscreveMemoria;
            */
                

            }
        }
        
        else{
            estadoAtual = busca;
            System.out.println("---------------------------------");
            System.out.println("Instrução finalizada, buscando nova instrução");

            try {
                System.out.println("Estado atual = "+estadoAtual);
                acabouInst = controller.buscaInstrucao();
                return imBusca;
           
            } catch (Exception e) {
                System.out.println("Terminou o programa");
                estadoAtual = "Acabo saporra";
                return imAcabou;
            }
           
        }
        return imAcabou;   
    }

}