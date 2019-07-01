
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Tela extends Application {
    Scene scene1, scene2;
    Stage window;
    int clock = 0;
    Button button;
    ImageView selectedImage;
    MaquinaEstados maqEstados;
    String arquivo;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        window = primaryStage;

        // Button 1
        Label label1 = new Label("Welcome to our mips simulator!");
        Button button1 = new Button("Choose the .mips file");
        button1.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(window);
            arquivo = String.valueOf(file.getAbsolutePath());
            System.out.println("deu ruim: "+arquivo);
            // try {
            //     maqEstados = new MaquinaEstados(arquivo);
            // } catch (FileNotFoundException e1) {
            //     System.out.println("deu erro");
            // }
        });

        //Layout 1 - children laid out in vertical column
        VBox layout1 = new VBox(20);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 300, 300);


        //--------------------------------------------------------
       
        //Creating label
        Label label = new Label("Estado atual: busca");
        //Taking care of image display
        selectedImage = new ImageView();   
        selectedImage.setImage(maqEstados.getImBusca());
      
        //Creating button
        button = new Button("Next");

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(50);
        vbox.getChildren().addAll(label,selectedImage,button);
        
        
        StackPane layout = new StackPane();
        layout.getChildren().add(vbox);
        
        //Taking care of button
        button.setOnAction(e -> {
            clock++;
            Image im = maqEstados.getProxEstado();
            selectedImage.setImage(im);
            label.setText("Estado atual: "+maqEstados.getEstadoAtual());
           
            

        });

        scene2 = new Scene(layout, 1200, 800);

        window.setScene(scene1);
        window.show();
    }
 

    public static void main(String[] args) {
        launch(args);
    }



}