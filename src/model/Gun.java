package src.model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.view.GamePanel;
import src.controller.SoundPlayer;

public class Gun implements Runnable {
    private Image bullet;
    private int ammo = 3;
    private SoundPlayer shootAudio;
    private volatile boolean stopped = false;
    private String shootPath = "resources\\sounds\\gunShoot.wav";
    private String reloadPath = "resources\\sounds\\gunReload.wav";

    public Gun() {
        showBullet();
        shootAudio = new SoundPlayer();
    }

    private void showBullet() {
        try {
            bullet = ImageIO.read(new File("resources/images/bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawBullet(Graphics g, ImageObserver observer, int bulletIndex) {
        if (bulletIndex >= 0 && bulletIndex < ammo) {
            int x = 30 + bulletIndex * 20;
            int y = 500;
            g.drawImage(bullet, x, y, observer);
        }
    }

    public void shoot() {
        if (ammo > 0) {
            System.out.println("Shoot!");
            shootAudio.playSound(shootPath);
            ammo--;
        } else {
            System.out.println("Out of ammo!");
        }
    }

    public int getAmmo() {
        return ammo;
    }

    public void reload() {
        shootAudio.playSound(reloadPath);
        ammo = 3;
        System.out.println("Reloaded!");
    }

    @Override
    public void run() {
        while (!stopped) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        System.out.println("Thread Gun Stopped");
        stopped = true;
    }
}