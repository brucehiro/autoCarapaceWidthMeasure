package hiro.ding.aMCW;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class photoRangeSelection extends JPanel{
	private BufferedImage inputImg;
	private BufferedImage[][] subInputImg = new BufferedImage[3][3];
	private BufferedImage[][] sSubInputImg = new BufferedImage[9][9];
	private categoryIdentify photoCI = new categoryIdentify();
	private String[] subInputImgStringArray = new String[9];
	private int carapaceCounter = 0;
	private int backgroundCounter = 0;
	private int chelateCounter = 0;
	private int scaleYCounter = 0;
	
	photoRangeSelection(BufferedImage inputImg)
	{				
		this.inputImg = inputImg;		
		subInputImg = splitImg(inputImg);

		
	}
	
	BufferedImage[][] splitImg(BufferedImage inputImg)
	{
		BufferedImage[][] subInputImg = new BufferedImage[3][3];
		
		for(int i=0,k=0; i<inputImg.getWidth(); i+=inputImg.getWidth()/3,k++)
		{
			for(int j=0,l=0; j<inputImg.getHeight(); j+=inputImg.getHeight()/3,l++)
			{
				subInputImg[k][l]=inputImg.getSubimage(i, j, inputImg.getWidth()/3, inputImg.getHeight()/3);
				
			}
		}
		
		return subInputImg;
	}
	
	BufferedImage[][] getSubImgs()
	{
		return subInputImg;
	}
}
