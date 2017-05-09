package game;

import game.Game.GAMESTATE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.text.DecimalFormat;

public class Player extends GameObject {

    private static final int xVelLimit = 8, yVelLimit = 10;
    private boolean onGround = false;
    private CollisionHandler cHandler;

    private enum STATE {
        IDLE, SHORT, TALL, ABOVEARROW, LEFTARROW, RIGHTARROW;
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
            if (d2x == 0.0) {
                if (Double.valueOf(new DecimalFormat(".##").format(dx)) > 0.1) {
                    dx = dx - 0.2;
                } else if (Double.valueOf(new DecimalFormat(".##").format(dx)) < -0.1) {
                    dx = dx + 0.2;
                } else {
                    dx = 0.0;
                }
            }

            //left and right check
            if (cHandler.checkLeft() && dx < 0) {
                dx = 0;
                d2x = 0;
                x = ((x + 10) / 20) * 20;
            } else if (cHandler.checkRight() && dx > 0) {
                dx = 0;
                d2x = 0;
                x = ((x + 10) / 20) * 20;
            }

            //check off screen
            if (y <= 0) {
                state = STATE.ABOVEARROW;
            } else if (x >= 600) {
                state = STATE.RIGHTARROW;
            } else if (x <= -20) {
                state = STATE.LEFTARROW;
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

            //above check
            if (cHandler.checkAbove() && d2y < 1) {
                d2y = 1;
                dy = 0;
                y = ((y / 20) * 20) + h;
            }

            if (stateTick > 0 && state != STATE.IDLE) {
                stateTick--;
            } else if (x > -20 && x < 600 && y > 0) {
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
        int[] xpoints;
        int[] ypoints;
        Polygon p;
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
            case ABOVEARROW:
                g.setColor(new Color(220, 0, 0));
                int x2 = x;
                if (x2 < 5) {
                    x2 = 5;
                } else if (x2 > 575) {
                    x2 = 575;
                }
                xpoints = new int[]{10 + x2, 20 + x2, 13 + x2, 13 + x2, 7 + x2, 7 + x2, x2};
                ypoints = new int[]{5, 15, 15, 35, 35, 15, 15};
                p = new Polygon(xpoints, ypoints, 7);
                g.fillPolygon(p);
                g.setColor(Color.black);
                g.drawPolygon(p);
                break;
            case LEFTARROW:
                g.setColor(new Color(220, 0, 0));
                xpoints = new int[]{35, 15, 15, 5, 15, 15, 35};
                ypoints = new int[]{7 + y, 7 + y, y, 10 + y, 20 + y, 13 + y, 13 + y};
                p = new Polygon(xpoints, ypoints, 7);
                g.fillPolygon(p);
                g.setColor(Color.black);
                g.drawPolygon(p);
                break;
            case RIGHTARROW:
                g.setColor(new Color(220, 0, 0));
                xpoints = new int[]{565, 585, 585, 595, 585, 585, 565};

                ypoints = new int[]{7 + y, 7 + y, y, 10 + y, 20 + y, 13 + y, 13 + y};
                p = new Polygon(xpoints, ypoints, 7);
                g.fillPolygon(p);
                g.setColor(Color.black);
                g.drawPolygon(p);
                break;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, w, h);
    }

}
