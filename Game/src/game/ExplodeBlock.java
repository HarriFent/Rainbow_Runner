package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

class ExplodeBlock extends Block {

    private float alpha;
    private boolean explode;
    private int aniTick;

    public ExplodeBlock(int x, int y, ID id, Handler handler, Game game) {
        super(x, y, id, handler, game);
        color = new Color(0, 0, 150);
        alpha = 1.0f;
    }

    @Override
    public void tick() {
        GameObject player = game.findPlayer();
        int i = cHandler.getTouchedBlockIndex();
        alpha = (float) (1.0 - (color.getBlue() / 255.0));
        if (color.getBlue() == 0) {
            alpha = 0.0f;
        }
        if (i >= 0 && index == i) {
            int cInt1 = handler.object.get(i).color.getBlue();
            if (player.d2y == 0 && index == i && cInt1 < 255) {
                cInt1 += 2;
                cInt1 = Game.clamp(cInt1, 0, 255);
                handler.object.get(i).color = new Color(0, 0, cInt1);
                HUD.SCORE++;
                return;
            }
            if (cInt1 == 255 && index == i && collide == true) {
                collide = false;
                player.dy = -9;
                Random r = new Random();
                player.dx = (r.nextDouble() * 10) - 5;
                this.explode = true;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (!explode) {
            Graphics2D g2d = (Graphics2D) g;
            Composite ogComp = g2d.getComposite();
            AlphaComposite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
            g2d.setComposite(alphaComp);
            g2d.setColor(color);
            g2d.fillRect(x, y, 20, 20);
            g2d.setColor(new Color(0, 0, color.getBlue() - 50));
            g2d.fillRect(x, y + 15, 20, 5);
            g2d.setColor(Color.black);
            g2d.drawRect(x, y, 20, 20);
            g2d.setComposite(ogComp);
        } else {
            aniTick++;
            if (aniTick == 300) {
                aniTick = 0;
                explode = false;
            }
            int add = aniTick / 100;
            g.setColor(color);
            switch (add) {
                case 0:
                    g.drawOval(x+5, y+5, 10, 10);
                    break;
                case 1:
                    g.drawOval(x, y, 20, 20);
                    break;
                case 2:
                    g.drawOval(x-5, y-5, 30, 30);
                    break;
                default:
            }
        }
    }
}
