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
import javafx.scene.shape.Line;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
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
    Shape shape2d;
    String actualFigura="";
    boolean borrar = false;
    boolean drag = false;
    Group root = new Group();
    double startX = -1, startY = -1;
    Color color2d;
    private SubScene subScene;

    int contadorS = 0, contadorLine = 0,contadorPrismaRec = 0, contadorB = 0, contadorP = 0, contadorC = 0, contadorRec = 0;
    boolean seleccion = false;

    /**
     * Constructor que tiene como objetivo establecer la subScene y camára que
     * será usada para la creación de los objetos 3d.
     *
     * @param subScene contiene la subscene donde se dibujaran las figuras
     */
    public paint(SubScene subScene,double x,double y) {
        setSubScene(subScene);
    }

    /**
     * Constructor que tiene como objetivo crear la instancia de la clase Paint
     */
    public paint() {

    }

    /**
     * Método que establece la subScene con la cámara
     *
     * @param subScene
     */
    public void setSubScene(SubScene subScene) {
        this.subScene = subScene;
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(20000.0);
        camera.setTranslateZ(-1000);
        camera.setFieldOfView(35);
        camera.setLayoutX(subScene.getWidth()/2.1);
        camera.setLayoutY(subScene.getHeight()/2);
        subScene.setCamera(camera);
        subScene.setRoot(root);

    }

    /**
     * Método que determina si esta activada la función borrador. Su valor
     * default es false.
     *
     * @param borrar variable que determina si la opcion de borrar esta
     * habilitada
     */
    public void setBorrador(boolean borrar) {
        cursorBorrador();
        this.borrar = borrar;
    }

    /**
     * Método que determina si la función de seleccion esta habilitada
     *
     * @param seleccion variable que determina si la opcion de seleccionar esta
     * habilitada
     */
    public void setSeleccion(boolean seleccion) {
        this.seleccion = seleccion;
    }

    /**
     * Método que consigue el valor de la opción seleccionar. Su valor default
     * es false.
     *
     * @return regresa el valor booleano de la variable seleccion
     */
    public boolean getSeleccion() {
        return seleccion;
    }

    /*
     * @param Color asignado a la figura 2d
     */
    public void setColor2d(Color colorValue) {
        if(shape2d != null){
            if(shape2d.getId().matches(".*rec.*")){
             shape2d.setFill(colorValue);
            }else{
                shape2d.setStroke(colorValue);
            }
        }
    }
    /**
     *
     * @param shape3d figura que se le aplicará el color
     * @param colorValue color que se le aplicará a la figura
     */
    public void color(Shape3D shape3d, Color colorValue) {
        if (shape3d != null) {
            PhongMaterial b = new PhongMaterial();
            b.setDiffuseColor(colorValue);
            shape3d.setMaterial(b);
        }
        
    }

    /**
     * Método que establece el drag. El valor default es false.
     *
     * @param drag variable que indica si la figura será dibujada en drag
     */
    public void setDragging(boolean drag) {
        this.drag = drag;
    }

    /**
     * Método que establece el drag. El valor default es false.
     *
     * @return regresa el valor booleano del dragging. True significa que se
     * activo el dragging mientras que False lo contrario.
     */
    public boolean getDragging() {
        return drag;
    }

    /**
     * Método que establece la figura. Sólo se permite: cylinder (cilindro), box(cubo), pyramid(piramide),
     * sphere(esfera), rec(rectangulo), line(linea), prismaRec(prisma rectangular)
     *
     * @param figura variable que recibe el nombre de la figura
     */
    public void setActualFigura(String figura) {
        subScene.setCursor(Cursor.DEFAULT);
        actualFigura = figura;
    }

    /**
     * Método que aumenta el contador de la figura actual para poder crear una
     * nueva figura
     */
    public void setNew() {
        subScene.setCursor(Cursor.DEFAULT);
        switch (actualFigura) {
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
            case "prismaRec":
                contadorPrismaRec++;
                break;
        }
    }

    /**
     * Método que añade un cubo a la subScene
     *
     * @param x posición en el eje X
     * @param y posición en el eje Y
     * @param tam tamaño que tendrá el cubo
     * @param colorValue
     */
    public void addBox(double x, double y, double tam, Color colorValue) {
        System.out.println("cubo");
        if (drag) {
            contadorB = contadorB - 1;
            String id = "#box" + contadorB;
            root.getChildren().remove(root.lookup(id));
        }
        Box box = new Box(tam, tam, tam);
        box.setId("box" + contadorB++);
        posicion(box, x, y, 0);
        color(box, colorValue);
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
     * Método que añade un cubo a la subScene
     *
     * @param x posición en el eje X
     * @param y posición en el eje Y
     * @param tam tamaño que tendrá el cubo
     * @param colorValue
     */
    public void addPrismaRec(double x, double y, double tamX, double tamY,double tamZ, Color colorValue) {
        System.out.println("prisma");
        if (drag) {
            contadorPrismaRec = contadorPrismaRec - 1;
            String id = "#prismaRec" + contadorPrismaRec;
            root.getChildren().remove(root.lookup(id));
        }
        Box prisma = new Box(tamX, tamY, tamZ);
        prisma.setId("prismaRec" + contadorPrismaRec++);
        posicion(prisma, x, y, 0);
        color(prisma, colorValue);
        prisma.setCullFace(CullFace.BACK);
        root.getChildren().add(prisma);
        ultimaFigura = prisma;
        prisma.setOnMousePressed((e) -> {
            if (borrar) {
                subScene.setCursor(Cursor.DEFAULT);
                borrar = false;
                root.getChildren().remove(prisma);
            }
            if (seleccion && shape3d == null) {
                shape3d = prisma;
                System.out.println(shape3d);
                hover();
            }
        });

    }

    /**
     *
     * @param x posición en el eje X
     * @param y posición en el eje Y
     * @param radio el radio de la figura
     * @param colorValue valor de color que tendrá la figura
     */
    public void addSphere(double x, double y, double radio, Color colorValue) {
        if (drag) {
            contadorS = contadorS - 1;
            String id = "#sphere" + contadorS;
            root.getChildren().remove(root.lookup(id));
        }
        Sphere sphere = new Sphere(radio);
        sphere.setId("sphere" + contadorS++);
        color(sphere, colorValue);
        posicion(sphere, x, y, 0);
        sphere.setCullFace(CullFace.BACK);
        root.getChildren().add(sphere);
        ultimaFigura = sphere;
        sphere.setOnMousePressed((e) -> {
            if (borrar) {
                subScene.setCursor(Cursor.DEFAULT);
                borrar = false;
                root.getChildren().remove(sphere);
            }
            if (seleccion) {
                shape3d = sphere;
                hover();
            }
        });
    }

    /**
     *
     * @param x posición en el eje X
     * @param y posición en el eje Y
     * @param radio radio que tendrá la base de la figura
     * @param colorValue valor de color que tendrá la figura
     */
    void addCylinder(double x, double y, double radio, Color colorValue) {
        if (drag) {
            contadorC = contadorC - 1;
            String id = "#cylinder" + contadorC;
            root.getChildren().remove(root.lookup(id));
        }
        Cylinder cylinder = new Cylinder(radio, radio * 2);
        cylinder.setId("cylinder" + contadorC++);
        color(cylinder, colorValue);
        posicion(cylinder, x, y, 0);
        root.getChildren().add(cylinder);
        ultimaFigura = cylinder;
        cylinder.setOnMousePressed((e) -> {
            if (borrar) {
                subScene.setCursor(Cursor.DEFAULT);
                borrar = false;
                root.getChildren().remove(cylinder);
            }
            if (seleccion && shape3d == null) {
                shape3d = cylinder;
                hover();
            }
        });
    }

    /**
     *
     * @param x posición en el eje X
     * @param y posición en el eje Y
     * @param h altura que tendrá las figuras
     * @param colorValue color que tendrá la figura
     */
    public void addPyramid(double x, double y, float h, Color colorValue) {
        if (drag) {
            contadorP = contadorP - 1;
            String id = "#pyramid" + contadorP;
            root.getChildren().remove(root.lookup(id));
        }
        TriangleMesh pyramidMesh = new TriangleMesh();
        pyramidMesh.getTexCoords().addAll(0, 0);
        //h = 175;                    // Height
        float s = h / 2;                    // Side
        pyramidMesh.getPoints().addAll(
                0, 0, 0, // Point 0 - Top
                0, h, -s, // Point 1 - Front
                -s, h, 0, // Point 2 - Left
                s, h, 0, // Point 3 - Back
                0, h, s // Point 4 - Right
        );
        pyramidMesh.getFaces().addAll(
                0, 0, 2, 0, 1, 0, // Front left face
                0, 0, 1, 0, 3, 0, // Front right face
                0, 0, 3, 0, 4, 0, // Back right face
                0, 0, 4, 0, 2, 0, // Back left face
                4, 0, 1, 0, 2, 0, // Bottom rear face
                4, 0, 3, 0, 1, 0 // Bottom front face
        );
        MeshView pyramid = new MeshView(pyramidMesh);
        pyramid.setId("pyramid" + contadorP++);
        pyramid.setCullFace(CullFace.NONE);
        posicion(pyramid, x, y, 0);
        root.getChildren().add(pyramid);
        ultimaFigura = pyramid;
        pyramid.setOnMousePressed((e) -> {
            if (borrar) {
                subScene.setCursor(Cursor.DEFAULT);
                borrar = false;
                root.getChildren().remove(pyramid);
            }
            if (seleccion && shape3d == null) {
                shape3d = pyramid;
                hover();
            }
        });

    }

    public void addRec(double x, double y, double w, double h, Color colorValue) {
        if (drag) {
            contadorRec = contadorRec - 1;
            String id = "#rec" + contadorRec;
            root.getChildren().remove(root.lookup(id));
        }
        Rectangle r = new Rectangle();
        r.setId("rec" + contadorRec++);
        r.setFill(colorValue);
        r.setX(x);
        r.setY(y);
        r.setWidth(w - 5);
        r.setHeight(h - 5);
        root.getChildren().add(r);
        r.setOnMousePressed((e) -> {
            if (borrar) {
                borrar = false;
                root.getChildren().remove(r);
            }
            if (seleccion && shape2d==null) {
                shape2d = r;
            }
        });
    }
    
    public void addLine(double x, double y, double x2, double y2, Color colorValue) {
        if (drag) {
            contadorLine = contadorLine - 1;
            String id = "#line" + contadorLine;
            root.getChildren().remove(root.lookup(id));
        }

        Line l = new Line();
        l.setId("line" + contadorLine++);
        l.setStartX(x);
        l.setStartY(y);
        l.setEndX(x2);
        l.setEndY(y2);
        l.setStrokeWidth(10);
        l.setStroke(colorValue);
        l.setOnMousePressed((e) -> {
            if (seleccion) {
                shape2d = l;
            }
            if (borrar) {
                subScene.setCursor(Cursor.DEFAULT);
                borrar = false;
                root.getChildren().remove(l);
            }
        });
        root.getChildren().add(l);
    }

    /**
     *
     * @return Regresa la última piramide creada
     */
    public Shape3D getPyramid() {
        int aux = contadorP - 1;
        return (Shape3D) root.lookup("#" + "pyramid" + aux);
    }

    /**
     *
     * @return regresa el último cubo creado
     */
    public Shape3D getBox() {
        int aux = contadorB - 1;
        return (Shape3D) root.lookup("#" + "box" + aux);
    }

     /**
     *
     * @return regresa el último prisma rectangular creado
     */
    public Shape3D getPrismaRec() {
        int aux = contadorPrismaRec - 1;
        return (Shape3D) root.lookup("#" + "prismaRec" + aux);
    }
    /**
     *
     * @return regresa la última esfera creada
     */
    public Shape3D getSphere() {
        int aux = contadorS - 1;
        return (Shape3D) root.lookup("#" + "sphere" + aux);
    }

    /**
     *
     * @return regresa el último cilindro creado
     */
    public Shape3D getCylinder() {
        int aux = contadorC - 1;
        return (Shape3D) root.lookup("#" + "cylinder" + aux);
    }

    /**
     *
     * @return regresa la actual figura 3D. Esta puede ser la figura
     * seleccionada.
     */
    public Shape3D getActualFigura() {
        return shape3d;
    }

    /**
     *
     * @return regresa el nombre de la figura actual
     */
    public String getNombreActualFigura() {
        return actualFigura;
    }

    /**
     *
     * @return regresa la última figura creada
     */
    public Shape3D getLast() {
        return ultimaFigura;
    }

    /**
     * Borra todas las figuras de la subScene
     */
    public void clear() {
        root.getChildren().clear();
    }

    /**
     * Deshabilita la figura seleccionada
     */
    public void noSeleccion() {
        if (shape3d != null) {
            PhongMaterial b = (PhongMaterial) shape3d.getMaterial();
            b.setBumpMap(null);
            shape3d = null;
        }
        shape2d = null;
    }

    /**
     *
     * @param shape3d Figura 3D que se le aplicará la posicion
     * @param x posición en el eje X
     * @param y posición en el eje Y
     * @param z posición en el eje Z
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
     * @param shape3d Figura3d que se le aplicará la rotación
     * @param anguloX angulo respecto al eje X
     * @param anguloY angulo respecto al eje Y
     * @param anguloZ angulo respecto al eje Z
     */
    public void setRotacion(Shape3D shape3d, double anguloX, double anguloY, double anguloZ) {
        if(!actualFigura.equals("line") && !actualFigura.equals("rec")){
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
    }

    

    /**
     * Método que resalta la figura seleccionada
     */
    public void hover() {
        PhongMaterial b = (PhongMaterial) shape3d.getMaterial();
        root.getChildren().remove(shape3d);
        Image imageBox = new Image(getClass().getResourceAsStream("images.jpg"));
        b.setBumpMap(imageBox);
        root.getChildren().add(shape3d);
    }

    /**
     * Método que pone el cursor para borrar
     */
    public void cursorBorrador() {
        try {
            Image borrador = new Image(getClass().getResourceAsStream("borrador.png"));
            subScene.setCursor(new ImageCursor(borrador,
                    borrador.getWidth() / 2,
                    borrador.getHeight() / 2));
        } catch (Exception e) {

        }
    }
}
