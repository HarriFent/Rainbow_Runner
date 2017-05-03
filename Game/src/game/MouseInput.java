package game;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MouseInput extends MouseAdapter{
    
    public int x,y;
    public boolean clicked = false;
    
    @Override
    public void mousePressed(MouseEvent e) {
        clicked = true;
    }@Override
    public void mouseReleased(MouseEvent e) {
        clicked = false;
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }    
    
    public Point getMousePos() {
        return new Point(x,y);
    }
}
