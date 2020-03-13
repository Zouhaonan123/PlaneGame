package ny.zhn.game;

import java.awt.Color;
import java.awt.Graphics;

public class Shell extends GameObject {
	
	double degree;
	
	public Shell() {
		degree = Math.random()*Math.PI*2;
		x = 200;
		y = 200;
		width = 10;
		height = 20;
		speed = 7;
	}
	
	public void drawSelf(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.yellow);
		g.fillOval((int)x, (int)y, width, height);
		//System.out.println(speed);
		x += speed*Math.cos(degree);
		y -= speed*Math.sin(degree);
		//System.out.println(x + " " + y);
		if(x < 10 || x > Constant.GAME_WIDTH - 40) {
			degree = Math.PI - degree;
		}
		if(y < 40 || y > Constant.GAME_HEIGHT - 30) {
			degree = -degree;
		}
		
		g.getColor();
		
	}
}
