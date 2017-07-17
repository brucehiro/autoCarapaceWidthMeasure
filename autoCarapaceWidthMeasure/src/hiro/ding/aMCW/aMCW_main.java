package hiro.ding.aMCW;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class aMCW_main {

	public static void main(String[] args) {				
		double[] testColorPercentageMean = new double[3];
		double[] testColorPercentageSS = new double[3];
		String result;
		File inputTestImg = new File("D:\\crabCarapace\\smileTest2.JPG");
		categoryIdentify cI = new categoryIdentify(inputTestImg);
		
	    result=cI.getCategoryType();
	    System.out.println(result);    

		
	}
}
