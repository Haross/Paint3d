/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrastrargrafico;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;


/**
 * Ejemplo de gráfico que se puede arrastrar con el ratón.
 *
 * @author Chuidiang
 *
  */
public class ArrastrarGrafico extends Canvas implements MouseMotionListener
{
    /**
   * serial uid
   */
  private static final long serialVersionUID = -4273648398171436938L;

  /** 
     * Posicion x del vértice superior izquierdo del rectángulo.
     */
    private int xRectangulo = 0;

    /** 
     * Posicion y del vértice superior izquierdo del rectángulo
     */
    private int yRectangulo = 0;

    /** 
     * Ancho del rectángulo
     */
    private int anchoRectangulo = 100;

    /** 
     * Alto del rectángulo
     */
    private int altoRectangulo = 100;

    /** 
     * Si actualmente se está arrastrando o no el rectángulo.
     */
    private boolean arrastrando = false;

    /** 
     * x en la que estaba anteriormente el ratón.
     */
    private int xAnteriorRaton;

    /** 
     * y en la que estaba anteriormente el ratón
     */
    private int yAnteriorRaton;

    /**
     * Crea un nuevo objeto ArrastrarGrafico.
     */
    public ArrastrarGrafico()
    {
        addMouseMotionListener(this);
    }

    /**
     * Para darle un tamaño por defecto al Canvas de dibujo
     *
     * @return Dimension por defecto.
     */
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(500, 500);
    }

    /**
     * Dibuja el rectángulo en la posición indicada por por xRectangulo e
     * yRectangulo.
     *
     * @param g Graphics con el que dibujar.
     */
    @Override
    public void paint(Graphics g)
    {
        g.setColor(Color.RED);
        g.fillRect(xRectangulo, yRectangulo, anchoRectangulo, altoRectangulo);
    }

    /**
     * Crea la ventana con el Canvas y lo visualiza
     *
     * @param args Se ignoran
     */
    public static void main(String[] args)
    {
        JFrame v = new JFrame("Arrastrar Grafico");
        ArrastrarGrafico c = new ArrastrarGrafico();
        v.getContentPane().add(c);
        v.pack();
        v.setVisible(true);
        v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Método al que se llama cuando se arrastra el ratón.
     * Se comprueba con el atributo arrastrando si está empezando el arrastre o
     * ya se esta en medio del mismo.
     * Si se comienza el arrastre, se guardan las coordenadas del ratón que
     * vienen en el evento MouseEvent y se cambia el valor del atributo arrastrando.
     * Si se está en medio de un arrastre, se calcula la nueva posición del
     * rectángulo y se llama al método repaint() para que se pinte.
     *
     * @param e Evento del ratón
     */
    public void mouseDragged(MouseEvent e)
    {
      // Si comienza el arrastre ...
        if (!arrastrando)
        {
          // ... y el ratón está dentro del rectángulo
            if (estaDentro(e))
            {
              // Se guardan las posiciones del ratón
                xAnteriorRaton = e.getX();
                yAnteriorRaton = e.getY();
                // y se marca que ha comenzado el arrastre.
                arrastrando = true;
            }
        }
        else
        {
          // Si ya había empezado el arrastre, se calculan las nuevas
          // coordenadas del rectángulo
            xRectangulo = (xRectangulo + e.getX()) - xAnteriorRaton;
            yRectangulo = (yRectangulo + e.getY()) - yAnteriorRaton;
            
            // Se guarda la posición del ratón para el siguiente cálculo
            xAnteriorRaton = e.getX();
            yAnteriorRaton = e.getY();
            
            // y se manda repintar el Canvas
            repaint();
        }
    }

    /**
     * Para ver si el ratón está dentro del rectángulo.
     * Si está dentro, puede comenzar el arrastre.
     *
     * @param e El evento de ratón
     *
     * @return true si el ratón está dentro del rectángulo
     */
    private boolean estaDentro(MouseEvent e)
    {
        if (
            (e.getX() > xRectangulo) &&
                (e.getX() < (xRectangulo + anchoRectangulo)) &&
                (e.getY() > yRectangulo) &&
                (e.getY() < (yRectangulo + altoRectangulo)))
        {
            return true;
        }

        return false;
    }

    /**
     * El ratón se mueve sin arrastrar. Se marca fin de arrastre.
     *
     * @param e E
     */
    public void mouseMoved(MouseEvent e)
    {
        arrastrando = false;
    }
}