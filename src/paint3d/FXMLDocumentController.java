
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
    @FXML private SubScene sub1;
    
    Group root = new Group(); 
    private String figura = "";
    boolean inDrag = false;
    boolean bandColor = true;
    boolean select = false;
    double startX = -1, startY = -1;
    boolean borrar = false;
    //Contadores de las figuras
    int contadorS = 0,contadorLine = 0,contadorB = 0,contadorP = 0,contadorC = 0,contadorRec=0;
    Shape3D shape3d; //Guarda la figura seleccionada
    
    
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
        sub1.setCursor(Cursor.DEFAULT);
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
        if(select==false){
            Image imageSeleccion = new Image(getClass().getResourceAsStream("open.png"));
            ImageView iS = new ImageView(imageSeleccion);
            iS.setFitHeight(30);
            iS.setFitWidth(25);
            btnSeleccionar.setGraphic(iS);
            figura = "";
            select = true;
            btnRotacion.setDisable(false);
        }else{
            Image imageSeleccion = new Image(getClass().getResourceAsStream("hand.png"));
            ImageView iS = new ImageView(imageSeleccion);
            iS.setFitHeight(30);
            iS.setFitWidth(25);
            btnSeleccionar.setGraphic(iS);
            select = false;
            noSeleccion();
            btnRotacion.setDisable(true);
        }
        bandColor = true;
    }
    
    //Método que cambia el color de la figura seleccionada
    @FXML private void cambioColor(){
        System.out.println("color changed");
        if(shape3d != null){
            PhongMaterial b = (PhongMaterial) shape3d.getMaterial();
            b.setDiffuseColor(colorPicker.getValue());   
        }
    }
    
    @FXML private void rotacionFigura(){
        Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
        Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rz = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
        if(shape3d != null){
            rx.setAngle(anguloX.getValue());
            ry.setAngle(anguloY.getValue());
            rz.setAngle(anguloZ.getValue());
            shape3d.getTransforms().addAll(rx, ry, rz);
        }
    }
    
    //Este método sirve para posicionar y rotar
    private void posicionYColor(Shape3D shape3d, double x, double y, double z){
        Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
        Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rz = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
        if(shape3d != null){
            rx.setAngle(anguloX.getValue());
            ry.setAngle(anguloY.getValue());
            rz.setAngle(anguloZ.getValue());
            shape3d.getTransforms().addAll(rx, ry, rz);
            shape3d.setTranslateX(x);
            shape3d.setTranslateY(y);
            shape3d.setTranslateZ(z);
            PhongMaterial b = new PhongMaterial();
            b.setDiffuseColor(colorPicker.getValue());
            shape3d.setMaterial(b);
        }
    }
    
    private void noSeleccion(){
        if(shape3d != null){
        PhongMaterial b = (PhongMaterial) shape3d.getMaterial();
        b.setBumpMap(null);
        shape3d = null; 
        }
    }
      //Metodo que resalta la figura seleccionada
       private void hover(){  
        PhongMaterial b = (PhongMaterial) shape3d.getMaterial();
        root.getChildren().remove(shape3d);
        Image imageBox = new Image(getClass().getResourceAsStream("images.jpg"));
        b.setBumpMap(imageBox);
        root.getChildren().add(shape3d);
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
                     addSphere(posX(startX),posY(startY),abs((curX-startX)*1.1));
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
      
//----------------------Métodos de creación de figuras 3D-----------------------
   
   /*Pasos
        -Declaracion
        -Etiqueta
        -Color
        -Posiciones
        -CullFace
        -Añadir figura a escena
   
   */
    public void addBox(double x, double y, double tam){
        Box box = new Box(tam, tam*2,tam);
        box.setId("box"+contadorB++);
        posicionYColor(box,x,y,-100);
        box.setCullFace(CullFace.BACK);
        root.getChildren().add(box);
        
        box.setOnMousePressed((e) ->{ 
            if(borrar){
                    sub1.setCursor(Cursor.DEFAULT);
                    borrar = false;
                    root.getChildren().remove(box);
            }   
            if(select && shape3d == null){
                shape3d = box;
                hover();
            }
        }); 
    }
    
    public void addCylinder(double x, double y, double radio){
        Cylinder cylinder = new Cylinder(radio,radio*2); 
        cylinder.setId("cylinder"+contadorC++);
        posicionYColor(cylinder,x,y,300);
        root.getChildren().add(cylinder);
        cylinder.setOnMousePressed((e) ->{
            if(borrar){
                    sub1.setCursor(Cursor.DEFAULT);
                    borrar = false;
                    root.getChildren().remove(cylinder);
             }   
            if(select && shape3d == null){
                shape3d = cylinder;
                hover(); 
            }
        }); 
    }
    
    public void addSphere(double x, double y, double radio){
        Sphere sphere = new Sphere(radio);
        sphere.setId("sphere"+contadorS++);
        PhongMaterial s = new PhongMaterial();
        s.setDiffuseColor(colorPicker.getValue());
        sphere.setMaterial(s);
        posicionYColor(sphere,x,y,30);
        sphere.setCullFace(CullFace.BACK);
        root.getChildren().add(sphere);
        sphere.setOnMousePressed((e) ->{
            if(borrar){
                    sub1.setCursor(Cursor.DEFAULT);
                    borrar = false;
                    root.getChildren().remove(sphere);
            }     
            if(select){
                shape3d = sphere;
                hover();      
            }
        });
    }
    
    private void addTriangle(double x, double y, float h){
        TriangleMesh pyramidMesh = new TriangleMesh();
        pyramidMesh.getTexCoords().addAll(0,0);
        //h = 175;                    // Height
        float s = h/2;                    // Side
        pyramidMesh.getPoints().addAll(
        0,    0,    0,            // Point 0 - Top
        0,    h,    -s,         // Point 1 - Front
        -s, h,    0,            // Point 2 - Left
        s,  h,    0,            // Point 3 - Back
        0,    h,    s           // Point 4 - Right
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
    pyramid.setCullFace(CullFace.NONE);
    pyramid.setOnMousePressed((e) ->{
            if(borrar){
                    sub1.setCursor(Cursor.DEFAULT);
                    borrar = false;
                    root.getChildren().remove(pyramid);
                }   
            if(select && shape3d == null){
                shape3d = pyramid;
                hover();         
            }
        });
    posicionYColor(pyramid,x,y,30);
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
       
        colorPicker.setValue(Color.RED);
        btnRotacion.setDisable(true);
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
        
        Image imageSeleccion = new Image(getClass().getResourceAsStream("hand.png"));
        ImageView iS = new ImageView(imageSeleccion);
        iS.setFitHeight(30);
        iS.setFitWidth(25);
        btnSeleccionar.setGraphic(iS);
    }  
}