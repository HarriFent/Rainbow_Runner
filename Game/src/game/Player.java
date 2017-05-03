package game;

import game.Game.GAMESTATE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Player extends GameObject {

    private static final int xVelLimit = 8, yVelLimit = 10;
    private boolean onGround = false;
    private CollisionHandler cHandler;

    private enum STATE {
        IDLE, SHORT, TALL;
    }
    private STATE state = STATE.IDLE;

    public Player(int x, int y, ID id, Handler handler, Game game) {
        super(x, y, id, handler, game);
        cHandler = new CollisionHandler(handler, this);
    }

    @Override
    public void tick() {

        //change and limit x velocity
        if (game.gameState == GAMESTATE.GAME) {
            if (dx <= xVelLimit && dx >= -xVelLimit) {
                dx = dx + d2x;
                if (dx > xVelLimit) {
                    dx = xVelLimit;
                }
                if (dx < -xVelLimit) {
                    dx = -xVelLimit;
                }
            }
            //decrease x velocity
            if (d2x == 0) {
                if (dx > 0.1) {
                    dx = dx - 0.2;
                } else if (dx < 0.1) {
                    dx = dx + 0.2;
                } else {
                    dx = 0;
                }
            }
            //change and limit y velocity
            onGround = cHandler.checkOnGround();
            if (!onGround) {
                if (dy == -8) {
                    state = STATE.TALL;
                }
                if (dy < yVelLimit && dy > -yVelLimit) {
                    d2y = 0.3;
                }
                dy = dy + d2y;
                if (dy > yVelLimit) {
                    dy = yVelLimit;
                } else if (dy < -yVelLimit) {
                    dy = -yVelLimit;
                }
            } else if (dy > 0) {
                d2y = 0;
                dy = 0;
                y = handler.object.get(cHandler.getTouchedBlockIndex()).y - h;
                stateTick = 8;
                state = STATE.SHORT;
            }

            if (stateTick > 0 && state != STATE.IDLE) {
                stateTick--;
            } else {
                state = STATE.IDLE;
            }

            //change position
            x += dx;
            y += dy;

            //quick reset
            if (y > 600) {
                game.gameState = GAMESTATE.GAMEOVER;

            }
        }
    }

    @Override
    public void render(Graphics g) {
        switch (state) {
            case IDLE:
                g.setColor(new Color(220, 0, 0));
                g.fillOval(x, y, 20, 20);
                g.setColor(new Color(255, 150, 150));
                g.fillOval(x + 3, y + 3, 10, 10);
                g.setColor(new Color(220, 0, 0));
                g.fillOval(x + 4, y + 4, 15, 15);
                g.setColor(Color.black);
                g.drawOval(x, y, 20, 20);
                break;
            case SHORT:
                g.setColor(new Color(220, 0, 0));
                g.fillOval(x - 5, y + 10, 30, 10);
                g.setColor(new Color(255, 150, 150));
                g.fillOval(x - 2, y + 13, 15, 5);
                g.setColor(new Color(220, 0, 0));
                g.fillOval(x - 1, y + 12, 22, 7);
                g.setColor(Color.black);
                g.drawOval(x - 5, y + 10, 30, 10);
                break;
            case TALL:
                g.setColor(new Color(220, 0, 0));
                g.fillOval(x + 5, y - 10, 10, 30);
                g.setColor(new Color(255, 150, 150));
                g.fillOval(x + 8, y - 7, 5, 15);
                g.setColor(new Color(220, 0, 0));
                g.fillOval(x + 9, y - 6, 7, 22);
                g.setColor(Color.black);
                g.drawOval(x + 5, y - 10, 10, 30);
                break;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

}
