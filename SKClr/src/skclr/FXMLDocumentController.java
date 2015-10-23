/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package skclr;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;

/**
 *
 * @author W8
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ColorPicker colorPicker;
    
    @FXML
    private Button applyButton, restButton, infoButton, closeButton;
    
    @FXML
    private ProgressBar bar;
    
    @FXML
    private ProgressIndicator indicator;
    
    @FXML
    private RadioButton darkRB, lightRB;
    
    private Scene scene;
    
    private String text="Vacío";
    
    public void setScene(Scene scene){
        this.scene=scene;
    }
    
    @FXML
    private void closeButtonAction(){
        System.exit(0);
    }
    
    @FXML
    private void showInfo(){
        Alert alert=new Alert(AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText("Se ha realizado un cambio de tema");
        alert.setContentText("El skin activo ahora es: "+text);
        alert.showAndWait();
    }
    
    @FXML
    private void AcercaDe(){
        Alert alert=new Alert(AlertType.INFORMATION);
        alert.setTitle("Creditos");
        alert.setHeaderText("Josseline Juliane Arreola Cruz");
        alert.setContentText("Matricula: 143471");
        alert.showAndWait();
    }
    
    @FXML
    private void appyButtonAction(){
        String hexColor=colorPicker.getValue().toString();
        String hexColorFill=hexColor.substring(2,hexColor.length()-2);
        String hexColorStyle = "-fx-background-color: #" + hexColor;
        
        applyButton.setStyle(hexColorStyle);
        infoButton.setStyle(hexColorStyle);
        bar.setStyle("-fx-accent: #" + hexColorFill);
        indicator.setStyle("-fx-accent: #" + hexColorFill);
        
        Color colorTextFill = Color.web(hexColorFill);
        
        darkRB.setTextFill(colorTextFill);
        lightRB.setTextFill(colorTextFill);
    }
    
    private void createToggleGroup(){
        ToggleGroup group = new ToggleGroup();
        lightRB.setToggleGroup(group);
        darkRB.setToggleGroup(group);
    }
    
    @FXML
    public void changed(){
        if (lightRB.isSelected()) text= "Light";
        if (darkRB.isSelected()) text= "Dark";
        
        scene.getStylesheets().clear();
        scene.getStylesheets().add(SKClr.class.getResource(text+".css").toExternalForm());
    }
    
    @FXML
    private void restButtonAction(){
        Color blackColor = Color.web("000000");
        scene.getStylesheets().clear();
        
        bar.setStyle(null);
        indicator.setStyle(null);
        
        darkRB.setTextFill(blackColor);
        lightRB.setTextFill(blackColor);
        
        text = "Vacío";
    }
    
    @FXML
    private void applyCSS(ActionEvent event){
        MenuItem item = (MenuItem) event.getSource();
        text = item.getText();
        System.out.println("El skin seleccionado es: "+ text);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(SKClr.class.getResource(text+".css").toExternalForm());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createToggleGroup();
    }    
    
}
