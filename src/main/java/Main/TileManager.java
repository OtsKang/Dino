package Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.Objects;


import Tile.Tile;


public class TileManager {

    GamePanel gp;
    public Tile[] tiles;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tiles = new Tile[18];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        try {
            loadMap("maps/sampleMap.txt");
        } catch(FileNotFoundException f) {
            System.out.println("tiedostoa ei loydy");
        }
        System.out.println(System.getProperty("user.dir"));
    }


    public void getTileImage() {
        setUpTile(0,"0",true);
        setUpTile(1,"1",true);
        setUpTile(2,"2",true);
        setUpTile(3,"3",true);
        setUpTile(4,"4",true);
        setUpTile(5,"5",true);
        setUpTile(6,"6",true);
        setUpTile(7,"7",true);
        setUpTile(8,"8",true);
        setUpTile(9,"9",true);
        setUpTile(10,"10",true);
        setUpTile(11,"11",true);
        setUpTile(12,"12",true);
        setUpTile(13,"13",true);
        setUpTile(14,"14",true);
        setUpTile(15,"15",true);
        setUpTile(16,"16",true);
        setUpTile(17,"17",true);
    }


    public void setUpTile(int index, String imageName, boolean collision) {

        UtilityTool uTool = new UtilityTool();
        try{
            tiles[index] = new Tile();
            tiles[index].image = ImageIO.read(new FileInputStream("src/main/resources/Tiles/" + imageName + ".png"));
            tiles[index].image = uTool.scaleImage(tiles[index].image, gp.origTileSize, gp.origTileSize);
            tiles[index].collision = collision;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filepath) throws FileNotFoundException {

        InputStream is = getClass().getClassLoader().getResourceAsStream(filepath);
        StringBuilder result = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(is));
            BufferedReader br = new BufferedReader(isr);
            int col = 0;
            int row = 0;
            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                 String line = br.readLine();
                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.origTileSize;
            int worldY = worldRow * gp.origTileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            /*
             * Lasketaan, mitkä ruudut piirretään eli pelaajan kamerassa näkyvät, plus yksi rivi tai pylväs
             * käsittäen kaikki neljä pääilmansuuntaa
             *  */
            if( worldX + gp.origTileSize > gp.player.worldX - gp.player.screenX * gp.origTileSize &&
                    worldX - gp.origTileSize* gp.origTileSize < gp.player.worldX + gp.player.screenX &&
                    worldY + gp.origTileSize*gp.origTileSize > gp.player.worldY - gp.player.screenY &&
                    worldY - gp.origTileSize*gp.origTileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tiles[tileNum].image,screenX,screenY,null);
            }
            worldCol++;
            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}

