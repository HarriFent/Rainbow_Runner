package game;

import game.Game.GAMESTATE;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handler handler;
    private boolean spacePressed, leftPressed, rightPressed;
    private Game game;

    public KeyInput(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                leftPressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = true;
                break;
            case KeyEvent.VK_ESCAPE:
                handler.clearObjects();
                HUD.SCORE = 0;
                game.gameState = GAMESTATE.MENU;
                break;
            default:
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                leftPressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false;
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = false;
                break;
            default:
                break;
        }
    }

    public void tick() {
        GameObject player = findPlayer();
        if (spacePressed) {
            if (player.getd2y() == 0 && game.gameState == GAMESTATE.GAME) {
                player.setdy(-8);
                player.stateTick = 10;
            } else if (game.gameState == GAMESTATE.GAMEOVER) {
                game.gameState = GAMESTATE.GAME;
                player.dy = 0;
                player.dx = 0;
                HUD.SCORE = 0;
                for (int i = 0; i < game.level.length; i++) {
                    if ((int) game.level[i] == 1) {
                        player.setx((i % 30) * 20);
                        player.sety((i / 30) * 20);
                        break;
                    }
                }
                for (int i = 0; i < handler.object.size(); i++) {
                    GameObject tempObject = handler.object.get(i);
                    tempObject.collide = true;
                    if (tempObject.id == ID.BasicBlock) {
                        tempObject.color = new Color(0, 255, 0);
                    } else if (tempObject.id == ID.ExplodeBlock){
                        tempObject.color = new Color(0, 0, 150);
                    }
                }
            }
        }
        if (leftPressed) {
            player.setd2x(-0.2);
        } else if (rightPressed) {
            player.setd2x(0.2);
        } else {
            player.setd2x(0);
        }
    }

    private GameObject findPlayer() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getid() == ID.Player) {
                return temp;
            }
        }
        return null;
    }
}
