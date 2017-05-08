package game;

import game.Game.GAMESTATE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class LevelEditor {

    private final MouseInput mouseInput;
    private final Game game;
    private final FileHandler fHandler;
    private Object[] level;

    private enum dObject {
        Air, Player, Basic_Block;
    }
    private dObject object;

    public LevelEditor(MouseInput mouseInput, Game game, FileHandler fileHandler) {
        this.mouseInput = mouseInput;
        this.game = game;
        fHandler = fileHandler;
        level = fHandler.getLevel(0);
        object = dObject.Air;
    }

    public void tick() {
        if (mouseInput.clicked) {
            if (mouseInput.y < 560) {
                int index = (mouseInput.x / 20) + ((mouseInput.y / 20) * 30);
                switch (object) {
                    case Air:
                        level[index] = 0;
                        break;
                    case Player:
                        level[index] = 1;
                        break;
                    case Basic_Block:
                        level[index] = 2;
                        break;
                    default:
                        break;
                }
            }
            for (int i = 0; i < 3; i++) {
                if (new Rectangle(i * 20, 560, 20, 20).contains(mouseInput.getMousePos())) {
                    System.out.print(i);
                    switch (i) {
                        case 0:
                            object = dObject.Air;
                            break;
                        case 1:
                            object = dObject.Player;
                            break;
                        case 2:
                            object = dObject.Basic_Block;
                            break;
                        default:
                    }
                }
            }
            if (new Rectangle(530, 562, 65, 36).contains(mouseInput.getMousePos())) {
                game.gameState = GAMESTATE.MENU;
                game.level = level;
                fHandler.saveToFile(level);
            }
            mouseInput.clicked = false;
        }
    }

    public void render(Graphics g) {
        for (int index = 0; index < level.length; index++) {
            int x = (index % 30) * 20;
            int y = (index / 30) * 20;
            switch ((int) level[index]) {
                case 1:
                    g.setColor(Color.red);
                    g.fillOval(x, y, 20, 20);
                    break;
                case 2:
                    g.setColor(Color.green);
                    g.fillRect(x, y, 20, 20);
                    break;
                default:
                    break;
            }
        }
        g.setColor(new Color(150, 150, 150));
        g.fillRoundRect(530, 562, 65, 36, 8, 8);
        g.setColor(new Color(0, 0, 0));
        g.drawRoundRect(530, 562, 65, 36, 8, 8);
        g.drawString("Back", 550, 585);
        g.setColor(Color.red);
        g.fillOval(20, 560, 20, 20);
        g.setColor(Color.green);
        g.fillRect(40, 560, 20, 20);
        for (int i = 0; i < 30; i++) {
            g.setColor(new Color(100, 100, 100));
            g.drawLine(i * 20 - 1, 0, i * 20 - 1, 559);
            if (i < 29) {
                g.drawLine(0, i * 20 - 1, 600, i * 20 - 1);
            }
        }

    }
}
