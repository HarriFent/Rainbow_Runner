package game;

import game.Game.GAMESTATE;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class HUD {

    public static int SCORE = 0;
    private Game game;

    public HUD(Game game) {
        this.game = game;
    }

    public void tick() {
        
    }

    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Composite ogComp = g2.getComposite();
        float alpha = 0.5f;
        AlphaComposite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2.setComposite(alphaComp);
        g2.setColor(new Color(150, 150, 150));
        g2.fillRoundRect(5, 5, 200, 30, 10, 10);
        g2.setColor(new Color(0, 0, 0));
        g2.setFont(new Font(Font.SANS_SERIF, 0, 16));
        g2.setComposite(ogComp);
        g2.drawString("Score: " + Integer.toString(SCORE), 10, 26);
        if (game.gameState == GAMESTATE.GAMEOVER) {
            g2.setFont(new Font(Font.SANS_SERIF, 0, 30));
            g2.drawString("GAMEOVER", 200, 310);
            g2.setFont(new Font(Font.SANS_SERIF, 0, 20));
            g2.drawString("Score: " + Integer.toString(SCORE), 240, 340);
            g2.setFont(new Font(Font.SANS_SERIF, 0, 16));
            g2.drawString("Press [SPACE] to play again", 180, 370);
        }
    }
}
