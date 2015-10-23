/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skclr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author W8
 */
public class SKClr extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader myloader= new FXMLLoader(getClass().getResource("/skclr/FXMLDocument.fxml"));
        Parent root=(Parent) myloader.load();
        FXMLDocumentController myController = myloader.getController();
        
        Scene scene = new Scene (root);
        myController.setScene(scene);
        
        stage.setScene(scene);
        stage.setTitle("SkinColor");
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
