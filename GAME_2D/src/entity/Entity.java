package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

// tạo class cha này để sử dụng cho các nhân vật sau này
public class Entity{
	public int worldX,worldY;
	public int speed;
	
//	import kho lưu trữ hình ảnh
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public String direction;
	
	public int spriteCounter= 0;
	public int spriteNumber= 1;
	
	 //tạo khối HCN ảo trên player để tạo collision
	public Rectangle solidArea;
	public boolean collisionOn = false;
	
}