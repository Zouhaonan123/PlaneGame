package ny.zhn.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class Plane extends GameObject {

	boolean up, down, left, right;
	boolean live = true;
	int speed = 8;
	public Plane(Image planeImg, double x, double y) {
		this.img = planeImg;
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 33;
	}
	public void drawSelf(Graphics g) {
		if(live) {
			g.drawImage(img, (int)x, (int)y, null); 
			if(up) {
				y -= speed;
			}
			if(down) {
				y += speed;
			}
			if(left) {
				x -= speed;
			}
			if(right) {
				x += speed;
			}
		}
		
	}
	
	public void addDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		}
	}
	
	public void minusDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		}
	}

}
