package Entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import Main.GamePanel;
import Main.KeyHandler;

    public class Player {

        GamePanel gp;
        KeyHandler kh;
        public String direction;
        double jumpCount = 12;

        public boolean isIdle = true;
        public int speed = 4;
        private double spriteCounter = 0;
        private double jumpSpriteCounter = 0;
        private boolean isJumping = false;

        public BufferedImage[] idleSprite = new BufferedImage[10];
        public BufferedImage[] sprites = new BufferedImage[7];
        public BufferedImage[] jumpSprites = new BufferedImage[11];

        public final int screenX;
        public int screenY;
        public int charHeight;
        public int charWidth;
        public int initY;

        public int worldX;
        public int worldY;
        public Player(GamePanel gp, KeyHandler kh) {

            this.gp = gp;
            this.kh = kh;
            screenX = gp.origTileSize *3;
            screenY = gp.origTileSize *18;


            worldX = gp.origTileSize*3;
            worldY = gp.origTileSize*47;
            charHeight = gp.origTileSize * 2;
            charWidth = gp.origTileSize * 2;
            initY = gp.origTileSize*22;

            direction = "Right";
            SetPlayerImages();
        }

        public void update() {
            isIdle = true;
            if (kh.rightPressed || kh.leftPressed) {
                isIdle = false;
                if (kh.rightPressed) {
                    direction = "Right";
                    worldX += speed;
                } else if (kh.leftPressed) {
                    direction = "Left";
                    worldX -= speed;
                }
            }
            if (!isJumping){
                if(kh.jumpPressed){
                    isJumping = true;
                    initY = worldY;
                }
            } else {
                if (jumpCount >= -12) {
                    double neg = 1;
                    if (jumpCount < 0) neg = -1;
                    worldY -= (int) (jumpCount*jumpCount)* 0.5 * neg;
                    jumpCount--;
                } else {
                    isJumping = false;
                    jumpCount = 12;
                    worldY = initY;
                }
            }
            spriteUpdate(sprites);
        }

        private void spriteUpdate(BufferedImage[] arr) {
            if (spriteCounter >= 6) {
                spriteCounter = 0;
            } else spriteCounter += 0.25;
        }

        public void SetPlayerImages() {
            try {
                for (int i = 0; i < sprites.length; i++) {
                    sprites[i] = ImageIO.read(new FileInputStream("src/main/resources/player/Right" + i + ".png"));
                }
                for (int i = 0; i < idleSprite.length; i++) {
                    idleSprite[i] = ImageIO.read(new FileInputStream("src/main/resources/player/Idle" + i + ".png"));
                }
                for(int i = 0; i < jumpSprites.length; i++){
                    jumpSprites[i] = ImageIO.read(new FileInputStream("src/main/resources/player/RightJump"+i+".png"));
                }
            } catch (IOException i) {
                i.printStackTrace();
            }
        }

        public BufferedImage JumpSpriteChecker(){
            int drawJumpSpriteCount = Math.abs((int) jumpCount);
            if(drawJumpSpriteCount > 10){
                drawJumpSpriteCount = 10;
            }
            return jumpSprites[drawJumpSpriteCount];
        }

        public void draw(Graphics2D g2) {
            if (isIdle) {
                if(direction == "Right"){
                    if(isJumping) {
                        g2.drawImage(JumpSpriteChecker(), screenX, screenY, charWidth, charHeight, null);
                    } else {
                        g2.drawImage(idleSprite[(int) spriteCounter], screenX, screenY, charWidth, charHeight, null);
                    }
                } else if (direction == "Left"){
                if(isJumping) {

                    g2.drawImage(JumpSpriteChecker(), screenX + (charWidth / 2), screenY, -charWidth, charHeight, null);
                } else {
                    g2.drawImage(idleSprite[(int) spriteCounter], screenX + (charWidth / 2), screenY, -charWidth, charHeight, null);
                }
                }
            } else
                switch (direction) {
                    case "Right":
                           g2.drawImage(sprites[(int) spriteCounter], screenX, screenY, charWidth, charHeight, null);
                        break;
                    case "Left":
                        g2.drawImage(sprites[(int) spriteCounter], screenX + (charWidth / 2), screenY, -charWidth, charHeight, null);
                        break;
                }
        }
    }


