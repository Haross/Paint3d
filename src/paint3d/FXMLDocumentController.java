
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint3d;

import static java.lang.Math.abs;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
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
       Group root;
    private String figura = "";
    private PerspectiveCamera camera;
    boolean inDrag = false;
    double startX = -1, startY = -1;
    double curX = -1, curY = -1;
    PhongMaterial blueStuff = new PhongMaterial();
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
    
    private double posX(double x){
        if(x<370){
            return -370+x;
        }else{
            return x*370/740;
        }
    }
    private double posY(double y){
        if(y<315){
            return -315+y;
        }else{
            return y*315/630;
        }
    }
    //Hola
    @FXML
    private void onMousePressedListener(MouseEvent e){
        this.startX = posX(e.getX());
        this.startY = posY(e.getY());
        inDrag = true;
        System.err.println("mousePressed at" + startX + ", "+ startY);
    }  
    @FXML
    private void onMouseReleased(MouseEvent e){
         curX = e.getX();
         curY = e.getY();
         //double w = curX - startX; 
         //double h = curY - startY;         
         if (inDrag == true) {
             switch(figura){
                 case "box": 
                     addBox();
                     break;
                 case "cylinder": 
                     addCylinder();
                     break;
                 case "sphere": 
                     addSphere(startX,startY,abs((curX-startX))/4);
                     break;
                 case "pyramid": 
                     addTriangle();
                     break;
             }
            inDrag = false;  
        }
    }
    public void addBox(){
        Box box = new Box(100, 100, 100);
        box.setCullFace(CullFace.BACK);
        box.setMaterial(blueStuff);
        box.setTranslateX(30);
        box.setTranslateY(30);
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
    public void addCylinder(){
        Cylinder cylinder = new Cylinder(100,50); 
        root.getChildren().add(cylinder);
        cylinder.setMaterial(blueStuff);
        cylinder.setTranslateX(30); 
        cylinder.setTranslateY(-150); 
        cylinder.setTranslateZ(30);
    }
    public void addSphere(double x, double y, double radio){
        Sphere sphere = new Sphere(radio);
        sphere.setCullFace(CullFace.BACK);
        sphere.setTranslateX(x);
        sphere.setTranslateY(y);
        sphere.setTranslateZ(30);
        sphere.setMaterial(blueStuff);
        root.getChildren().add(sphere);
    }
    private void addTriangle(){
        TriangleMesh pyramidMesh = new TriangleMesh();
        pyramidMesh.getTexCoords().addAll(0,0);
    float h = 175;                    // Height
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
    pyramid.setDrawMode(DrawMode.FILL);
    pyramid.setCullFace(CullFace.BACK);
    pyramid.setMaterial(blueStuff);
    pyramid.setTranslateX(30);
    pyramid.setTranslateY(30);
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
        root = new Group(); 
        root.setId("sub1GroupID");
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
