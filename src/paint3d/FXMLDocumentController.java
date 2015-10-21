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
    private SplitPane Split;
    
    @FXML
    private Group root;
    //hola
    private PerspectiveCamera camera;
    PhongMaterial blueStuff = new PhongMaterial();
     @FXML
    private void setC(ActionEvent event) {
       addCylinder();
       addBox();
      // addSphere();
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image imageBox = new Image(getClass().getResourceAsStream("cubo.png"));
        ImageView iv = new ImageView(imageBox);
        iv.setFitWidth(30);
        iv.setFitHeight(30);
        btnBox.setGraphic(iv);
        Platform.runLater(() ->  initSceneAndCamera());
        initColors();
    } 
    private void initSceneAndCamera() {
      
        this.camera = new PerspectiveCamera(true);
        this.camera.setNearClip(0.1);
        this.camera.setFarClip(20000.0);
        this.camera.setTranslateZ(-1000);
        this.camera.setFieldOfView(35);
        
        Group g = new Group();
        
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-1000);
        light.setTranslateY(100);
        light.setTranslateZ(-1000);
        g.getChildren().add(light);

        /*SubScene scene3d;
        scene3d = new SubScene(g,650,620);
        scene3d.setFill(Color.WHITE);
        scene3d.setCamera(this.camera);
        scene3d.setPickOnBounds(true);
       
        this.root.getChildren().add(scene3d);*/
    }   
    
    private void initColors(){
        blueStuff.setDiffuseColor(Color.LIGHTBLUE);
        blueStuff.setSpecularColor(Color.BLUE);
    }
}
