package game;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 600, HEIGHT = 600;
    private Thread thread;
    private boolean running = false;
    public Handler handler;
    private KeyInput keyInput;
    private MouseInput mouseInput;
    private LevelEditor levelEditor;
    private Menu menu;
    private HUD hud;
    private FileHandler fHandler;

    public enum GAMESTATE {
        MENU, LEVELEDITOR, GAME, GAMEOVER;
    }
    public GAMESTATE gameState = GAMESTATE.MENU;
    public Object[] level = {};

    public Game() {
        fHandler = new FileHandler();
        try {
            fHandler.loadFile();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        level = fHandler.getLevel(0);
        handler = new Handler();
        keyInput = new KeyInput(handler, this);
        mouseInput = new MouseInput();
        menu = new Menu(mouseInput, this);
        levelEditor = new LevelEditor(mouseInput, this, fHandler);
        hud = new HUD(this);
        this.addKeyListener(keyInput);
        this.addMouseListener(mouseInput);
        this.addMouseMotionListener(mouseInput);
        new Window(606, 629, "Game", this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frames = 0;
            }
        }
        stop();
    }

    public static void main(String[] args) {
        new Game();
    }

    private void tick() {
        switch (gameState) {
            case MENU:
                menu.tick();
                break;
            case LEVELEDITOR:
                levelEditor.tick();
                break;
            case GAMEOVER:
            case GAME:
                handler.tick();
                keyInput.tick();
                hud.tick();
                break;
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(220, 220, 220));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        switch (gameState) {
            case MENU:
                menu.render(g);
                break;
            case LEVELEDITOR:
                levelEditor.render(g);
                break;
            case GAMEOVER:
            case GAME:
                this.drawBarrier(g);
                handler.render(g);
                this.findPlayer().render(g);
                hud.render(g);
                break;
        }

        g.dispose();
        bs.show();
    }
    
    private void drawBarrier(Graphics g) {
        float alpha = 0.5f;
        Graphics2D g2d = (Graphics2D) g;
        Composite ogComp = g2d.getComposite();
        AlphaComposite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g2d.setComposite(alphaComp);

        for (int i = 0; i < 31; i++) {
            int[] lstx = {-20 + (i * 20), 0 + (i * 20), 20 + (i * 20), 0 + (i * 20)};
            int[] lsty = {580, 580, 600, 600};
            Polygon p = new Polygon(lstx, lsty, 4);
            if (i % 2 == 0) {
                g.setColor(Color.black);
            } else {
                g.setColor(Color.yellow);
            }
            g.fillPolygon(p);
        }
        g2d.setComposite(ogComp);
    }

    public GameObject findPlayer() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getid() == ID.Player) {
                return temp;
            }
        }
        return null;
    }

    public static int clamp(int i, int min, int max) {
        if (i < min) {
            i = min;
        } else if (i > max) {
            i = max;
        }
        return i;
    }
}
