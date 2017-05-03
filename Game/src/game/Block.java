package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends GameObject {

    private int cInt1 = 0, cInt2 = 255;
    private CollisionHandler cHandler;
    private float alpha;

    public Block(int x, int y, ID id, Handler handler, Game game) {
        super(x, y, id, handler, game);
        cHandler = new CollisionHandler(handler, game.findPlayer());
        color = new Color(0, 255, 0);
        alpha = 1.0f;
    }

    @Override
    public void tick() {
        GameObject player = game.findPlayer();
        int i = cHandler.getTouchedBlockIndex();
        alpha = (float) (color.getGreen() / 255.0);
        if (color.getGreen() == 0) {
            alpha = 0.0f;
        }
        if (i >= 0 && index == i) {
            cInt1 = handler.object.get(i).color.getRed();
            cInt2 = handler.object.get(i).color.getGreen();
            if (player.d2y == 0 && index == i && cInt1 < 255) {
                cInt1 += 2;
                cInt2 -= 2;
                cInt1 = Game.clamp(cInt1, 0, 255);
                cInt2 = Game.clamp(cInt2, 0, 255);
                handler.object.get(i).color = new Color(cInt1, cInt2, 0);
                HUD.SCORE++;
                return;
            }
            if (cInt1 == 255 && index == i) {
                collide = false;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Composite ogComp = g2d.getComposite();
        AlphaComposite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaComp);
        g2d.setColor(color);
        g2d.fillRect(x, y, 20, 20);
        Color color2;
        int col1, col2;
        if (color.getGreen() > 50) {
            col2 = color.getGreen() - 50;
        } else {
            col2 = 0;
        }
        if (color.getRed() > 50) {
            col1 = color.getRed() - 50;
        } else {
            col1 = 0;
        }
        color2 = new Color(col1, col2, 0);
        g2d.setColor(color2);
        g2d.fillRect(x, y + 15, 20, 5);
        g2d.setColor(Color.black);
        g2d.drawRect(x, y, 20, 20);
        g2d.setComposite(ogComp);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }
}
