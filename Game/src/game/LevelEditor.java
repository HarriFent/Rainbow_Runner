package game;

import game.Game.GAMESTATE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

public class LevelEditor {

    private MouseInput mouseInput;
    private Game game;
    private FileHandler fHandler;
    private short[] level;

    LevelEditor(MouseInput mouseInput, Game game, FileHandler fileHandler) {
        this.mouseInput = mouseInput;
        this.game = game;
        fHandler = fileHandler;
        level = new short[300];
    }

    public void tick() {
        ArrayList<Short> arrayListLevel;
        arrayListLevel = fHandler.levels.get(0);
        level = new short[arrayListLevel.size()];
        for (int i = 0; i < arrayListLevel.size(); i++){
            level[i] = arrayListLevel.get(i);
        }   
        if (mouseInput.clicked && mouseInput.y < 580) {
            int index = (mouseInput.x / 20) + ((mouseInput.y / 20) * 30);
            if (level[index] == 0) {
                level[index] = 1;
            } else if (level[index] == 1) {
                level[index] = 0;
            }
            mouseInput.clicked = false;
        }
        Rectangle r = new Rectangle(530, 582, 65, 16);
        if (mouseInput.clicked == true && r.contains(mouseInput.getMousePos())){
            game.gameState = GAMESTATE.MENU;
            game.level = level;
            fHandler.saveToFile(level);
        }
    }

    public void render(Graphics g) {
        for (int index = 0; index < level.length; index++) {
            if (level[index] == 1) {
                int x = (index % 30)*20;
                int y = (index / 30)*20;
                g.setColor(Color.green);
                g.fillRect(x, y, 20, 20);
            }
        }
        g.setColor(new Color(150, 150, 150));
        g.fillRoundRect(530, 582, 65, 16, 8, 8);
        g.setColor(new Color(0, 0, 0));
        g.drawRoundRect(530, 582, 65, 16, 8, 8);
        g.drawString("Back",550,595);
        for (int i = 0; i < 30; i++) {
            g.setColor(new Color(100, 100, 100));
            g.drawLine(i * 20 - 1, 0, i * 20 - 1, 579);
            g.drawLine(0, i * 20 - 1, 810, i * 20 - 1);
        }
        
    }
}
