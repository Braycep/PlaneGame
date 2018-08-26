package top.braycep.main;

import top.braycep.bean.*;
import top.braycep.inter.Award;
import top.braycep.inter.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class World extends JPanel {
    private static final int WIDTH = FlyingObject.WIDTH;
    private static final int HEIGHT = FlyingObject.HEIGHT;
    private static final int START = 0;
    private static final int RUNNING = 1;
    private static final int PAUSE = 2;
    private static final int GAME_OVER = 3;

    private int state;
    private int score;
    private Sky sky;
    private HeroPlane heroPlane;
    private FlyingObject[] enemies;
    private Bullet[] bullets;
    private int enemieDelay;
    private int bulletDelay;

    {
        initWorld();
        initEvents();
    }

    private void initWorld() {
        state = START;
        score = 0;
        sky = new Sky();
        heroPlane = new HeroPlane();
        enemies = new FlyingObject[]{};
        bullets = new Bullet[]{};
    }

    private void initEvents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (state == GAME_OVER) {
                    initWorld();
                    state = START;
                } else if (state == START) {
                    state = RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (state == RUNNING) {
                    state = PAUSE;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (state == PAUSE) {
                    state = RUNNING;
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (state == RUNNING) {
                    heroPlane.moveTo(e.getX(), e.getY());
                }
            }
        });
    }

    private FlyingObject initEnemies() {
        int enemy = new Random().nextInt(20);
        if (enemy < 5) {
            return new Bee();
        } else if (enemy < 15) {
            return new SmallPlane();
        } else if (enemy < 20) {
            return new BigPlane();
        }
        return null;
    }

    private void enemiesAction() {
        if (++enemieDelay % 40 == 0) {
            enemies = Arrays.copyOf(enemies, enemies.length + 1);
            enemies[enemies.length - 1] = initEnemies();
            enemieDelay = 0;
        }
    }

    private void shootAction() {
        if (++bulletDelay % 20 == 0) {
            bulletDelay = 0;
            Bullet[] bs = heroPlane.shoot();
            bullets = Arrays.copyOf(this.bullets, bullets.length + bs.length);
            System.arraycopy(bs, 0, bullets, bullets.length - bs.length, bs.length);
        }
    }

    private void notOutOfBoundsAction() {
        FlyingObject[] es = new FlyingObject[enemies.length];
        int index = 0;
        for (FlyingObject enemy : enemies) {
            if (enemy.notOutOfBounds()) {
                es[index] = enemy;
                index++;
            }
        }
        enemies = Arrays.copyOf(es, index);

        index = 0;
        Bullet[] bs = new Bullet[bullets.length];
        for (Bullet bullet : bullets) {
            if (bullet.notOutOfBounds()) {
                bs[index] = bullet;
                index++;
            }
        }
        bullets = Arrays.copyOf(bs, index);
    }

    private void hitAction() {
        for (FlyingObject enemy : enemies) {
            for (Bullet bullet : bullets) {
                if (enemy.hit(bullet) && enemy.isLife() && bullet.isLife()) {
                    enemy.goDead();
                    bullet.goDead();
                    if (enemy instanceof Score) {
                        score += ((Score) enemy).getScore();
                    } else if (enemy instanceof Award) {
                        if (((Award) enemy).getAwardType() == Award.LIFE) {
                            heroPlane.addLife();
                        } else {
                            heroPlane.addFire();
                        }
                    }
                }
            }

            if (enemy.hit(heroPlane) && enemy.isLife()) {
                enemy.goDead();
                heroPlane.subLife();
                heroPlane.clearFire();
                if (heroPlane.getLife() == 0) {
                    state = GAME_OVER;
                }
            }
        }
    }

    private void action() {
        int interval = 10;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == RUNNING) {
                    enemiesAction();
                    shootAction();
                    notOutOfBoundsAction();
                    hitAction();
                }
                repaint();
            }
        }, interval, interval);
    }

    @Override
    public void paint(Graphics g) {
        sky.paintObject(g);
        heroPlane.paintObject(g);
        if (state == RUNNING) {
            sky.step();

            for (FlyingObject enemy : enemies) {
                enemy.step();
                enemy.paintObject(g);
            }

            for (Bullet bullet : bullets) {
                bullet.step();
                bullet.paintObject(g);
            }

            g.drawString("得分：" + score, 20, 20);
            g.drawString("生命：" + heroPlane.getLife(), 20, 40);
            g.drawString("火力：" + heroPlane.getDoubleFire(), 20, 60);
        }

        switch (state) {
            case START:
                g.drawImage(FlyingObject.loadImage("start.png"), 0, 0, WIDTH, HEIGHT, null);
                break;
            case PAUSE:
                g.drawImage(FlyingObject.loadImage("pause.png"), 0, 0, WIDTH, HEIGHT, null);
                break;
            case GAME_OVER:
                g.drawImage(FlyingObject.loadImage("gameover.png"), 0, 0, WIDTH, HEIGHT, null);
                break;
        }
    }

    public static void main(String[] args) {
        World world = new World();

        JFrame frame = new JFrame();
        frame.add(world);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        world.action();
    }
}
