
package gui;


import entities.Entity;
import utilities.Vec2D;

import java.util.Vector;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Renderer extends JPanel implements MouseListener{

    private final int            WIDTH, HEIGHT;
    private       Vector<Entity> entityVector;

    private       boolean        mouseIsPressed = false;
    private       int            mouseX, mouseY;


    public Renderer(String title, int width, int height){
    
        WIDTH = width;
        HEIGHT = height;
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.GRAY);

        JFrame window = new JFrame(title);
        window.add(this);
        window.pack();
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        addMouseListener(this);

        entityVector = new Vector<Entity>();
    }


    public void add_entity(Entity entity){
        entityVector.add(entity);
    }


    public void clear_entities(){
        entityVector.clear();
    }


    public void paint(Graphics g){
        
        super.paint(g);

        Graphics2D g2D = (Graphics2D) g;
        
        g2D.setColor(Color.RED);

        for (Entity entity : entityVector){
            try{
                g2D.setColor(Color.decode(entity.get_colorTag()));
            }
            catch(Exception e){
                g2D.setColor(Color.RED);
            }

            double x = entity.get_position().get_x();
            double y = entity.get_position().get_y();

            g2D.fillOval((int) x - entity.get_radius(), HEIGHT - (int) y - entity.get_radius(), 2*entity.get_radius(), 2*entity.get_radius());
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }


    @Override
    public void mouseEntered(MouseEvent e) {}


    @Override
    public void mouseExited(MouseEvent e) {}


    @Override
    public void mousePressed(MouseEvent e) {
        mouseIsPressed = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }


    @Override
    public void mouseReleased(MouseEvent e){}


    public Vec2D get_mouse_position(){
        mouseIsPressed = false;
        return new Vec2D(mouseX, HEIGHT - mouseY);
    }
    

    public boolean mouse_is_pressed(){
        return mouseIsPressed;
    }
}