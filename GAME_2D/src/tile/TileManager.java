package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	public Tile[] tile;
//	biến bản đồ
	public int mapTileNum [][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10]; //có thể thêm nhiều loại tiles hơn ở đây
//		kích thước map
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/world01.txt");
	}
	public void getTileImage() {
		try {
			
			tile[0]= new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1]= new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true; //vì wall cứng
			
			tile[2]= new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3]= new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			tile[4]= new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5]= new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
//	hàm tải map
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader  br = new BufferedReader(new InputStreamReader(is));
			
			int col =0;
			int row=0;
			
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine(); // readline này giúp đọc dữ liệu trên 1 dòng
				
				while(col < gp.maxWorldCol) {
					String numbers[]= line.split(" "); //lưu ý: map01.txt phả có khoảng trắng với nhau
					//đổi number thành integer
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row]=num;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col =0;
					row++;
				}
			}
			br.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
//	tạo hàm vẽ ở đây
	public void draw(Graphics2D g2) {
		
//		vẽ thủ công
//		g2.drawImage(tile[0].image,0,0,gp.tileSize,gp.tileSize,null);
//		g2.drawImage(tile[1].image,48,0,gp.tileSize,gp.tileSize,null);
//		g2.drawImage(tile[2].image,96,0,gp.tileSize,gp.tileSize,null);
		
		int worldCol = 0;
		int worldRow = 0;
		
//		int x = 0;
//		int y = 0;
		
//		dùng while loop
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow]; // tileNum là 1 số ...
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
//			lệnh if này giúp vẽ màn hình theo đúng vị trí của nhân vật và hiệu suất tải ảnh cao hơn.
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - gp.tileSize < gp.player.worldX + gp.player.screenX) {
				
				g2.drawImage(tile[tileNum].image,screenX,screenY,gp.tileSize,gp.tileSize,null);
			}
			worldCol++;
//			x += gp.tileSize;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol =0;
//				x =0;
				worldRow++;
//				y += gp.tileSize;
				
			}
		}
	}
}
