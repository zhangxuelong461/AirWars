package cn.tedu.shoot;

import java.util.Random;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

/** 飞行物(父类) 
 * 将小敌机、大敌机、小蜜蜂、子弹重复的宽、高、x坐标、y坐标、移动函数等属性方法写在父类中
 */
public abstract class FlyingObject {
	//获取图片前判断对象的状态
	public static final int LIFE = 0; //活着的
	public static final int DEAD = 1; //死了的
	public static final int REMOVE = 2; //删除的
	protected int state = LIFE; //当前状态(默认为活着的)
	
	//宽、高、x坐标、y坐标、
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	
	/** 专门给小敌机、大敌机、小蜜蜂提供的 */
	public FlyingObject(int width,int height){
		this.width = width;
		this.height = height;	
		Random rand = new Random(); //随机数对象
		x = rand.nextInt(World.WIDTH-this.width); //x：窗口的宽-小敌机的宽
		y = -this.height; //y：负的小敌机的高
	}
	
	/** 专门给英雄机、天空、子弹提供的 */
	public FlyingObject(int width,int height,int x,int y){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	/** 飞行物移动(没任何意义，设置为抽象类，子类中此方法已全部重写) */
	public abstract void step();
	
	/** 获取对象的图片(抽象方法) */
	public abstract BufferedImage getImage();
	
	/** 判断是否是活着的 */
	public boolean isLife() {
		//若当前状态为LIFE，则返回true，否则返回false
		return state==LIFE;
	}
	
	/** 判断是否是死了的 */
	public boolean isDead() {
		//若当前状态为DEAD，则返回true，否则返回false
		return state==DEAD;
	}
	
	/** 判断是否是删除的 */
	public boolean isRemove() {
		//若当前状态为REMOVE，则返回true，否则返回false
		return state==REMOVE;
	}
	
	/** 画对象 g:画笔 */
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
	}
	
	/** 验证敌人是否越界(以便将越界的删除) */
	public boolean outOfBounds() {
		return this.y>=World.HEIGHT; //敌人的y坐标>=窗口的高
	}
	
	/** 碰撞检测 this:敌人 other:子弹/英雄机 */
	public boolean hit(FlyingObject other) { 
		int x1 = this.x - other.width;
		int x2 = this.x + this.width;
		int y1 = this.y - other.height;
		int y2 = this.y + this.height;
		int x = other.x;
		int y = other.y;
		
		return x>=x1 && x<=x2 && y>=y1 && y<=y2;
	}
	
	/** 相撞之后，飞行物去死 */
	public void goDead() {
		state = DEAD; //修改对象状态为DEAD（死了的）
	}
}










