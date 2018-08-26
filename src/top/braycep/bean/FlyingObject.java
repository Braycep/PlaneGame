package top.braycep.bean;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public abstract class FlyingObject {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 700;
    private static final int LIFE = 0;
    private static final int DEAD = 1;
    private static final int REMOVE = 2;

    BufferedImage[] images;
    private int state = LIFE;
    int x, y;
    int width, height;
    int currentImage;

    FlyingObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    FlyingObject(int width, int height) {
        this.x = new Random().nextInt(WIDTH - width);
        this.y = -height;
        this.width = width;
        this.height = height;
    }

    /**
     * 检测当前对象与object是否碰撞
     *
     * @param object 碰撞对象
     * @return 返回是否碰撞
     */
    public boolean hit(FlyingObject object) {
        int x1 = x - object.width;
        int x2 = x + width;
        int y1 = y - object.height;
        int y2 = y + height;
        return object.x > x1 && object.x < x2 && object.y > y1 && object.y < y2;
    }

    public boolean notOutOfBounds() {
        return x > 0 && x < WIDTH && y < HEIGHT;
    }

    public void paintObject(Graphics g) {
        g.drawImage(getImage(), x, y, width, height, null);
    }

    public static BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(new File(System.getProperty("user.dir") + "\\images\\", fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    BufferedImage getImage() {
        if (isLife()) {
            return images[0];
        } else if (isDead()) {
            if (currentImage < 5) {
                return images[currentImage++];
            } else {
                state = REMOVE;
            }
        }
        return null;
    }

    public void goDead(){
        state = DEAD;
    }

    public abstract void step();

    public boolean isLife() {
        return state == LIFE;
    }

    private boolean isDead() {
        return state == DEAD;
    }
}
