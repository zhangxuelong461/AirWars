package cn.tedu.shoot;
import java.awt.image.BufferedImage;

/** 小敌机 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed;	//移动速度
	
	/** 构造方法(初始化) */
	public Airplane() {
		super(48,50);
		speed = 2;
	}
	/** 重写step()方法 */
	public void step() {
		y += speed; //y+(向下)
	}
	
	int index = 1;
	/** 重写getImage()获取图片，每10毫秒执行一次 */
	public BufferedImage getImage() {
		if(isLife()) {
			//若活着，直接返回第一张图片
			return Images.airplanes[0];
		}else if(isDead()) {
			//若死了的，返回第2张到第5张图片的轮换（4张爆破图）5后再删除
			BufferedImage img = Images.airplanes[index++];
			if(index==Images.airplanes.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	
	/** 重写接口方法 */
	public int getScore() {
		return 1; //打掉小敌机，玩家得1分
	}
	
}
