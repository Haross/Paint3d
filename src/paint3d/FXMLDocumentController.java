/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint3d;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
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
    private Group root;
    //hola
    private PerspectiveCamera camera;
    PhongMaterial blueStuff = new PhongMaterial();
     @FXML
    private void setB(ActionEvent event) {
       addBox();
      // 
    }
    
    @FXML
    private void setC(ActionEvent event){
        addCylinder();
    }
    
    @FXML
    private void setS(ActionEvent event){
        addSphere();
    }
    
    @FXML
    private void setP(ActionEvent event){
        addTriangle();
    }
    
    
 
    public void addBox(){
        Box box = new Box(100, 100, 100);
        box.setMaterial(blueStuff);
        box.setTranslateX(150);
        box.setTranslateY(100);
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
        cylinder.setTranslateX(200); 
        cylinder.setTranslateY(200); 
        cylinder.setTranslateZ(200);
    }
    public void addSphere(){
        Sphere sphere = new Sphere(100);
        sphere.setTranslateX(180);
        sphere.setTranslateY(100);
        sphere.setTranslateZ(100);
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
    pyramid.setMaterial(blueStuff);
    pyramid.setTranslateX(250);
    pyramid.setTranslateY(250);
    pyramid.setTranslateZ(100);
    root.getChildren().add(pyramid);
    
    
    
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        
        Image imagePyramid = new Image(getClass().getResourceAsStream("pyramid.jpg"));
        ImageView ipy = new ImageView(imagePyramid);
        ipy.setFitHeight(30);
        ipy.setFitWidth(30);
        btnPyramid.setGraphic(ipy);
        
        
        initColors();
    } 
    
    
    private void initColors(){
        blueStuff.setDiffuseColor(Color.LIGHTBLUE);
        blueStuff.setSpecularColor(Color.BLUE);
    }
}
