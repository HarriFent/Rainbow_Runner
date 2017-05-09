package game;

import game.Game.GAMESTATE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Menu {

    private MenuButton btn1, btn2, btn3;
    private MouseInput mouseInput;
    private Game game;

    public Menu(MouseInput mouseInput, Game game) {
        this.mouseInput = mouseInput;
        this.game = game;
        btn1 = new MenuButton(10, 40, "Play Game");
        btn2 = new MenuButton(10, 75, "Level Editor");
        btn3 = new MenuButton(10, 110, "Exit");
    }

    public void tick() {
        btn1.mouseOver = false;
        btn2.mouseOver = false;
        btn3.mouseOver = false;
        if (btn1.getBound().contains(mouseInput.getMousePos())) {
            btn1.mouseOver = true;
            if (mouseInput.clicked == true) {
                game.gameState = GAMESTATE.GAME;
                Player p = new Player(200, 100, ID.Player, game.handler, game);
                game.handler.addObject(p);
                for (int i = 0; i < game.level.length; i++) {
                    switch ((int) game.level[i]) {
                        case 1:
                            p.setx((i % 30) * 20);
                            p.sety((i / 30) * 20);
                            break;
                        case 2:
                            game.handler.addObject(new Block((i % 30) * 20, (i / 30) * 20, ID.BasicBlock, game.handler, game));
                            break;
                        case 3:
                            game.handler.addObject(new Portal((i % 30) * 20, (i / 30) * 20, ID.PortalBlock, game.handler, game));
                            break;
                        case 4:
                            game.handler.addObject(new ExplodeBlock((i % 30) * 20, (i / 30) * 20, ID.ExplodeBlock, game.handler, game));
                        default:
                            break;
                    }
                }
            }
        }
        if (btn2.getBound().contains(mouseInput.getMousePos())) {
            btn2.mouseOver = true;
            if (mouseInput.clicked == true) {
                game.gameState = GAMESTATE.LEVELEDITOR;
            }
        }
        if (btn3.getBound().contains(mouseInput.getMousePos())) {
            btn3.mouseOver = true;
            if (mouseInput.clicked == true) {
                System.exit(0);
            }
        }
    }

    public void render(Graphics g) {
        btn1.drawButton(g);
        btn2.drawButton(g);
        btn3.drawButton(g);
    }
}

class MenuButton {

    private int x, y, w, h;
    private String label;
    public boolean mouseOver = false;

    public MenuButton(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.w = 200;
        this.h = 30;
        this.label = text;
    }

    public void drawButton(Graphics g) {
        if (mouseOver) {
            g.setColor(new Color(150, 150, 150));
            g.fillRoundRect(x, y, w + 20, h, 10, 10);
            g.setColor(new Color(0, 0, 0));
            g.drawRoundRect(x, y, w + 20, h, 10, 10);
            g.setColor(new Color(255, 255, 255));
            g.drawString(label, x + 5, y + 20);
        } else {
            g.setColor(new Color(200, 200, 200));
            g.fillRoundRect(x, y, w, h, 10, 10);
            g.setColor(new Color(0, 0, 0));
            g.drawRoundRect(x, y, w, h, 10, 10);
            g.drawString(label, x + 5, y + 20);
        }
    }

    public Rectangle getBound() {
        return new Rectangle(x, y, w, h);
    }
}
