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
 * �ɻ���Ϸ������
 * @author �޺���
 *
 */

public class PlaneGameFrame extends JFrame {
	Image background = GameUtil.getImage("images/background.jfif");
	Image planeImg = GameUtil.getImage("images/plane.png");
	
	Plane plane = new Plane(planeImg, 300, 300);
	//Shell shell = new Shell();
	
	Shell[] shells = new Shell[50];//����50���ڵ�
	
	Explode exp;
	
	Date startTime = new Date();//��¼��ʼʱ��
	Date endTime;
	int period;
	static boolean fresh = false;
	
	public void launchFrame() {
		this.setTitle("�޺���");
		this.setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
		this.setLocation(300, 200);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		new PaintThread().start();//�����ػ������߳�
		addKeyListener(new KeyMonitor());
		
		//��ʼ��50���ڵ�
		for(int i = 0; i<shells.length; i++) {
			shells[i] = new Shell();
		}
		
	}

	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		
		g.drawImage(background, 0, 0, null);
		
		plane.drawSelf(g);
		//���������ڵ�
		for(int i = 0; i<shells.length; i++) {
			shells[i].drawSelf(g);
			boolean hit = shells[i].getRect().intersects(plane.getRect());//��ײ���
			if(hit) {
				//System.out.println("ը�ˣ�");
				plane.live = false;
				if(exp == null) {
					exp = new Explode(plane.x, plane.y);
					//System.out.println(plane.x + " " + plane.y);
					endTime = new Date();//��¼����ʱ��
					period = (int)((endTime.getTime() - startTime.getTime())/1000);
				}
				
				exp.draw(g);
				
			}
			
			if(!plane.live) {
				g.setColor(Color.red);
				Font f = new Font("����", Font.BOLD, 50);
				g.setFont(f);
				
				//g.drawString("������ " + period + " �룡", 200, 200);
				if(period < 5) {
					g.drawString("������ " + period + " �룡̫���ˣ�", 140, 200);
				} else {
					g.drawString("������ " + period + " �룡̫ǿ�ˣ�", 140, 200);
				}
			}
		}
		g.setColor(c);
	}
	
	//�������Ƿ����ػ����ڣ��ڲ���
	class PaintThread extends Thread {
		@Override
		public void run() {
			while(true) {
				repaint();
				//System.out.println("���ڱ���һ�Σ�");
				
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				 
			}
		}
	}
	
	//������̼����ڲ���
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
	        offScreenImage = this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//������Ϸ���ڵĿ�Ⱥ͸߶�
	     
	    Graphics gOff = offScreenImage.getGraphics();
	    paint(gOff);
	    g.drawImage(offScreenImage, 0, 0, null);
	}  
}
