
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint3d;

import static java.lang.Math.abs;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Javier, Josseline, Hugo
 */
public class FXMLDocumentController implements Initializable {
    @FXML private ColorPicker colorPicker;
    @FXML private Button btnBox, btnRotacion,btnRect,btnSeleccionar, btnCylinder, btnSphere,btnPyramid,btnBorrar, btnLine;
    @FXML private Slider anguloX, anguloY, anguloZ;
    @FXML public SubScene sub1;  
    
    paint p = new paint();
   double startX, startY;
//------------------------Métodos para establecer figuras---------------//
     @FXML
    private void setRec(ActionEvent event) {
        p.setActualFigura("rec");
    }
    
    @FXML
    private void setB(ActionEvent event) {
        p.setActualFigura("box");
    }
    
    @FXML
    private void setC(ActionEvent event){
        p.setActualFigura("cylinder");
    }
     @FXML
    private void setLine(ActionEvent event){
        p.setActualFigura("line");
    }
    
    @FXML
    private void setS(ActionEvent event){
        p.setActualFigura("sphere");
    }
   
    @FXML
    private void setP(ActionEvent event){
        p.setActualFigura("pyramid");
    }
//------------Métodos de botones auxiliares-------------------------------------
    @FXML private void borrar(){
        p.setBorrador(true);
    }
    
    @FXML private void seleccionar(){
       
        if(!p.getSeleccion()){
            btnSeleccionar.setGraphic(icono("open.png",25,30));
            p.setActualFigura("");
            p.setSeleccion(true);
            btnRotacion.setDisable(false);
        }else{
            btnSeleccionar.setGraphic(icono("hand.png",25,30));
            p.setSeleccion(false);
            p.noSeleccion();
            btnRotacion.setDisable(true);
        }
    }
    
    //Método que cambia el color de la figura seleccionada
    @FXML private void cambioColor(){
        if(p.getActualFigura() != null ){
            p.color(p.getActualFigura(),colorPicker.getValue() );
            return;
        }
        p.setColor2d(colorPicker.getValue()); 
    }
    
    @FXML private void rotacionFigura(){
        p.setRotacion(p.getActualFigura(), anguloX.getValue(), anguloY.getValue(), anguloZ.getValue());
    }
      
    @FXML void clear(ActionEvent event){
        p.clear();
    }

   @FXML
    private void AcercaDe(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Créditos");
        alert.setHeaderText("Universidad Politécnica de Chiapas");
        alert.setContentText(" Josseline Juliane Arreola Cruz | Matricula: 143471\n Javier de Jesús Flores Herrera | Matricula: 143372 \n Hugo Sarmiento Toledo | Matricula: 143359 \n Dr. Juan Carlos López Pimentel \n Programación Visual");
        alert.showAndWait();
    }
    
//-------------Métodos para manejar coordenadas en la subscena-------------------
    private double posX(double x){
        double aux = sub1.getHeight();
        if(x<=aux/2){
            return -aux/2+x;
        }else{
            return (x - (aux+1)/2) *(aux/2) + 1;
        }
    }
    private double posY(double y){
        double aux = sub1.getWidth();
        if(y<=aux/2){
            return -aux/2+y;
        }else{ 
            System.out.println(map(y,aux/2,aux,0,aux/2));
           return map(y,aux/2,aux,0,aux/2);
        }
    }
    private double map(double x, double in_min, double in_max, double out_min, double out_max){
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

        
    
//-----------Métodos para dibujar figuras---------------------------
    @FXML
    private void onMousePressedListener(MouseEvent e){
        p.setDragging(true); 
        this.startX = e.getX();
        this.startY = e.getY();
        System.err.println("mousePressed at" + startX + ", "+ startY);
    }  
    
    @FXML
    private void drag(MouseEvent e){
         double curX = e.getX();
         double curY = e.getY();      
         if (p.getDragging()) {
             sub1.setCursor(Cursor.CROSSHAIR);
             switch(p.getNombreActualFigura()){
                 case "box": 
                     p.addBox(posX(startX),posY(startY),abs((curX-startX))*2, colorPicker.getValue());
                     break;
                 case "cylinder": 
                     p.addCylinder(posX(startX),posY(startY),abs((curX-startX)), colorPicker.getValue());
                     break;
                 case "sphere": 
                     p.addSphere(posX(startX),posY(startY),abs((curX-startX)*1.1),colorPicker.getValue());
                     break;
                 case "pyramid": 
                    p.addPyramid(posX(startX),posY(startY),(float)abs((curX-startX)*2),colorPicker.getValue());
                     break;
                 case "rec":
                     p.addRec(posX(startX),posY(startY),abs((curX-startX)),abs((curY-startY)),colorPicker.getValue());
                     break;
                 case "line":
                     p.addLine(posX(startX),posY(startY),posX(curX),posY(curY),colorPicker.getValue());
                     break;
             }   
             p.setRotacion(p.getLast(), anguloX.getValue(), anguloY.getValue(), anguloZ.getValue());
        }
    }

    @FXML
    private void onReleased(MouseEvent e){
        p.setDragging(false);
        p.setNew();
    }

    @FXML
    private void closeButtonAction(){
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        p.setSubScene(sub1);        
        colorPicker.setValue(Color.RED);
        btnRotacion.setDisable(true);
        btnBox.setGraphic(icono("cubo.png",30,30));
        btnCylinder.setGraphic(icono("cilindro.png",30,30));
        btnSphere.setGraphic(icono("esfera.png",30,30));
        btnPyramid.setGraphic(icono("pyramid.png",30,30));
        btnRect.setGraphic(icono("rectagulo.png",30,30));
        btnLine.setGraphic(icono("line.png",20,30));     
        btnBorrar.setGraphic(icono("borrador.png",25,35));
        btnSeleccionar.setGraphic(icono("hand.png",25,30));
    }  
    
    private ImageView icono(String name, double width, double height){
        Image imageSeleccion = new Image(getClass().getResourceAsStream(name));
        ImageView iS = new ImageView(imageSeleccion);
        iS.setFitHeight(height);
        iS.setFitWidth(width);
        return iS;
    }
}