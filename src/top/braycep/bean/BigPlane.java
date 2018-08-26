package top.braycep.bean;

import top.braycep.inter.Score;

import java.awt.image.BufferedImage;

public class BigPlane extends FlyingObject implements Score {
    private int speed;

    {
        images = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            images[i] = loadImage("bigplane" + i + ".png");
        }
    }

    public BigPlane() {
        super(69, 99);
        speed = 2;
    }

    @Override
    public void step() {
        y += speed;
    }

    @Override
    public int getScore() {
        return 3;
    }
}
