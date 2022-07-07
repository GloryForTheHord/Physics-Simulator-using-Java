
package gui;


import entities.Entity;

import java.util.Vector;

import java.awt.*;
import javax.swing.*;


public class Renderer extends JPanel{

    private final int            WIDTH, HEIGHT;
    private       Vector<Entity> entityVector;


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
}