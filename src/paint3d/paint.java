/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paint3d;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Javier
 */
public class paint {
    
    Shape3D shape3d, ultimaFigura;
    String actualFigura;
    boolean borrar = false;
    boolean drag = false;
    Group root = new Group();
    double startX = -1, startY = -1;
    
    private SubScene subScene;
    
    int contadorS = 0, contadorLine = 0, contadorB = 0, contadorP = 0, contadorC = 0, contadorRec = 0;
    boolean seleccion = false;
    /**
     *
     * @param subScene contiene la subscene donde se dibujaran las figuras
     */
    public paint(SubScene subScene){
        setSubScene(subScene);
    }

    /**
     *
     */
    public paint(){
        
    }

    /**
     *
     * @param subScene
     */
    public void setSubScene(SubScene subScene ){
        this.subScene = subScene;
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(20000.0);
        camera.setTranslateZ(-1000);
        camera.setFieldOfView(35);
        subScene.setCamera(camera);
        subScene.setRoot(root);
 
    }

    /**
     *
     * @param borrar variable que determina si la opcion de borrar esta habilitada
     */
    public void setBorrador(boolean borrar){
        cursorBorrador();
        this.borrar = borrar;
    }

    /**
     *
     * @param seleccion variable que determina si la opcion de seleccionar esta habilitada
     */
    public void setSeleccion(boolean seleccion){
        this.seleccion = seleccion;
    }

    /**
     *
     * @return regresa el valor booleano de la variable seleccion
     */
    public boolean getSeleccion(){
        return seleccion;
    }

    /**
     *  Método que establece el drag
     * @param drag variable que indica si la figura será dibujada en drag
     */
    public void setDragging(boolean drag){
        this.drag = drag;
    }

    /**
     * Método que establece la figura. 
     * Sólo se permite: cylinder, box, pyramid, sphere
     * @param figura variable que recibe el nombre de la figura
     */
    public void setActualFigura(String figura){
        subScene.setCursor(Cursor.DEFAULT);
        actualFigura = figura;
    }
       
    /**
     * Método que aumenta el contador de la figura actual para poder crear una nueva figura
     */
    public void setNew() {
        subScene.setCursor(Cursor.DEFAULT);
        switch(actualFigura) {
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
    
    /**
     * Método que añade un cubo a la subScene 
     * @param x
     * @param y
     * @param tam tamaño que tendrá el cubo
     * @param colorValue
     */
    public void addBox(double x, double y, double tam, Color colorValue) {
        System.out.println("figura creada");
        if(drag){
            contadorB = contadorB - 1;
            String id = "#box" + contadorB;
            root.getChildren().remove(root.lookup(id));
        }
            Box box = new Box(tam, tam, tam);
            box.setId("box" + contadorB++);
            posicion(box, x, y, -100);
            color(box,colorValue);
            box.setCullFace(CullFace.BACK);
            root.getChildren().add(box);
            ultimaFigura = box;
            box.setOnMousePressed((e) -> {
                if (borrar) {
                    subScene.setCursor(Cursor.DEFAULT);
                    borrar = false;
                    root.getChildren().remove(box);
                }
                if (seleccion && shape3d == null) {
                    shape3d = box;
                    hover();
                }
            });
        
    }
    
    /**
     *
     * @param x
     * @param y
     * @param radio
     * @param colorValue
     */
    public void addSphere(double x, double y, double radio, Color colorValue){
        if(drag){
            contadorS = contadorS-1;
            String id = "#sphere"+contadorS;
            root.getChildren().remove(root.lookup(id));
        }
        Sphere sphere = new Sphere(radio);
        sphere.setId("sphere"+contadorS++);
        color(sphere,colorValue);
        posicion(sphere,x,y,30);
        sphere.setCullFace(CullFace.BACK);
        root.getChildren().add(sphere);
        ultimaFigura = sphere;
        sphere.setOnMousePressed((e) ->{
            if(borrar){
                    subScene.setCursor(Cursor.DEFAULT);
                    borrar = false;
                    root.getChildren().remove(sphere);
            }     
            if(seleccion){
                shape3d = sphere;
                hover();      
            }
        });
    }

    /**
     *
     * @param x
     * @param y
     * @param radio
     * @param colorValue
     */
    public void addCylinder(double x, double y, double radio, Color colorValue){
        if(drag){
            contadorC = contadorC-1;
            String id = "#cylinder"+contadorC;
            root.getChildren().remove(root.lookup(id));
        }
        Cylinder cylinder = new Cylinder(radio,radio*2); 
        cylinder.setId("cylinder"+contadorC++);
        color(cylinder,colorValue);
        posicion(cylinder,x,y,300);
        root.getChildren().add(cylinder);
        ultimaFigura = cylinder;
        cylinder.setOnMousePressed((e) ->{
            if(borrar){
                    subScene.setCursor(Cursor.DEFAULT);
                    borrar = false;
                    root.getChildren().remove(cylinder);
             }   
            if(seleccion && shape3d == null){
                shape3d = cylinder;
                hover(); 
            }
        }); 
    }

    /**
     *
     * @param x
     * @param y
     * @param h
     * @param colorValue
     */
    public void addPyramid(double x, double y, float h, Color colorValue){
        if(drag){
            contadorP = contadorP -1;
            String id = "#pyramid"+contadorP;
            root.getChildren().remove(root.lookup(id));
        }
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
    posicion(pyramid,x,y,30);
    root.getChildren().add(pyramid);
    ultimaFigura = pyramid;
    pyramid.setOnMousePressed((e) ->{
            if(borrar){
                    subScene.setCursor(Cursor.DEFAULT);
                    borrar = false;
                    root.getChildren().remove(pyramid);
                }   
            if(seleccion && shape3d == null){
                shape3d = pyramid;
                hover();         
            }
        });

    }
     
    /**
     *
     * @return
     */
    public Shape3D getPyramid(){
        int aux = contadorP-1;
        return (Shape3D) root.lookup("#"+"pyramid"+aux);
    } 
    
    /**
     *
     * @return
     */
    public Shape3D getBox(){
        int aux = contadorB-1;
        return (Shape3D) root.lookup("#"+"box"+aux);
    }

    /**
     *
     * @return
     */
    public Shape3D getSphere(){
        int aux = contadorS-1;
        return (Shape3D) root.lookup("#"+"sphere"+aux);
    }

    /**
     *
     * @return
     */
    public Shape3D getCylinder(){
        int aux = contadorC-1;
        return (Shape3D) root.lookup("#"+"cylinder"+aux);
    }

    /**
     *
     * @return
     */
    public Shape3D getActualFigura(){
        return shape3d;
    }

    /**
     *
     * @return
     */
    public String getNombreActualFigura(){
        return actualFigura;
    }

    /**
     *
     * @return
     */
    public Shape3D getLast(){
        return ultimaFigura;
    }
   
    /**
     *
     */
    public void clear(){
        root.getChildren().clear();
    }
    
    /**
     *
     */
    public void noSeleccion(){
        if(shape3d != null){
            System.out.println("no seleccion");
        PhongMaterial b = (PhongMaterial) shape3d.getMaterial();
        b.setBumpMap(null);
        shape3d = null; 
        }
    }
    
    /**
     *
     * @param shape3d
     * @param x
     * @param y
     * @param z
     */
    public void posicion(Shape3D shape3d, double x, double y, double z) {
        if (shape3d != null) {
            shape3d.setTranslateX(x);
            shape3d.setTranslateY(y);
            shape3d.setTranslateZ(z);
        }
    }
    
    /**
     *
     * @param shape3d
     * @param anguloX
     * @param anguloY
     * @param anguloZ
     */
    public void setRotacion(Shape3D shape3d,double anguloX, double anguloY, double anguloZ){
        System.out.println("rotacion enable");
        Rotate rx = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
        Rotate ry = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        Rotate rz = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
        if (shape3d != null) {
            rx.setAngle(anguloX);
            ry.setAngle(anguloY);
            rz.setAngle(anguloZ);
            shape3d.getTransforms().addAll(rx, ry, rz);
        }
    }
    
    /**
     *
     * @param shape3d
     * @param colorValue
     */
    public void color(Shape3D shape3d, Color colorValue){
        if(shape3d != null){
            PhongMaterial b = new PhongMaterial();
            b.setDiffuseColor(colorValue);
            shape3d.setMaterial(b);
        }
    }
    
    /**
     *
     */
    public void hover() {
        PhongMaterial b = (PhongMaterial) shape3d.getMaterial();
        root.getChildren().remove(shape3d);
        Image imageBox = new Image(getClass().getResourceAsStream("images.jpg"));
        b.setBumpMap(imageBox);
        root.getChildren().add(shape3d);
    }
   
    /**
     *
     */
    public void cursorBorrador(){
       try{
           Image borrador = new Image(getClass().getResourceAsStream("borrador.png"));
           subScene.setCursor(new ImageCursor(borrador,
           borrador.getWidth()/2,
           borrador.getHeight()/2));
       }catch(Exception e){
           
       }
   }
}
