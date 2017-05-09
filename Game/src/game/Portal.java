package game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

class Portal extends GameObject {

    private int aniTick = 0;

    public Portal(int x, int y, ID id, Handler handler, Game game) {
        super(x, y, id, handler, game);
    }

    @Override
    public void tick() {
        GameObject player = game.findPlayer();
        GameObject portal, portal2;
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).id == ID.PortalBlock) {
                portal = handler.object.get(i);
                for (int i2 = 0; i2 < handler.object.size(); i2++) {
                    if (handler.object.get(i2).id == ID.PortalBlock && i2 != i) {
                        portal2 = handler.object.get(i2);
                        if (player.getBounds().intersects(portal.getBounds()) && portal.collide) {
                            player.x = portal2.x;
                            player.y = portal2.y;
                            portal2.collide = false;
                        } else if (player.getBounds().intersects(portal2.getBounds()) && portal2.collide) {
                            player.x = portal.x;
                            player.y = portal.y;
                            portal.collide = false;
                        }

                        if (!player.getBounds().intersects(portal.getBounds())) {
                            portal.collide = true;
                        }
                        if (!player.getBounds().intersects(portal2.getBounds())) {
                            portal2.collide = true;
                        }
                    }
                }
            }

        }
    }

    @Override
    public void render(Graphics g) {
        aniTick++;
        if (aniTick == 900) {
            aniTick = 0;
        }
        int add = aniTick / 300;
        Graphics2D g2d = (Graphics2D) g;
        Composite ogComp = g2d.getComposite();
        AlphaComposite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g2d.setComposite(alphaComp);
        g2d.setColor(new Color(150, 0, 150));
        g2d.fillRect(x + add, y + add, w - (add * 2), h - (add * 2));
        g2d.setColor(new Color(110, 0, 110));
        g2d.fillRect(x + 3 + add, y + 3 + add, w - 6 - (add * 2), h - 6 - (add * 2));
        g2d.setColor(new Color(110, 0, 110));
        g2d.fillRect(x + 6 + add, y + 6 + add, w - 12 - (add * 2), h - 12 - (add * 2));
        g2d.setComposite(ogComp);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

}
