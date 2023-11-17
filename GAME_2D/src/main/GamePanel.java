package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

// JPanel là một class hiển thị như một loại màn hình chơi game
public class GamePanel extends JPanel implements Runnable{
	
//	cài đặt các thông số màn hình chơi game
	
//	đặt kích thước ban đầu 
	final int originalTileSize = 16; //ô 16x16 pixels
	final int scale = 3; //16 *3 = 48x48 pixels
//	kích thước nvat hay NPC 
	public final int tileSize = originalTileSize * scale; //48pxs
	
//	đặt kích thước màn hình
	
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol; //48x16 = 768px
	public final int screenHeight = tileSize * maxScreenRow; //48x12 = 576px
	
//	thêm thông số map thế giới
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol; //48x16 = 768px
	public final int worldHeight = tileSize * maxWorldRow; //48x12 = 576px
	
//	thêm FPS (Frames Per Second)
	int FPS = 60;
	
//	thêm TileManager object
	TileManager tileM = new TileManager(this);
	
//	thêm object KeyHandler
	KeyHandle keyH = new KeyHandle();
	
//	một luồng xử lý ==> runnable(interface)[run]
	Thread gameThread;
	
//	thêm va chạm
	public CollisionChecker cChecker = new  CollisionChecker(this);
	
//	thêm Player trong entity
	public Player player = new Player(this, keyH);
	
////	cái đặt vài thông số mặc định
//	int playerX = 100;
//	int playerY = 100;
//	int playerSpeed = 4;
	
//tạo một hàm tạo ở đây
	public GamePanel() {
	this.setPreferredSize(new Dimension(screenWidth,screenHeight));
	this.setBackground(Color.black);
	this.setDoubleBuffered(true);
//	thêm KeyHandler vào đây
	this.addKeyListener(keyH);
	this.setFocusable(true); //nhận focus từ bàn phím
	}
	
//	tạo ham này khi gameThread start sẽ khởi động [run]
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
@Override
	public void run() {
		
	double drawInterval = 1000000000/ FPS; // cứ mỗi 0,016s thì vẽ frame 1 lần
	double delta = 0;
	long lastTime= System.nanoTime();
	long currentTime;
//	 kiểm tra số lần FPS
	long timer = 0;
	int countDraw = 0;
	
	//	khi gameThread tồn tại
		while(gameThread != null) {
			
	//		System.out.println("Đang chạy vòng lặp");
			
//			dùng để kiểm tra thời gian
//			long currentTime = System.nanoTime();
//			System.out.println("current time:"+currentTime);
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime)/ drawInterval;
			
			timer = currentTime - lastTime;
			
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta --;
				countDraw ++;
			}
			if(timer >= 1000000000) {
				System.out.println("FPS: " + countDraw);
				countDraw =0;
				timer = 0;
			}
		}	
	}
	
	public void update() {
		player.update();
	}
	
//	dùng hàm có sẵn painComponent (graphics g)
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
//		graphic2D cúng giống với Graphic nhưng có nhiều function hơn
		Graphics2D g2 = (Graphics2D)g;
		
//		luôn thêm background trên người chơi
		tileM.draw(g2);
		
//		tạo màu cho khối
//		g2.setColor(Color.white);
//		draw 1 hình chũ nhật trên màn hình bằng fillRec(int x,int y,int width,int height)
//		g2.fillRect(playerX,playerY,tileSize,tileSize);
		player.draw(g2);
		
//		dùng dispose để giải phóng bộ nhớ do Graphic2D (is used). [không có vẫn run]
		g2.dispose();
	}
}
