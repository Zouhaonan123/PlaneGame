package ny.zhn.game;

import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;


/**
 * 飞机游戏主窗口
 * @author 邹浩南
 *
 */

public class PlaneGameFrame extends JFrame {
	Image background = GameUtil.getImage("images/background.jfif");
	Image planeImg = GameUtil.getImage("images/plane.png");
	
	Plane plane = new Plane(planeImg, 300, 300);
	//Shell shell = new Shell();
	
	Shell[] shells = new Shell[50];//定义50发炮弹
	
	Explode exp;
	
	Date startTime = new Date();//记录起始时间
	Date endTime;
	int period;
	static boolean fresh = false;
	
	public void launchFrame() {
		this.setTitle("邹浩南");
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(300, 200);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		new PaintThread().start();//启动重画窗口线程
		addKeyListener(new KeyMonitor());
		
		//初始化50发炮弹
		for(int i = 0; i<shells.length; i++) {
			shells[i] = new Shell();
		}
		
	}

	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		
		g.drawImage(background, 0, 0, null);
		
		plane.drawSelf(g);
		//画出所有炮弹
		for(int i = 0; i<shells.length; i++) {
			shells[i].drawSelf(g);
			boolean hit = shells[i].getRect().intersects(plane.getRect());//碰撞检测
			if(hit) {
				//System.out.println("炸了！");
				plane.live = false;
				if(exp == null) {
					exp = new Explode(plane.x, plane.y);
					//System.out.println(plane.x + " " + plane.y);
					endTime = new Date();//记录结束时间
					period = (int)((endTime.getTime() - startTime.getTime())/1000);
				}
				
				exp.draw(g);
				
			}
			
			if(!plane.live) {
				g.setColor(Color.red);
				Font f = new Font("宋体", Font.BOLD, 50);
				g.setFont(f);
				
				//g.drawString("你坚持了 " + period + " 秒！", 200, 200);
				if(period < 5) {
					g.drawString("你坚持了 " + period + " 秒！太快了！", 140, 200);
				} else {
					g.drawString("你坚持了 " + period + " 秒！太强了！", 140, 200);
				}
			}
		}
		g.setColor(c);
	}
	
	//帮助我们反复重画窗口，内部类
	class PaintThread extends Thread {
		@Override
		public void run() {
			while(true) {
				repaint();
				//System.out.println("窗口被画一次！");
				
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				 
			}
		}
	}
	
	//定义键盘监听内部类
	class KeyMonitor extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
			if(e.getKeyCode() == KeyEvent.VK_F) {
				fresh = true;
			}
		}
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);

		}
	}
	
	public static void main(String[] args) {
		PlaneGameFrame frame = new PlaneGameFrame();
		frame.launchFrame();
		
		
	}
	
	private Image offScreenImage = null;
	 
	public void update(Graphics g) {
	    if(offScreenImage == null)
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	}  
}
