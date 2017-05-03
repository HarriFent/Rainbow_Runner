package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

    protected int x, y, w, h, index;
    protected ID id;
    protected Handler handler;
    protected double dx, dy;
    protected double d2x, d2y;
    protected boolean collide = true;
    protected int stateTick = 0;
    protected Color color;
    protected Game game;

    public GameObject(int x, int y, ID id, Handler handler, Game game) {
        this.x = x;
        this.y = y;
        this.w = 20;
        this.h = 20;
        this.id = id;
        this.handler = handler;
        this.game = game;
    }

    public abstract void tick();

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public void setx(int x) {
        this.x = x;
    }

    public void sety(int y) {
        this.y = y;
    }

    public void setw(int w) {
        this.w = w;
    }

    public void seth(int h) {
        this.h = h;
    }

    public void setid(ID id) {
        this.id = id;
    }

    public void setdx(double dx) {
        this.dx = dx;
    }

    public void setdy(double dy) {
        this.dy = dy;
    }

    public void setd2x(double d2x) {
        this.d2x = d2x;
    }

    public void setd2y(double d2y) {
        this.d2y = d2y;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int getw() {
        return w;
    }

    public int geth() {
        return h;
    }

    public ID getid() {
        return id;
    }

    public double getdx() {
        return dx;
    }

    public double getdy() {
        return dy;
    }

    public double getd2x() {
        return d2x;
    }

    public double getd2y() {
        return d2y;
    }
}
