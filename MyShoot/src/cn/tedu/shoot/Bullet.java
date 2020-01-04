package cn.tedu.shoot;

import java.awt.image.BufferedImage;

/** 子弹 */
public class Bullet extends FlyingObject{
	//移动速度
	private int speed;
	
	/** 构造方法(初始化) */
	public Bullet(int x,int y) {
		super(8,20,x,y);	
		speed = 3;
	}
	/** 重写 */
	public void step() {
		y -= speed; //y-(向上)
	}
	
	/** 重写getImage()获取图片 */
	public BufferedImage getImage() {
		if(isLife()) {
			//若活着，直接返回bullet即可
			return Images.bullet;
		}else if(isDead()) {
			state = REMOVE;
		}
		//若死了的，把状态改为REMOVE直接删除，返回null类型
		return null;
	}
	
	/** 验证子弹是否越界(重写父类方法) */
	public boolean outOfBounds() {
		return this.y<=-this.height; //子弹的y坐标<=-子弹的高
	}
}
