package hiro.ding.aMCW;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class windosUI extends JFrame{
	private BufferedImage inputImg;
	private BufferedImage[][] inputImgSelectedArea = new BufferedImage[3][3];
	private photoRangeSelection photoRS;
	private autoMeasureCarapaceWidth photoAMCW;
	
	windosUI()
	{
		setLayout(new BorderLayout());
		try
		{
			inputImg=ImageIO.read(new File("D:\\crabCarapace\\P7100673.JPG"));
		}
		catch(IOException ee)
		{
			System.out.println(ee.toString());
		}
		
		photoRS = new photoRangeSelection(inputImg);
		inputImgSelectedArea = photoRS.getSubImgs();
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				photoAMCW = new autoMeasureCarapaceWidth(inputImgSelectedArea[i][j]);
				System.out.println(photoAMCW.getResult());
			}
		}
		
		
		
				
//		add(photoRS,BorderLayout.CENTER);
		
		setSize(640,480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new windosUI();
	}

}
