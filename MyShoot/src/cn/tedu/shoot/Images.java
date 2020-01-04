package cn.tedu.shoot;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/** 图片工具类 */
public class Images {
	public static BufferedImage sky;
	public static BufferedImage bullet;
	public static BufferedImage[] heros;
	public static BufferedImage[] airplanes;
	public static BufferedImage[] bigairplanes;
	public static BufferedImage[] bees;
	public static BufferedImage start; //启动状态图片
	public static BufferedImage pause; //停止状态图片
	public static BufferedImage gameover; //结束状态图片
	
	//初始化图片资源
	static {
		start = loadImage("start.png");
		pause = loadImage("pause.png");
		gameover = loadImage("gameover.png");
		
		//天空图片
		sky = loadImage("background.png");
		//子弹图片
		bullet = loadImage("bullet.png");
		//英雄机两张图片
		heros = new BufferedImage[2];
		heros[0] = loadImage("hero0.png");
		heros[1] = loadImage("hero1.png");
		//小敌机、大敌机、小蜜蜂 5张图片(一张本身图，4张爆破图)
		airplanes = new BufferedImage[5];
		bigairplanes = new BufferedImage[5];
		bees = new BufferedImage[5];
		airplanes[0] = loadImage("airplane0.png");
		bigairplanes[0] = loadImage("bigairplane0.png");
		bees[0] = loadImage("bee0.png");
		for(int i=1;i<airplanes.length;i++) {
			airplanes[i] = loadImage("bom"+i+".png");
			bigairplanes[i] = loadImage("bom"+i+".png");
			bees[i] = loadImage("bom"+i+".png");
		}
		
	}
	/** 读取图片 */
	public static BufferedImage loadImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
