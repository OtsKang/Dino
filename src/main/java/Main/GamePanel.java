package Main;

import Entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{


    public final int origTileSize = 32;
    public final int maxScreenCol = 32; // 1024
    public final int maxScreenRow = 24; // 768
    public final int FPS = 60;
    public final int screenWidth = origTileSize * maxScreenCol;
    public final int screenHeight = origTileSize * maxScreenRow;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public Thread gameThread;

    KeyHandler kh = new KeyHandler();
    private Image backGround;
    TileManager tm = new TileManager(this);
    public Player player = new Player(this,kh);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(kh);
        this.setFocusable(true);
        backGround = Toolkit.getDefaultToolkit().createImage("src/main/resources/BG/BG.png");
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // 0.01666 sekuntia
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 =(Graphics2D) g;
        g2.drawImage(backGround,0,0,this);
        tm.draw(g2);
        player.draw(g2);
        g2.dispose();
    }


}
