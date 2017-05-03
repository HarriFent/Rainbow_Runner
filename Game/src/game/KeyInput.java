package game;

import game.Game.GAMESTATE;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    Handler handler;
    private boolean spacePressed = false;
    private Game game;

    public KeyInput(Handler handler, Game game) {
        this.handler = handler;
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        GameObject player = findPlayer();
        switch (key) {
            case KeyEvent.VK_LEFT:
                player.setd2x(-0.2);
                break;
            case KeyEvent.VK_RIGHT:
                player.setd2x(0.2);
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = true;
                break;
            default:
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        GameObject player = findPlayer();
        switch (key) {
            case KeyEvent.VK_LEFT:
                player.setd2x(0);
                break;
            case KeyEvent.VK_RIGHT:
                player.setd2x(0);
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
            }else if (game.gameState == GAMESTATE.GAMEOVER){
                game.gameState = GAMESTATE.GAME;
                player.x = 290;
                player.y = 290;
                player.dy = 0;
                HUD.SCORE = 0;
                for (int i = 0; i < handler.object.size(); i++) {
                    GameObject tempObject = handler.object.get(i);
                    if (tempObject.id == ID.BasicBlock) {
                        tempObject.color = new Color(0,255,0);
                        tempObject.collide = true;
                    }
                }
            }
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
