
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
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Javier, Josseline, Hugo
 */
public class FXMLDocumentController implements Initializable {
    //h
    @FXML
    private Button btnBox;
    
    @FXML
    private Button btnCylinder;
    
    @FXML
    private Button btnSphere;
    
    @FXML
    private Button btnPyramid;
    
    @FXML
    private SplitPane Split;
   
    @FXML
    private SubScene sub1;
    
    Group root = new Group(); 
    private String figura = "";
    boolean inDrag = false;
    double startX = -1, startY = -1;
    PhongMaterial blueStuff = new PhongMaterial();
    //Contadores de las figuras
    int contadorS = 0;
    int contadorB = 0;
    int contadorP = 0;
    int contadorC = 0;
    String a = "";
    
     @FXML
    private void setB(ActionEvent event) {
        figura = "box";
        System.out.println("La figura actual es "+ figura);
    }
    
    @FXML
    private void setC(ActionEvent event){
        figura = "cylinder";
        System.out.println("La figura actual es "+ figura);
    }
    
    @FXML
    private void setS(ActionEvent event){
        figura = "sphere";
        System.out.println("La figura actual es "+ figura);
    }
    //c:
    @FXML
    private void setP(ActionEvent event){
        figura = "pyramid";
        System.out.println("La figura actual es "+ figura);
    }
    //Función para acomodar las coordenadas
    private double posX(double x){
        if(x<=370){
            return -370+x;
        }else{
            return (x - 371) * (370 - 1) / (740 - 371) + 1;
        }
    }
    //Función para acomodar las coordenadas
    private double posY(double y){
        if(y<=315){
            return -315+y;
        }else{ 
           return (y - 316) * (316 -1) / (630 - 316) + 1;
        }
    }

    @FXML
    private void onMousePressedListener(MouseEvent e){
        this.startX = e.getX();
        this.startY = e.getY();
        //Funcion para activar la lectura del drag en la funcion de drag
        inDrag = true;
        System.err.println("mousePressed at" + startX + ", "+ startY);
    }  
    @FXML
    private void drag(MouseEvent e){
         double curX = e.getX();
         String id ="";        
         if (inDrag == true) {
             switch(figura){
                 case "box": 
                     //Contador para quitar anterior
                     contadorB =contadorB-1;
                     id = "#"+figura+contadorB;
                     root.getChildren().remove(root.lookup(id));
                     addBox(posX(startX),posY(startY),abs((curX-startX)));
                     break;
                 case "cylinder": 
                     contadorC = contadorC-1;
                     id = "#"+figura+contadorC;
                     root.getChildren().remove(root.lookup(id));
                     addCylinder(posX(startX),posY(startY),abs((curX-startX)));
                     break;
                 case "sphere": 
                     //se hace el contador -1 para eliminar la figura anterior del drag 
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
             }
             //Elimina todas las figuras
            //root.getChildren().clear();
             
        }
    }
    
    @FXML
    private void onReleased(MouseEvent e){
        inDrag = false;
        //Este switch es para aumentar los contadores para el id
        //porque si no se aumentan se elmina la figura dibujada anterior siempre
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
             }
    }

    public void addBox(double x, double y, double tam){
        Box box = new Box(tam, tam,tam);
        //Se asigna un id a la esfera para poder elminarla facilmente
        box.setId("box"+contadorB++);
        box.setCullFace(CullFace.BACK);
        box.setMaterial(blueStuff);
        box.setTranslateX(x);
        box.setTranslateY(y);
        box.setTranslateZ(-100);
        root.getChildren().add(box);
        Rotate rxBox = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
        Rotate ryBox = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rzBox = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
        rxBox.setAngle(30);
        ryBox.setAngle(40);
        rzBox.setAngle(0);
        box.getTransforms().addAll(rxBox, ryBox, rzBox);
    }
    public void addCylinder(double x, double y, double radio){
        Cylinder cylinder = new Cylinder(radio,radio*2); 
        cylinder.setId("cylinder"+contadorC++);
        root.getChildren().add(cylinder);
        cylinder.setMaterial(blueStuff);
        cylinder.setTranslateX(x); 
        cylinder.setTranslateY(y); 
        cylinder.setTranslateZ(300);
        Rotate rxC = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
        Rotate ryC = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rzC = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
        rxC.setAngle(30);
        ryC.setAngle(40);
        rzC.setAngle(0);
        cylinder.getTransforms().addAll(rxC, ryC, rzC);
        
    }
    public void addSphere(double x, double y, double radio){
        Sphere sphere = new Sphere(radio);
        sphere.setCullFace(CullFace.BACK);
        sphere.setTranslateX(x);
        sphere.setTranslateY(y);
        sphere.setTranslateZ(30);
        sphere.setMaterial(blueStuff);
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
    pyramid.setMaterial(blueStuff);
    pyramid.setTranslateX(x);
    pyramid.setTranslateY(y);
    pyramid.setTranslateZ(30);
    root.getChildren().add(pyramid);
    }
    
    @FXML
    private void closeButtonAction(){
        System.exit(0);
    }
    
    @FXML
    private void AcercaDe(){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Créditos");
        alert.setHeaderText("Universidad Politécnica de Chiapas");
        alert.setContentText("Josseline Juliane Arreola Cruz | Matricula: 143471\n Javier de Jesús Flores Herrera | Matricula: 143372 \n Hugo Sarmiento Toledo | Matricula: 143359 \n Dr. Juan Carlos López Pimentel \n Programación Visual");
        alert.showAndWait();
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
        
        
        initColors();
    } 
    
    
    private void initColors(){
        blueStuff.setDiffuseColor(Color.RED);
        blueStuff.setSpecularColor(Color.WHITE);
    }

//
}
