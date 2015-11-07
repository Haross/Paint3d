
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint3d;

import static java.lang.Math.abs;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import static javafx.scene.shape.DrawMode.FILL;
import static javafx.scene.shape.DrawMode.LINE;
import javafx.scene.shape.Line;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Javier, Josseline, Hugo
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ColorPicker colorPicker;
    
    @FXML
    private Button btnBox, btnRect,btnSeleccionar, btnCylinder, btnSphere,btnPyramid,btnBorrar, btnLine;
   
    @FXML
    private Slider anguloX, anguloY, anguloZ;
    @FXML
    private SubScene sub1;
    
    Group root = new Group(); 
    private String figura = "";
    boolean inDrag = false;
    boolean bandColor = true;
    boolean select = false;
    double startX = -1, startY = -1;
    boolean borrar = false;
    //Contadores de las figuras
    int contadorS = 0,contadorLine = 0,contadorB = 0,contadorP = 0,contadorC = 0,contadorRec=0;
    
    
    
//------------------------Métodos para establecer figuras---------------//
     @FXML
    private void setRec(ActionEvent event) {
        sub1.setCursor(Cursor.DEFAULT);
        figura = "rec";
    }
    
    @FXML
    private void setB(ActionEvent event) {
         sub1.setCursor(Cursor.DEFAULT);
        figura = "box";
    }
    
    @FXML
    private void setC(ActionEvent event){
        figura = "cylinder";
    }
     @FXML
    private void setLine(ActionEvent event){
         sub1.setCursor(Cursor.DEFAULT);
        figura = "line";
    }
    
    @FXML
    private void setS(ActionEvent event){
         sub1.setCursor(Cursor.DEFAULT);
        figura = "sphere";
    }
   
    @FXML
    private void setP(ActionEvent event){
         sub1.setCursor(Cursor.DEFAULT);
        figura = "pyramid";
    }
//------------Métodos de botones auxiliares-------------------------------------
    @FXML private void borrar(){
        borrar = true;
        cursorBorrador();
    }
    
    @FXML private void seleccionar(){
        if(btnSeleccionar.getText().equals("seleccionar")){
            btnSeleccionar.setText("deseleccionar");
            select = true;
        }else{
            btnSeleccionar.setText("seleccionar");
            select = false;
        }
        bandColor = true;
    }
    @FXML void clear(ActionEvent event){
        root.getChildren().clear();
    }
    public void cursorBorrador(){
       Image borrador = new Image(getClass().getResourceAsStream("borrador.png"));
       sub1.setCursor(new ImageCursor(borrador,
                                      borrador.getWidth()/2,
                                      borrador.getHeight()/2));
   }
    
   @FXML
    private void AcercaDe(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Créditos");
        alert.setHeaderText("Universidad Politécnica de Chiapas");
        alert.setContentText("Josseline Juliane Arreola Cruz | Matricula: 143471\n Javier de Jesús Flores Herrera | Matricula: 143372 \n Hugo Sarmiento Toledo | Matricula: 143359 \n Dr. Juan Carlos López Pimentel \n Programación Visual");
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
        if(y<=315){
            return -315+y;
        }else{ 
           return (y - 316) * (315) / (314) + 1;
        }
    }
//-----------Métodos para dibujar figuras---------------------------
    @FXML
    private void onMousePressedListener(MouseEvent e){
        this.startX = e.getX();
        this.startY = e.getY();
        inDrag = true;
        System.err.println("mousePressed at" + startX + ", "+ startY);
    }  
    
    @FXML
    private void drag(MouseEvent e){
         double curX = e.getX();
         double curY = e.getY();
         String id ="";        
         if (inDrag == true) {
             sub1.setCursor(Cursor.CROSSHAIR);
             switch(figura){
                 case "box": 
                     contadorB =contadorB-1;
                     id = "#"+figura+contadorB;
                     root.getChildren().remove(root.lookup(id));
                     addBox(posX(startX),posY(startY),abs((curX-startX))*2);
                     break;
                 case "cylinder": 
                     contadorC = contadorC-1;
                     id = "#"+figura+contadorC;
                     root.getChildren().remove(root.lookup(id));
                     addCylinder(posX(startX),posY(startY),abs((curX-startX)));
                     break;
                 case "sphere": 
                     contadorS = contadorS-1;
                     id = "#"+figura+contadorS;
                     //método que elimina figura
                     root.getChildren().remove(root.lookup(id));
                     //se crea nueva figura
                     addSphere(posX(startX),posY(startY),abs((curX-startX)));
                     break;
                 case "pyramid": 
                     contadorP = contadorP -1;
                     id = "#"+figura+contadorP;
                     root.getChildren().remove(root.lookup(id));
                     addTriangle(posX(startX),posY(startY),(float)abs((curX-startX)*2));
                     break;
                 case "rec":
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
                     break;
             }             
        }
    }

    @FXML
    private void onReleased(MouseEvent e){
        inDrag = false;
        sub1.setCursor(Cursor.DEFAULT);
        switch(figura){
            case "box": 
                contadorB++;
                break;
            case "cylinder": 
                contadorC++;
                break;
            case "sphere": 
                contadorS++;
                break;
            case "pyramid": 
                contadorP++;
                break;
            case "rec":
                contadorRec++;
                break;
            case "line":
                contadorLine++;
                break;
            }
    }
    
//----------------------Métodos de creación de figuras-----------------------
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
   
    
    public void addBox(double x, double y, double tam){
        Box box = new Box(tam, tam*2,tam);
        PhongMaterial b = new PhongMaterial();
        b.setDiffuseColor(colorPicker.getValue());
        box.setMaterial(b);
        box.setOnMousePressed((e) ->{
            
            if(select){
                String[] aux = box.getId().split("box");
                root.getChildren().remove(box);
                root.getChildren().add(box);
                box.setDrawMode(LINE);
                if(bandColor){
                    colorPicker.setValue(b.getDiffuseColor());
                    bandColor = false;
                }else{
                    b.setDiffuseColor(colorPicker.getValue());   
                }
                if(borrar){
                    sub1.setCursor(Cursor.DEFAULT);
                    borrar = false;
                    root.getChildren().remove(box);
                }   
            }else{
               
                box.setDrawMode(FILL);
            }          
            
        });
    
        //Se asigna un id a la esfera para poder elminarla facilmente
        box.setId("box"+contadorB++);
        box.setCullFace(CullFace.BACK);
        box.setTranslateX(x);
        box.setTranslateY(y);
        box.setTranslateZ(-100);
        root.getChildren().add(box);
        Rotate rxBox = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
        Rotate ryBox = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rzBox = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
        rxBox.setAngle(anguloX.getValue());
        ryBox.setAngle(anguloY.getValue());
        rzBox.setAngle(anguloZ.getValue());
        box.getTransforms().addAll(rxBox, ryBox, rzBox);
    }
    public void addCylinder(double x, double y, double radio){
        Cylinder cylinder = new Cylinder(radio,radio*2); 
        PhongMaterial c = new PhongMaterial();
        c.setDiffuseColor(colorPicker.getValue());
        cylinder.setMaterial(c);
        cylinder.setId("cylinder"+contadorC++);
        root.getChildren().add(cylinder);
        cylinder.setTranslateX(x); 
        cylinder.setTranslateY(y); 
        cylinder.setTranslateZ(300);
        cylinder.setOnMousePressed((e) ->{
            c.setDiffuseColor(colorPicker.getValue());
            if(borrar){
                 sub1.setCursor(Cursor.DEFAULT);
                borrar = false;
                root.getChildren().remove(cylinder);
                
            }   
        });
        Rotate rxC = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
        Rotate ryC = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rzC = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
        rxC.setAngle(anguloX.getValue());
        ryC.setAngle(anguloY.getValue());
        rzC.setAngle(anguloZ.getValue());
        cylinder.getTransforms().addAll(rxC, ryC, rzC);
        
    }
    public void addSphere(double x, double y, double radio){
        Sphere sphere = new Sphere(radio);
        PhongMaterial s = new PhongMaterial();
        s.setDiffuseColor(colorPicker.getValue());
        sphere.setMaterial(s);
        sphere.setCullFace(CullFace.BACK);
        sphere.setTranslateX(x);
        sphere.setTranslateY(y);
        sphere.setTranslateZ(30);
        sphere.setOnMousePressed((e) ->{
            s.setDiffuseColor(colorPicker.getValue());
            if(borrar){
                 sub1.setCursor(Cursor.DEFAULT);
                borrar = false;
                root.getChildren().remove(sphere);
            }   
        });
        //Se asigna un id a la esfera para poder elminarla facilmente
        sphere.setId("sphere"+contadorS++);
        root.getChildren().add(sphere);
    }
    private void addTriangle(double x, double y, float h){
        TriangleMesh pyramidMesh = new TriangleMesh();
        pyramidMesh.getTexCoords().addAll(0,0);
     //h = 175;                    // Height
    float s = 300;                    // Side
        pyramidMesh.getPoints().addAll(
        0,    0,    0,            // Point 0 - Top
        0,    h,    -s/2,         // Point 1 - Front
        -s/2, h,    0,            // Point 2 - Left
        s/2,  h,    0,            // Point 3 - Back
        0,    h,    s/2           // Point 4 - Right
    );
        pyramidMesh.getFaces().addAll(
        0,0,  2,0,  1,0,          // Front left face
        0,0,  1,0,  3,0,          // Front right face
        0,0,  3,0,  4,0,          // Back right face
        0,0,  4,0,  2,0,          // Back left face
        4,0,  1,0,  2,0,          // Bottom rear face
        4,0,  3,0,  1,0           // Bottom front face
    ); 
    MeshView pyramid = new MeshView(pyramidMesh);
    pyramid.setId("pyramid"+contadorP++);
    pyramid.setDrawMode(DrawMode.FILL);
    pyramid.setCullFace(CullFace.BACK);
    PhongMaterial p = new PhongMaterial();
    p.setDiffuseColor(colorPicker.getValue());
    pyramid.setOnMousePressed((e) ->{
            p.setDiffuseColor(colorPicker.getValue());
            if(borrar){
                 sub1.setCursor(Cursor.DEFAULT);
                borrar = false;
                root.getChildren().remove(pyramid);
            }   
        });
    pyramid.setMaterial(p);
    pyramid.setTranslateX(x);
    pyramid.setTranslateY(y);
    pyramid.setTranslateZ(30);
    root.getChildren().add(pyramid);
    }
    
    @FXML
    private void closeButtonAction(){
        System.exit(0);
    }
 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(20000.0);
        camera.setTranslateZ(-1000);
        
        camera.setFieldOfView(35);
        sub1.setCamera(camera);
        sub1.setRoot(root); 
       
        Image imageBox = new Image(getClass().getResourceAsStream("cubo.png"));
        ImageView iv = new ImageView(imageBox);
        iv.setFitWidth(30);
        iv.setFitHeight(30);
        btnBox.setGraphic(iv);
        
        Image imageCylinder = new Image(getClass().getResourceAsStream("cilindro.png"));
        ImageView ic = new ImageView(imageCylinder);
        ic.setFitWidth(30);
        ic.setFitHeight(30);
        btnCylinder.setGraphic(ic);
        
        Image imageSphere = new Image(getClass().getResourceAsStream("esfera.png"));
        ImageView isp = new ImageView(imageSphere);
        isp.setFitHeight(30);
        isp.setFitWidth(30);
        btnSphere.setGraphic(isp);
        
        Image imagePyramid = new Image(getClass().getResourceAsStream("pyramid.png"));
        ImageView ipy = new ImageView(imagePyramid);
        ipy.setFitHeight(30);
        ipy.setFitWidth(30);
        btnPyramid.setGraphic(ipy);
        
        Image imageRectangulo = new Image(getClass().getResourceAsStream("rectagulo.png"));
        ImageView iR = new ImageView(imageRectangulo);
        iR.setFitHeight(30);
        iR.setFitWidth(30);
        btnRect.setGraphic(iR);
        
        Image imageLine = new Image(getClass().getResourceAsStream("line.png"));
        ImageView iL = new ImageView(imageLine);
        iL.setFitHeight(20);
        iL.setFitWidth(30);
        btnLine.setGraphic(iL);
        
        Image imageBorrador = new Image(getClass().getResourceAsStream("borrador.png"));
        ImageView iB = new ImageView(imageBorrador);
        iB.setFitHeight(25);
        iB.setFitWidth(35);
        btnBorrar.setGraphic(iB);
    } 
    
}
