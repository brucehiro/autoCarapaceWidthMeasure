package hiro.ding.aMCW;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

public class autoMeasureCarapaceWidth {
	private BufferedImage inputCarapaceImg;
	private BufferedImage inputBackgroundImg;
	private BufferedImage inputChelateImg;
	private BufferedImage inputTestImg;
	private double carapaceRedPercentageMean;
	private double carapaceRedPercentageSS;
	private double backgroundRedPercentageMean;
	private double backgroundRedPercentageSS;
	private double chelateRedPercentageMean;
	private double chelateRedPercentageSS;
	private double testRedPercentageMean;
	private double testRedPercentageSS;
	private String result;
		
	public autoMeasureCarapaceWidth(BufferedImage inputTestImg) 
	{
		try 
		{
	        inputCarapaceImg=ImageIO.read(new File("D:\\crabCarapace\\carapace_standard.JPG"));
	        inputBackgroundImg=ImageIO.read(new File("D:\\crabCarapace\\background_standard.JPG"));
	        inputChelateImg=ImageIO.read(new File("D:\\crabCarapace\\chelate_standard.JPG"));
	        this.inputTestImg=inputTestImg;
	        
	        
	        carapaceRedPercentageMean = measureMean(inputCarapaceImg);	        
	        backgroundRedPercentageMean = measureMean(inputBackgroundImg);	        
	        testRedPercentageMean = measureMean(inputTestImg);
	        chelateRedPercentageMean = measureMean(inputChelateImg);
	        carapaceRedPercentageSS = measureSS(inputCarapaceImg, carapaceRedPercentageMean);	        
	        backgroundRedPercentageSS = measureSS(inputBackgroundImg, backgroundRedPercentageMean);
	        chelateRedPercentageSS = measureSS(inputChelateImg, chelateRedPercentageMean);
	        testRedPercentageSS = measureSS(inputTestImg, testRedPercentageMean);	    
	        
	        result=isCarapace(testRedPercentageMean, testRedPercentageSS, inputTestImg.getHeight()*inputTestImg.getWidth(), carapaceRedPercentageMean, carapaceRedPercentageSS, inputCarapaceImg.getHeight()*inputCarapaceImg.getWidth(),backgroundRedPercentageMean, backgroundRedPercentageSS, inputBackgroundImg.getHeight()*inputBackgroundImg.getWidth(), chelateRedPercentageMean, chelateRedPercentageSS, inputChelateImg.getHeight()*inputChelateImg.getWidth());

	    } 
		catch (IOException ex) 
		{
			System.out.println(ex.toString());
		}
	}
	
	private double measureMean(BufferedImage inputImg)
	{
		float RedMean = 0;
		float GreenMean = 0;
		float BlueMean = 0;
		double RedPercentage;
		
		
		for(int i=0; i<inputImg.getWidth(); i++)
        {
        	for(int j=0; j<inputImg.getHeight(); j++)
        	{
        		RedMean += new Color(inputImg.getRGB(i, j)).getRed();
        		GreenMean += new Color(inputImg.getRGB(i, j)).getGreen();
        		BlueMean += new Color(inputImg.getRGB(i, j)).getBlue();       		
        	}
        }
        
        RedMean /= inputImg.getWidth()*inputImg.getHeight();        
        GreenMean /= inputImg.getWidth()*inputImg.getHeight();        
        BlueMean /= inputImg.getWidth()*inputImg.getHeight();
        
        RedPercentage = RedMean/(RedMean+GreenMean+BlueMean);        
               
        return RedPercentage;
	}
	
	private double measureSS(BufferedImage inputImg, double RedPercentageFromStandard)
	{
		double Red;
		double Green;
		double Blue;
		double RedPercentage = 0;
		double RedPercentageSS = 0;		
				
		for(int i=0; i<inputImg.getWidth(); i++)
        {
        	for(int j=0; j<inputImg.getHeight(); j++)
        	{
        		Red = new Color(inputImg.getRGB(i, j)).getRed()+1;        		
        		Green = new Color(inputImg.getRGB(i, j)).getGreen()+1;         		
        		Blue = new Color(inputImg.getRGB(i, j)).getBlue()+1;         		
        		RedPercentage = Red/(Red+Green+Blue);      		
        		RedPercentageSS += Math.pow(RedPercentage-RedPercentageFromStandard, 2); 
        	}
        }
				
		RedPercentageSS /= (inputImg.getWidth()*inputImg.getHeight()-1);
						                       
		return RedPercentageSS;		
	}
	
	String isCarapace(double testRedPercentageMean, double testRedPercentageSS, int testN, double carapaceRedPercentageMean, double carapaceRedPercentageSS, int carapaceN, double backgroundRedPercentageMean, double backgroundRedPercentageSS, int backgroundN, double chelateRedPercentageMean, double chelateRedPercentageSS, int chelateN)
	{
		double tInCarapaceRedPercentage;
		double tInBackgroundRedPercentage;
		double tInChelateRedPercentage;
		
		tInCarapaceRedPercentage = 	Math.abs(testRedPercentageMean-carapaceRedPercentageMean)/Math.pow((testRedPercentageSS/testN)+(carapaceRedPercentageSS/carapaceN), 0.5);
		tInBackgroundRedPercentage = Math.abs(testRedPercentageMean-backgroundRedPercentageMean)/Math.pow((testRedPercentageSS/testN)+(backgroundRedPercentageSS/backgroundN), 0.5);
		tInChelateRedPercentage = Math.abs(testRedPercentageMean-chelateRedPercentageMean)/Math.pow((testRedPercentageSS/testN)+(chelateRedPercentageSS/chelateN), 0.5);
		
		System.out.println(tInCarapaceRedPercentage);
		System.out.println(tInBackgroundRedPercentage);
		System.out.println(tInChelateRedPercentage);
										
		if(tInCarapaceRedPercentage-tInBackgroundRedPercentage > 0)
		{
			return "Background";
		}
		else
		{
			return "Carapace";
		}		
	}
	
	String getResult()
	{
		return result;
	}
}
