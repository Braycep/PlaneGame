package top.braycep.bean;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Sky extends FlyingObject {
    private int speed;
    private int y1;

    {
        images = new BufferedImage[1];
        images[0] = loadImage("background.png");
    }

    public Sky() {
        super(0, 0, WIDTH, HEIGHT);
        speed = 1;
        y1 = -HEIGHT;
    }

    @Override
    public void step() {
        y += speed;
        y1 += speed;
        if (y >= HEIGHT) {
            y = -HEIGHT;
        }
        if (y1 >= HEIGHT) {
            y1 = -HEIGHT;
        }
    }

    @Override
    BufferedImage getImage() {
        return images[0];
    }

    @Override
    public void paintObject(Graphics g) {
        g.drawImage(getImage(), x, y, null);
        g.drawImage(getImage(), x, y1, null);
    }
}
