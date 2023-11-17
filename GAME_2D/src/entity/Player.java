package entity;

//import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandle;

public class Player extends Entity{
	GamePanel gp;
	KeyHandle keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandle keyH) {
		this.gp= gp;
		this.keyH = keyH;
		
		screenX =gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		
//		 //tạo khối va chạm cho nv
		solidArea = new Rectangle(8,16,32,32); //có thể = new Rectangle(0,0,48,48) ==  hoặc 48 = gp.tileSize;
////		solidArea.x =8;
////		solidArea.y=16;
////		solidArea.width = 32;
////		solidArea.height = 32;
		
		setDefautlValue();
		getPlayerImage();
	}
	
	public void setDefautlValue() {
		worldX =gp.tileSize * 23;
		worldY= gp.tileSize * 21;
		speed=4;

		direction = "down"; // ở đây viết hướng nào cũng được 
	}
	
	public void getPlayerImage() {
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
//		thêm điều kiện khi ko nhấn thì ảnh đứng yên
		if(keyH.upPressed == true || keyH.downPressed == true ||
				keyH.leftPressed == true || keyH.rightPressed == true) {
			if(keyH.upPressed == true) {
				direction ="up";
//				worldY -= speed;
			}else if(keyH.downPressed == true) {
				direction = "down";		
//				worldY += speed;
			}else if(keyH.leftPressed == true) {
				direction = "left";
//				worldX -= speed;
			}else if(keyH.rightPressed == true) {
				direction = "right";
//				worldX += speed;
			}
			
//			//kiểm tra vacham tile
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//nếu collisionOn == false thì có thể chạy
			if(collisionOn == false) {
				switch (direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;				
				}
			}
//			
//			bộ đếm bước chạy hình ảnh nhưng sẽ chạy mãi khi đứng vẫn chạy ảnh cho nên mới lồng vào lệnh if lớn ở trên 		
			spriteCounter++;
			if(spriteCounter >10) {
				if(spriteNumber == 1) {
					spriteNumber =2;
				}else if(spriteNumber ==2) {
					spriteNumber =1;
				}
				spriteCounter = 0;
			}
		}
		
		
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		switch (direction) {
		case "up":
			if(spriteNumber == 1) {
				image = up1;
			}
			if(spriteNumber == 2) {
				image = up2;
			}			
			break;
		
		case "down":
			if(spriteNumber == 1) {
				image = down1;
			}
			if(spriteNumber == 2) {
				image = down2;
			}			
			break;
		
		case "left":
			if(spriteNumber == 1) {
				image = left1;
			}
			if(spriteNumber == 2) {
				image = left2;
			}	
			break;
		
		case "right":
			if(spriteNumber == 1) {
				image = right1;
			}
			if(spriteNumber == 2) {
				image = right2;
			}	
			
			break;
		}
//		đặt null sẽ giúp cho hình ảnh đúng với các số liệu x y gp.tileSize
		g2.drawImage(image, screenX, screenY,gp.tileSize,gp.tileSize,null);
	}
}
