package top.braycep.bean;

import top.braycep.inter.Score;

import java.awt.image.BufferedImage;

public class SmallPlane extends FlyingObject implements Score {
    private int speed;

    {
        images = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            images[i] = loadImage("airplane" + i + ".png");
        }
    }

    public SmallPlane() {
        super(57, 51);
        speed = 2;
    }

    @Override
    public void step() {
        y += speed;
    }

    @Override
    public int getScore() {
        return 1;
    }
}
