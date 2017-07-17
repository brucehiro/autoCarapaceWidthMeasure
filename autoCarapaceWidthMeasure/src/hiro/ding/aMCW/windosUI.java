package hiro.ding.aMCW;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class windosUI extends JFrame{
	private BufferedImage inputImg;
	
	private photoRangeSelection photoRS;
	
	
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
							
				
//		add(photoRS,BorderLayout.CENTER);
		
		setSize(640,480);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new windosUI();
	}

}
