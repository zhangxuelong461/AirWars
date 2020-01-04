package cn.tedu.shoot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/** 天空背景 */
public class Sky extends FlyingObject{
	//宽第二张天空背景图的y坐标、移动速度
	private int y1;
	private int speed;
	
	/** 构造方法(初始化) */
	public Sky(){
		super(World.WIDTH,World.HEIGHT,0,0);	
		speed = 1;
		y1 = -World.HEIGHT;
	}
	
	/** 重写step()方法 */
	public void step() {
		y += speed; //y+(向下)
		y1 += speed; //y1+(向下)
		if(y>=World.HEIGHT) { //若y>=窗口的高，意味着这张图已经到了最下面了
			y = -World.HEIGHT;  //修改y的值变为负的窗口的高(恢复到原始最上面的那一张图)
		}
		if(y1>=World.HEIGHT) {
			y1 = -World.HEIGHT;
		}
		
	}
	
	/** 重写getImage()获取图片 */
	public BufferedImage getImage() {
		return Images.sky;
	}
	
	/** 重写paintObject()画对象 g:画笔 */
	public void paintObject(Graphics g) {
		g.drawImage(getImage(),x,y,null);
		g.drawImage(getImage(),x,y1,null);
	}
}













