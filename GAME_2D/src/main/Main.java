package main;

import javax.swing.JFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JFrame window = new JFrame();
		
//		cài đặt nhấn X tắt màn hình
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		cài đặt kích thước màn hình không đổi
		window.setResizable(false);	
		
		window.setTitle("RPG 2D Adventure");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
//		 gói lại để hiển thị màn hình
		window.pack();
		
//		màn hình game xuất hiện ở trung tâm màn hình máy tính
		window.setLocationRelativeTo(null);
//		thiết đặt hiển thị cửa sổ game
		window.setVisible(true);
		try {
			gamePanel.startGameThread();
		} catch (Exception e) {
			e.getMessage();
		}
		
		
		
	}

}
