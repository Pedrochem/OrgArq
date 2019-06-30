import java.io.FileInputStream;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Tela extends Application {

    Stage window;
    int clock = 0;
    Button button;
    ImageView selectedImage;
    Image busca;
    Image decodifica; 
   
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Adding images
        window = primaryStage;
        busca = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/busca.png"));
        decodifica = new Image (new FileInputStream("/home/pedro/Desktop/OrgArq/images/decodifica.png"));

        //Taking care of image display
        selectedImage = new ImageView();   
        selectedImage.setImage(getImage());
       
        //Creating button
        button = new Button("Next");

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(50);
        vbox.getChildren().addAll(selectedImage,button);
        
        StackPane layout = new StackPane();
        layout.getChildren().add(vbox);
        
        //Taking care of button
        button.setOnAction(e -> {
            clock++;
            //TODO (MAQUINA DE ESTADOS; BOOLEAN INSTRUCAO ACABOU)

        });

        Scene scene = new Scene(layout, 1200, 800);

        window.setScene(scene);
        window.show();
    }

    



    public Image getImage(){
        switch (clock) {
            case 0:
                return busca;
            default:
                break;
        }
        return decodifica;

    }

    public static void main(String[] args) {
        launch(args);
    }



}