package cn.tedu.shoot;
import java.awt.image.BufferedImage;

/** 大敌机 */
public class BigAirplane extends FlyingObject implements Enemy{
	//移动速度	
	private int speed;
	
	/** 构造方法(初始化) */
	public BigAirplane() {
		super(66,89);
		speed = 2;
	}
	/** 重写step()方法 */
	public void step() {
		y += speed; //y+(向下)
	}	
	
	int index = 1;
	/** 重写getImage()获取图片 */
	public BufferedImage getImage() {
		if(isLife()) {
			//若活着，直接返回第一张图片
			return Images.bigairplanes[0];
		}else if(isDead()) {
			//若死了的，返回第2张到第5张图片的轮换（4张爆破图）5后再删除
			BufferedImage img = Images.bigairplanes[index++];
			if(index==Images.bigairplanes.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	
	/** 重写接口方法 */
	public int getScore() {
		return 3; //打掉大敌机，玩家得3分
	}
}
