package top.braycep.bean;

import java.awt.image.BufferedImage;

public class Bullet extends FlyingObject {
    private int speed;
    private int dir;
    private int baseX;

    {
        images = new BufferedImage[1];
        images[0] = loadImage("bullet.png");
    }

    Bullet(int x, int y, int direction) {
        super(x, y, 8, 14);
        speed = 5;
        dir = direction;
        baseX = x;
    }

    @Override
    public void step() {
        if (dir == -1) {
            x--;
        } else if (dir == 1) {
            x++;
        } else if (dir == 0) {
            speed = 1;
            x = baseX + ((int) (Math.sin(y * 0.1) * 50));
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
