package cn.tedu.shoot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/** 整个游戏世界 */
public class World extends JPanel{
	public static final int WIDTH = 400; //窗口的宽
	public static final int HEIGHT = 700; //窗口的高
	
	public static final int START = 0; //游戏启动状态
	public static final int RUNNING = 1; //游戏运行状态
	public static final int PAUSE = 2; //游戏暂停状态
	public static final int GAME_OVER = 3; //游戏结束状态
	private int state = START; //当前状态默认为启动状态
	
	private Sky sky = new Sky(); //天空对象
	private Hero hero = new Hero(); //英雄机对象
	private FlyingObject[] enemies = {}; //敌人数组
	private Bullet[] bullets = {}; //子弹数组
	
	/** 创建敌人(小敌机、大敌机、小蜜蜂)对象 */
	public FlyingObject nextOne() {
		Random rand = new Random();
		int type = rand.nextInt(20); //0到19
		if(type<5) {
			return new Bee();
		}else if(type<12) {
			return new Airplane();
		}else {
			return new BigAirplane();
		}
	}
	
	
	int enterIndex = 0; //敌人入场计数
	/** 敌人入场 */
	public void enterAction() { //每10毫秒走一次
		enterIndex++; //每10毫秒增1
		if(enterIndex%40==0) { //每400(10*40)毫秒走一次
			FlyingObject obj = nextOne(); //获取敌人对象
			enemies = Arrays.copyOf(enemies, enemies.length+1); //扩容
			enemies[enemies.length-1] = obj;
		}
	}
	
	int shootIndex = 0;
	/** 子弹入场 */
	public void shootAction() { //每10毫秒走一次
		shootIndex++; //每10毫秒增1
		if(shootIndex%30==0) { //每300(10*30)毫秒走一次
			Bullet[] bs = hero.shoot(); //获取英雄机发射
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length); //扩容
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length); //数组的追加
		}
	}
	
	/** 飞行物移动 */
	public void stepAction() { //每10毫秒走一次
		sky.step(); //天空移动
		for(int i=0;i<enemies.length;i++) {
			enemies[i].step(); //敌人（小敌机、大敌机、小蜜蜂）移动
		}
		for(int i=0;i<bullets.length;i++) {
			bullets[i].step(); //子弹动
		}
	}
	
	/** 删除越界的敌人与子弹 */
	public void outOfBoundsAction() {
		int index = 0; //不越界敌人数组下标
		FlyingObject[] enemyLives = new FlyingObject[enemies.length];
		for(int i=0;i<enemies.length;i++) {
			FlyingObject obj = enemies[i];
			if(!obj.outOfBounds() && !obj.isRemove()) { //将不越界的数组存到enemyLives数组中
				enemyLives[index] = obj;
				index++;
			}
		}
		enemies = Arrays.copyOf(enemyLives, index); //将不越界的敌人数组重新赋值给enemies
		
		index = 0; //归零
		Bullet[] bulletLives = new Bullet[bullets.length];
		for(int i=0;i<bullets.length;i++) {
			Bullet b = bullets[i];
			if(!b.outOfBounds() && !b.isRemove()) {
				bulletLives[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index); //将不越界的子弹数组重新赋值给bullets
	}
	
	int score = 0; //玩家得分
	/** 子弹与敌人的碰撞 */
	public void bulletBangAction() {//每10毫秒走一次
		for(int i=0;i<bullets.length;i++) { //遍历所有子弹
			Bullet b = bullets[i];
			for(int j=0;j<enemies.length;j++) { //遍历所有敌人
				FlyingObject f = enemies[j];
				if(f.isLife() && b.isLife() && f.hit(b)) { //撞上了
					f.goDead(); //敌人去死
					b.goDead(); //子弹去死
					if(f instanceof Enemy) {
						Enemy e = (Enemy)f;
						score += e.getScore(); //小敌机、大敌机得分
					}
					if(f instanceof Award) {
						Award a = (Award)f;
						int type = a.getAwardType(); //获取奖励类型
						switch(type) {
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire(); //英雄机得火力
							break;
						case Award.LIFE:
							hero.addLife(); //英雄机增命
							break;
						}
					}
				}
			}
		}
	}
	
	/** 英雄机与敌人碰撞 */
	public void heroBangAction() { //每10毫秒走一次
		for(int i=0;i<enemies.length;i++) { //遍历所有敌人
			FlyingObject f = enemies[i];
			if(f.isLife() && hero.isLife() && f.hit(hero)) {//撞上了
				f.goDead(); //敌人去死
				hero.substractLife(); //英雄机减命
				hero.clearDoubleFire(); //英雄机清空火力值
			}
		}
	}
	
	/** 检测游戏结束 */
	public void checkGameOverAction() {
		if(hero.getLife()<=0) { //游戏结束了
			state = GAME_OVER; //游戏结束将当前状态改为GAME_OVER
		}
	}
	
	/** 启动程序的执行 */
	public void action() {
		MouseAdapter l = new MouseAdapter() {
			/** 重写mouseMoved()鼠标移动事件 */
			public void mouseMoved(MouseEvent e) {
				if(state==RUNNING) {
					int x = e.getX(); //获取鼠标的x坐标
					int y = e.getY(); //获取鼠标的y坐标
					hero.moveTo(x, y); //英雄机随着鼠标移动
				}
			}
			
			/** 重写mouseClicked()鼠标点击事件 */
			public void mouseClicked(MouseEvent e) {
				switch (state) {
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					score = 0; //清理现场
					sky = new Sky();
					hero = new Hero();
					enemies = new FlyingObject[0];
					bullets = new Bullet[0];
					state = START;
					break;
				}
			}
			
			/** 重写mouseExited()鼠标移出事件 */
			public void mouseExited(MouseEvent e) {
				if(state==RUNNING) {
					state = PAUSE;
				}
			}
			
			/** 重写mouseEntered()鼠标移入事件 */
			public void mouseEntered(MouseEvent e) {
				if(state==PAUSE) {
					state = RUNNING;
				}
			}
		};
		this.addMouseListener(l); //处理鼠标操作事件
		this.addMouseMotionListener(l); //处理鼠标滑动事件
		
		Timer timer = new Timer();
		int intervel = 10; //以毫秒为单位
		timer.schedule(new TimerTask() {
			public void run() { //定时干的事（每10毫秒走一次）
				if(state==RUNNING) {
					enterAction(); //敌人入场
					shootAction(); //子弹入场
					stepAction(); //飞行物移动
					outOfBoundsAction(); //删除越界的敌人和子弹
					bulletBangAction(); //子弹与敌人碰撞
					heroBangAction(); //英雄机与敌人碰撞
					checkGameOverAction(); //检测游戏结束
				}
				repaint(); //重画（重新调用paint方法）
			}
		},intervel,intervel); //定时计划
	}
	
	/** 重写paint()类 */
	public void paint(Graphics g) {
		sky.paintObject(g);
		hero.paintObject(g);
		for(int i=0;i<enemies.length;i++) {
			enemies[i].paintObject(g);
		}
		for(int i=0;i<bullets.length;i++) {
			bullets[i].paintObject(g);
		}
		//画分和画命
		g.drawString("SCORE: "+score,10,25);
		g.drawString("LIFE: "+hero.getLife(),10,45);
		g.drawString("DOUBLEFIRE: "+hero.getDoubleFire(),10,65);
		
		//根据当前状态画图片
		switch(state) {
		case START:
			g.drawImage(Images.start,0,0,null);
			break;
		case PAUSE:
			g.drawImage(Images.pause,0,0,null);
			break;
		case GAME_OVER:
			g.drawImage(Images.gameover,0,0,null);
			break;
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH,HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true); //设置窗口可见；尽快调用paint()方法
		
		world.action(); //启动程序的执行
	}
}
