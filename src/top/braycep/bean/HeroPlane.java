package top.braycep.bean;

import java.awt.image.BufferedImage;

public class HeroPlane extends FlyingObject {
    private int doubleFire;
    private int life = 3;

    {
        images = new BufferedImage[2];
        images[0] = loadImage("hero0.png");
        images[1] = loadImage("hero1.png");
    }

    public HeroPlane() {
        super((WIDTH - 97) / 2, (HEIGHT - 124 - 50), 97, 124);
    }

    public void moveTo(int x, int y) {
        this.x = x - width / 2;
        this.y = y - height / 2;
    }

    public Bullet[] shoot() {
        Bullet[] bullets;
        if (doubleFire == 0) {
            bullets = new Bullet[1];
            bullets[0] = new Bullet(x + width / 2, y, 0);
        } else {
            bullets = new Bullet[3];
            bullets[0] = new Bullet(x + width / 4, y, -1);
            bullets[1] = new Bullet(x + width / 2, y, 0);
            bullets[2] = new Bullet(x + width / 4 * 3, y, 1);
            doubleFire -= 3;
        }
        return bullets;
    }

    @Override
    public void step() {

    }

    @Override
    BufferedImage getImage() {
        return currentImage == 0 ? (images[currentImage = 1]) : (images[currentImage = 0]);
    }

    public void addFire() {
        this.doubleFire += 30;
    }

    public void clearFire() {
        this.doubleFire = 0;
    }

    public void addLife() {
        this.life++;
    }

    public void subLife() {
        this.life--;
    }

    public int getDoubleFire() {
        return doubleFire;
    }

    public int getLife() {
        return life;
    }
}
