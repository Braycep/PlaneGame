package top.braycep.bean;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject {
    private int speed;
    private int dir;

    {
        images = new BufferedImage[1];
        images[0] = loadImage("bullet.png");
    }

    Bullet(int x, int y, int direction) {
        super(x, y, 8, 14);
        speed = 5;
        dir = direction;
    }

    @Override
    public void step() {
        if (dir == -1) {
            x--;
        } else if (dir == 1) {
            x++;
        }
        y -= speed;
    }

    @Override
    BufferedImage getImage() {
        if (isLife()) {
            return images[0];
        }
        return null;
    }
}
