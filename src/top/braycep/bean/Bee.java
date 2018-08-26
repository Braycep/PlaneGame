package top.braycep.bean;

import top.braycep.inter.Award;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Bee extends FlyingObject implements Award {
    private int xSpeed;
    private int ySpeed;
    private int awardType;

    {
        images = new BufferedImage[5];
        for (int i = 0; i < 5; i++) {
            images[i] = loadImage("bee"+i+".png");
        }
    }

    public Bee() {
        super(60, 50);
        xSpeed = 1;
        ySpeed = 2;
        awardType = new Random().nextInt(2);
    }

    @Override
    public void step() {
        x += xSpeed;
        y += ySpeed;
        if (x <= 0 || x >= WIDTH - width) {
            xSpeed *= -1;
        }
    }

    @Override
    public int getAwardType() {
        return awardType;
    }
}
