package hiro.ding.aMCW;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class photoRangeSelection extends JPanel{
	private BufferedImage inputImg;
	private BufferedImage[][] subInputImg = new BufferedImage[3][3];
	private File imgOutputFile;
	private Ellipse2D.Double ellipse;
	
	photoRangeSelection(BufferedImage inputImg)
	{				
		this.inputImg=inputImg;		

		for(int i=0,k=0; i<inputImg.getWidth(); i+=inputImg.getWidth()/3,k++)
		{
			for(int j=0,l=0; j<inputImg.getHeight(); j+=inputImg.getHeight()/3,l++)
			{
				subInputImg[k][l]=inputImg.getSubimage(i, j, inputImg.getWidth()/3, inputImg.getHeight()/3);
				//					imgOutputFile = new File("D:\\crabCarapace\\sub" + (i+j) + ".JPG");
				//					ImageIO.write(subInputImg[k][l], "jpg", imgOutputFile);
			}
		}					
	}
	
	BufferedImage[][] getSubImgs()
	{
		return subInputImg;
	}
}
