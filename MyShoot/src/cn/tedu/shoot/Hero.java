package cn.tedu.shoot;

import java.awt.image.BufferedImage;

/** 英雄机 */
public class Hero extends FlyingObject {
	//生命值、火力值
	private int life;
	private int doubleFire;
	
	/** 构造方法(初始化) */
	public Hero(){
		super(97,139,140,400);	
		life = 3;
		doubleFire = 0;
	}
	
	/** 英雄机随着鼠标移动
	 * x,y:鼠标的x坐标和y坐标
	 */
	public void moveTo(int x,int y) {
		this.x = x - this.width/2; //让鼠标点在英雄机的正中心
		this.y = y - this.height/2;
	}
	
	/** 重写step()方法 */
	public void step() {
		//英雄机切换图片操作下面方法已经实现了，所以此处不需再次编写
	}
	
	int index = 0;
	/** 重写getImage()获取图片 */
	public BufferedImage getImage() {
		//2就是heros[]数组的长度,对2求余不是0就是1,达到两张图片不断切换的效果
		//对3求余就是0、1、2，对4求余就是0、1、2、3
		//return Images.heros[index++ % 2]; 
		return Images.heros[index++ % Images.heros.length];
	}
	
	/** 英雄机发射子弹(创建子弹) */
	public Bullet[] shoot() {
		int xStep = this.width/4;
		int yStep = 20;
		if(doubleFire>0) {
			//双倍火力(2发子弹)
			Bullet[] bs = new Bullet[2];
			bs[0] = new Bullet(this.x+1*xStep,this.y-yStep);
			bs[1] = new Bullet(this.x+3*xStep,this.y-yStep);
			doubleFire -= 2; //发射一次双倍火力，则火力值减2
			return bs;
		}else {
			//单倍火力(1发子弹)
			Bullet[] bs = new Bullet[1];
			bs[0] = new Bullet(this.x+2*xStep,this.y-yStep); //英雄机子弹的位置为英雄机的宽分为4份
			return bs;
		}
	}
	
	/** 增命 */
	public void addLife() {
		life++; //命数增1
	}
	
	/** 获取命数 */
	public int getLife() {
		return life;
	}
	
	/** 减命 */
	public void substractLife() {
		life--;
	}
	
	/** 增加火力值 */
	public void addDoubleFire() {
		doubleFire += 40; //火力值增40
	}
	
	/** 获取火力值 */
	public int getDoubleFire() {
		return doubleFire;
	}
	
	/** 清空火力值 */
	public void clearDoubleFire() {
		doubleFire = 0;
	}
}
















