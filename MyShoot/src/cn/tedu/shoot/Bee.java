package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;

/** 小蜜蜂 */
public class Bee extends FlyingObject implements Award{
	//x坐标移动速度、y坐标移动速度、奖励类型
	private int xSpeed;
	private int ySpeed;
	private int awardType;
	
	/** 构造方法(初始化) */
	public Bee() {
		super(60,51);
		xSpeed = 1;
		ySpeed = 2;
		Random rand = new Random();
		awardType = rand.nextInt(2); //0或1
	}
	/** 重写step()方法 */
	public void step() {
		x += xSpeed; //x+(向左或向右,如果xSpeed为正向右，如果xSpeed为负则向左)
		y += ySpeed; //y+(向下)
		if(x<=0 || x>=World.WIDTH-this.width) {
			xSpeed *= -1;
		}
	}
	
	int index = 1;
	/** 重写getImage()获取图片 */
	public BufferedImage getImage() {
		if(isLife()) {
			//若活着，直接返回第一张图片
			return Images.bees[0];
		}else if(isDead()) {
			//若死了的，返回第2张到第5张图片的轮换（4张爆破图）5后再删除
			BufferedImage img = Images.bees[index++];
			if(index==Images.bees.length) {
				state = REMOVE;
			}
			return img;
		}
		return null;
	}
	
	/** 重写接口方法 */
	public int getAwardType() {
		return awardType; //返回奖励类型
	}
}
