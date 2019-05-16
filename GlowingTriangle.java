package homework2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author Prakash Dahal 02/28/2017
 */
public class GlowingTriangle extends JComponent {

    private final Polygon triangle = new Polygon();
    private Timer timer;
    private static Color triangleColor;
    private boolean draggingMouse = false;
    private int cordX1;
    private int cordX2;
    private int cordX3;
    private int cordY1;
    private int cordY2;
    private int cordY3;
    private int dragX;
    private int dragY;
    private int clickCount = 0;
    private int count = 20;

    public GlowingTriangle() {
        initTimer();
        initMouseListener();
    }

    private void initMouseListener() {
        class Listener extends MouseAdapter {

            @Override
            public void mousePressed(MouseEvent e) {
                draggingMouse = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                draggingMouse = true;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickCount > 3) {
                    System.out.println("No more click Please");
                } else {
                    clickCount++;
                    if (clickCount == 1) {
                        cordX1 = e.getX();
                        cordY1 = e.getY();
                        triangle.addPoint(cordX1, cordY1);
                    }
                    if (clickCount == 2) {
                        cordX2 = e.getX();
                        cordY2 = e.getY();
                        triangle.addPoint(cordX2, cordY2);
                    }
                    if (clickCount == 3) {
                        cordX3 = e.getX();
                        cordY3 = e.getY();
                        triangle.addPoint(cordX3, cordY3);
                    }
                }
                ////same thing as above.

//                if (triangle.npoints < 3) {       
//                    triangle.addPoint(e.getX(), e.getY());
//                }    if (triangle.npoints == 3) { 
//                    timer.start();
//                }
                draggingMouse = false;
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                dragX = e.getX();
                dragY = e.getY();
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                repaint();
            }
        }
        MouseAdapter listener = new Listener();
        addMouseListener(listener);
        addMouseMotionListener(listener);
    }

    private void initTimer() {
        class TimerListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {

                boolean alphaValue = false;

                if (count >= 20 && count < 200) {
                    alphaValue = true;
                }
                if (clickCount == 3) {
                    if (alphaValue == true) {
                        count++;
                       // System.out.println(count);
                    } else {
                        count--;
                    }
                }
            }
        }
        final int DELAY = 10;  // milliseconds between events
        timer = new Timer(DELAY, new TimerListener());
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.addRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle(getWidth(), getWidth()));
        // g2.setColor(Color.red);
        triangleColor = new Color(count, 0, 0);
        g2.setColor(triangleColor);
        if (clickCount == 3) {
            // g2.fill(triangle);
            g2.fillPolygon(triangle);
            repaint();
        }
        if (draggingMouse) {
            g2.setColor(triangleColor);
            g2.fillPolygon(triangle);
            repaint();

            triangle.translate(dragX, dragY);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Glowing Triangle");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(new GlowingTriangle());
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);
    }
}

