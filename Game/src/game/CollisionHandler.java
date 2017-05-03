package game;

import java.awt.Rectangle;
import java.util.ArrayList;

public class CollisionHandler {

    private Handler handler;
    private GameObject player;
    private static final int collisionRange = 3;

    public CollisionHandler(Handler handler, GameObject player) {
        this.handler = handler;
        this.player = player;
    }

    public boolean checkOnGround() {
        for (GameObject i : handler.object) {
            if (i.id == ID.BasicBlock) {
                Rectangle r3 = i.getBounds();
                Rectangle r2 = new Rectangle(player.getx(), player.gety() + player.geth(), player.getw(), collisionRange);
                if (r2.intersects(r3) && player.getdy() >= 0 && i.collide == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkAbove() {
        for (GameObject i : handler.object) {
            if (i.id == ID.BasicBlock) {
                Rectangle r3 = i.getBounds();
                Rectangle r2 = new Rectangle(player.x, player.y - collisionRange, player.w, collisionRange);
                if (r2.intersects(r3) && player.dy < 0 && i.collide == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLeft() {
        for (GameObject i : handler.object) {
            if (i.id == ID.BasicBlock) {
                Rectangle r3 = i.getBounds();
                Rectangle r2 = new Rectangle(player.x - collisionRange, player.y + 3, collisionRange, player.h - 6);
                if (r2.intersects(r3) && i.collide == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkRight() {
        for (GameObject i : handler.object) {
            if (i.id == ID.BasicBlock) {
                Rectangle r3 = i.getBounds();
                Rectangle r2 = new Rectangle(player.x + 20, player.y + 3, collisionRange, player.h - 6);
                if (r2.intersects(r3) && i.collide == true) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getTouchedBlockIndex() {
        for (int i = 1; i < handler.object.size(); i++) {
            if (handler.object.get(i).id == ID.BasicBlock) {
                Rectangle r = handler.object.get(i).getBounds();
                Rectangle r3 = new Rectangle(player.x, player.y + 20, player.w, collisionRange);
                if (i + 1 < handler.object.size() && handler.object.get(i + 1).id == ID.BasicBlock) {
                    Rectangle r2 = handler.object.get(i + 1).getBounds();
                    if (r.intersection(r3).getWidth() >= r2.intersection(r3).getWidth()
                            && r.intersection(r3).getHeight() >= r2.intersection(r3).getHeight()) {
                        if (r.intersection(r3).getWidth() > 0 && r.intersection(r3).getHeight() > 0) {
                            if (handler.object.get(i).collide == true) {
                                return i;
                            } else {
                                return i + 1;
                            }
                        }
                    } else if (r.intersection(r3).getWidth() < r2.intersection(r3).getWidth()
                            && r.intersection(r3).getHeight() <= r2.intersection(r3).getHeight()) {
                        if (r.intersection(r3).getWidth() > 0 && r.intersection(r3).getHeight() > 0) {
                            if (handler.object.get(i + 1).collide == true) {
                                return i + 1;
                            } else {
                                return i;
                            }
                        }
                    }
                } else if (r.intersects(r3) && handler.object.get(i).collide == true) {
                    return i;
                }
            }
        }
        return -1;
    }
}
