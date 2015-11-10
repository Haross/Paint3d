
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
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;

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
    boolean inDrag = false;
//------------------------Métodos para establecer figuras---------------//
     @FXML
    private void setRec(ActionEvent event) {
        
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
        System.out.println("color changed");
        p.color(p.getActualFigura(),colorPicker.getValue() );
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
        if(x<=370){
            return -370+x;
        }else{
            return (x - 371) + 1;
        }
    }
    private double posY(double y){
        if(y<=322){
            return -322+y;
        }else{ 
           return (y - 323) * (322) / (321) + 1;
        }
    }
//-----------Métodos para dibujar figuras---------------------------
    @FXML
    private void onMousePressedListener(MouseEvent e){
        p.setDragging(true); 
        this.startX = e.getX();
        this.startY = e.getY();
        inDrag = true;
        System.err.println("mousePressed at" + startX + ", "+ startY);
    }  
    
    @FXML
    private void drag(MouseEvent e){
         double curX = e.getX();
         double curY = e.getY();      
         if (inDrag == true) {
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
                 /*case "rec":
                     contadorRec = contadorRec-1;
                     id = "#"+figura+contadorRec;
                     System.out.println(root.getChildren().remove(root.lookup(id)));
                     addRec(posX(startX),posY(startY),abs((curX-startX)),abs((curY-startY)));
                     break;
                 case "line":
                     contadorLine = contadorLine-1;
                     id = "#"+figura+contadorLine;
                     root.getChildren().remove(root.lookup(id));
                     addLine(posX(startX),posY(startY),posX(curX),posY(curY));
                     break;*/
             }   
             p.setRotacion(p.getLast(), anguloX.getValue(), anguloY.getValue(), anguloZ.getValue());
        }
    }

    @FXML
    private void onReleased(MouseEvent e){
        inDrag = false;
        p.setDragging(inDrag);
        p.setNew();
    }
  /*  
//----------------------Métodos de creación de figuras 2D-----------------------
    public void addRec(double x, double y,double w,double h){
        Rectangle r = new Rectangle();
        r.setId("rec"+contadorRec++);
        r.setFill(colorPicker.getValue());
        r.setX(x);
        r.setY(y);
        r.setWidth(w-5);
        r.setHeight(h-5);
        root.getChildren().add(r);
        r.setOnMousePressed((e) ->{
            r.setFill(colorPicker.getValue());
            if(borrar){
                borrar = false;
                root.getChildren().remove(r);
            }   
        });
    }
    
   public void addLine(double x, double y, double x2, double y2){
       Line l= new Line();
       l.setId("line"+contadorLine++);
       l.setStartX(x);
       l.setStartY(y);
       l.setEndX(x2);
       l.setEndY(y2);
       l.setStrokeWidth(5);
       l.setStroke(colorPicker.getValue());
        l.setOnMousePressed((e) ->{
            l.setStroke(colorPicker.getValue());
            if(borrar){
                 sub1.setCursor(Cursor.DEFAULT);
                borrar = false;
                root.getChildren().remove(l);
            }   
        });
       root.getChildren().add(l);
    }
      
*/
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