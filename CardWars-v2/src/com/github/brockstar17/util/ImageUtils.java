package com.github.brockstar17.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.github.brockstar17.CardWars;

public class ImageUtils
{
	public static BufferedImage scale(BufferedImage img, int dWidth, int dHeight) {
		BufferedImage scaledImage = null;
		if(img != null)
		{
			scaledImage = new BufferedImage(dWidth, dHeight, img.getType());
			Graphics2D graphics2D = scaledImage.createGraphics();
			graphics2D.drawImage(img, 0, 0, dWidth, dHeight, null);
			graphics2D.dispose();
		}
		return scaledImage;
	}

	public static int calcWidth(double h, double nh, double w) {

		if(h < nh) // image being scaled up
		{

			double per = (nh - h) / h;
			return (int) (w * per + w);
		}
		else if(h > nh) // image being scale down
		{
			double per = (h - nh) / h;
			per *= -1;
			return (int) (w * per + w);
		}
		else
		{
			return (int) w;
		}

	}
	
	public static Dimension sizeBoard(Dimension s, CardWars cw){
		boolean sized = false;
		double sx = s.getWidth(), sy = s.getHeight();
		int nx = 0, ny = 0;
		
		double scaleBy = sy - 80;
		
		while(!sized)
		{
			nx = calcWidth(cw.BOARDHEIGHT, scaleBy, cw.BOARDWIDTH);
			
			if((nx + 10) < sx)
			{
				sized = true;
				ny = (int) scaleBy;
			}
			else
			{
				scaleBy -= 10;
			}
			
		}
		
		return new Dimension(nx, ny);
	}
}
