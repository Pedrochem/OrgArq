import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.image.Image;

public class MaquinaEstados {
    Controller controller;

    // Etapa 1: Busca
    private Image imBusca               = new Image (new FileInputStream("images/arqmult_e1.png"), 900, 900, true, false);
    // Etapa 2: Decodifica
    private Image imDecodifica          = new Image (new FileInputStream("images/arqmult_e2.png"), 900, 900, true, false);
    // Etapa 3: Executa instrução de beq
    private Image imExecutaBranch       = new Image (new FileInputStream("images/arqmult_e3_beq.png"), 900, 900, true, false);
    // Etapa 3: Executa instrução de Tipo-R
    private Image imExecutaTipoR        = new Image (new FileInputStream("images/arqmult_e3_tipo_r.png"), 900, 900, true, false);
    // Etapa 3: Executa instrução de Tipo-I
    private Image imExecutaTipoI        = new Image (new FileInputStream("images/arqmult_e3_tipo_i.png"), 900, 900, true, false);
    // Etapa 3: Executa instrução de salto incondicional
    private Image imExecutaJump         = new Image (new FileInputStream("images/arqmult_e3_jump.png"), 900, 900, true, false);
    // Etapa 3: Executa instrução de store
    private Image imExecutaSw           = new Image (new FileInputStream("images/arqmult_e3_lw_sw.png"), 900, 900, true, false);
    // Etapa 3: Executa instrução de load
    private Image imExecutaLw           = new Image (new FileInputStream("images/arqmult_e3_lw_sw.png"), 900, 900, true, false);
    // Etapa 4: Acesso à memória, Tipo-R
    private Image imAcessaMemoriaTipoR  = new Image (new FileInputStream("images/arqmult_e4_tipo_r.png"), 900, 900, true, false);
    // Etapa 4: Acesso à memória, Tipo-I
    private Image imAcessaMemoriaTipoI  = new Image (new FileInputStream("images/arqmult_e4_tipo_i.png"), 900, 900, true, false);
    // Etapa 4: Acesso à memória, load
    private Image imAcessaMemoriaLw     = new Image (new FileInputStream("images/arqmult_e4_lw.png"), 900, 900, true, false);
    // Etapa 4: Acesso à memória, store
    private Image imAcessaMemoriaSw     = new Image (new FileInputStream("images/arqmult_e4_sw.png"), 900, 900, true, false);
    // Etapa 5: Escrevea na memória, load
    private Image imEscreveMemoriaLw    = new Image (new FileInputStream("images/arqmult_e5_lw.png"), 900, 900, true, false);
    // Etapa final.
    private Image imAcabou              = new Image (new FileInputStream("images/acabou.png"), 900, 900, true, false);

    private final String busca = "busca";
    private final String decodifica = "decodifica";
    private final String executa = "executa";
    private final String acessoMemoria = "acessoMemoria";
    private final String escreveMemoria = "escreveMemoria";

    private String estadoAtual;
    private boolean acabouInst;
    private String tipo;

    public MaquinaEstados(String arq)throws FileNotFoundException {
        estadoAtual = busca;
        controller = new Controller(arq);
    }

    /**
     * Pega a imagem de busca
     * @return imagem de busca
     */
    public Image getImBusca(){
        acabouInst = controller.buscaInstrucao();
        System.out.println("Estado atual = " + estadoAtual);
        return imBusca;
    }

    /**
     * Retorna o estado atual
     * @return estado atual
     */
    public String getEstadoAtual(){
        return estadoAtual;
    }

    /**
     * Calcula o próximo estado da máquina de estados
     * @return imagem do próximo estado
     */
    public Image getProxEstado(){
        if (!acabouInst){
            switch (estadoAtual) {
                case busca:
                    acabouInst = controller.decodificaInstrucao();
                    estadoAtual = decodifica;
                    System.out.println("Estado atual = " + estadoAtual);
                    return imDecodifica;
                case decodifica:
                    acabouInst = controller.executaInstrucao();    //Controle executa a decodificação,assim podemos sabemos qual o tipo de instrucao para saber pra onde ir
                    tipo = controller.getTipo();
                    estadoAtual = executa;
                    System.out.println("Estado atual = " + estadoAtual);

                    switch (tipo) {
                        case "branch":
                            return imExecutaBranch;
                        case "tipoR":
                            return imExecutaTipoR;
                        case "jump":
                            return imExecutaJump;
                        case "tipoI":
                            return imExecutaTipoI;
                        case "load":
                            return imExecutaLw;
                        case "store":
                            return imExecutaSw;
                    }
                case executa:
                    acabouInst = controller.acessoMemoria();
                    estadoAtual = acessoMemoria;
                    System.out.println("Estado atual = " + estadoAtual);
                    switch (tipo) {
                        case "tipoR":
                            return imAcessaMemoriaTipoR;
                        case "tipoI":
                            return imAcessaMemoriaTipoI;
                        case "load":
                            return imAcessaMemoriaLw;
                        case "store":
                            return imAcessaMemoriaSw;
                    }
                case acessoMemoria:
                        controller.escreveMemoria();
                        estadoAtual = escreveMemoria;
                        System.out.println("Estado atual = " + estadoAtual);
                        acabouInst = true;
                        return imEscreveMemoriaLw;
            }
        }
        else{
            estadoAtual = busca;
            System.out.println("---------------------------------");
            System.out.println("Instrução finalizada, buscando nova instrução");
            try {
                System.out.println("Estado atual = " + estadoAtual);
                acabouInst = controller.buscaInstrucao();
                return imBusca;
            } catch (Exception e) {
                System.out.println("Terminou o programa");
                estadoAtual = "Final";
                return imAcabou;
            }
        }
        return imAcabou;
    }
}
