package hiro.ding.aMCW;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class categoryIdentify  {
	//0:carapace; 1:background; 2:chelate; 3:scale-Yellow
	private BufferedImage[] inputStandardImg = new BufferedImage[4];
	private BufferedImage inputTestImg;
	private double[][] colorPercentageMean = new double[inputStandardImg.length][3];
	double[] testColorPercentageMean = new double[3];
	private double[][] colorPercentageSS = new double[inputStandardImg.length][3];
	double[] testColorPercentageSS = new double[3];			
	File inputTestImgFile;
	String result;
					
	categoryIdentify(File inputTestImgFile)
	{
		this.inputTestImgFile = inputTestImgFile;
		
		try 
		{
	        inputStandardImg[0]=ImageIO.read(new File("D:\\crabCarapace\\carapace_standard.JPG"));
	        inputStandardImg[1]=ImageIO.read(new File("D:\\crabCarapace\\background_standard.JPG"));
	        inputStandardImg[2]=ImageIO.read(new File("D:\\crabCarapace\\chelate_standard.JPG"));
	        inputStandardImg[3]=ImageIO.read(new File("D:\\crabCarapace\\scaleY_standard.JPG"));
	        	       	        	        	        		
	        for(int l=0; l<inputStandardImg.length; l++)
	        {
	        	colorPercentageMean[l] = measureMean(inputStandardImg[l]);
	        	System.out.println(colorPercentageMean[l][0] + " " + colorPercentageMean[l][1] + " " + colorPercentageMean[l][2]);
	        	colorPercentageSS[l] = measureSS(inputStandardImg[l], colorPercentageMean[l]);
	        }
	        	        
	        inputTestImg = ImageIO.read(inputTestImgFile);
	        testColorPercentageMean = measureMean(inputTestImg);
	        System.out.println(testColorPercentageMean[0]+ " "+ testColorPercentageMean[1]+" "+testColorPercentageMean[2]);
	        testColorPercentageSS = measureSS(inputTestImg, testColorPercentageMean);

	    } 
		catch (IOException ex) 
		{
			System.out.println(ex.toString());
		}
	}
		
	double[] measureMean(BufferedImage inputImg)
	{
		float redMean = 0;
		float greenMean = 0;
		float blueMean = 0;
		double[] colorPercentageMean = new double[4];
		
		for(int i=0; i<inputImg.getWidth(); i++)
        {
        	for(int j=0; j<inputImg.getHeight(); j++)
        	{
        		redMean += new Color(inputImg.getRGB(i, j)).getRed()+1;
        		greenMean += new Color(inputImg.getRGB(i, j)).getGreen()+1;
        		blueMean += new Color(inputImg.getRGB(i, j)).getBlue()+1;       		
        	}
        }
        
        redMean /= inputImg.getWidth()*inputImg.getHeight();        
        greenMean /= inputImg.getWidth()*inputImg.getHeight();        
        blueMean /= inputImg.getWidth()*inputImg.getHeight();
        
        colorPercentageMean[0] = redMean/(redMean+greenMean+blueMean);
        colorPercentageMean[1] = greenMean/(redMean+greenMean+blueMean);
//        colorPercentageMean[2] = blueMean/(redMean+greenMean+blueMean);
        colorPercentageMean[2] = redMean / blueMean;
                      
        return colorPercentageMean;
	}
	
	double[] measureSS(BufferedImage inputImg, double[] colorPercentageFromStandard)
	{
		double[] colors = new double[4];
		double colorsSum;
		double[] colorPercentage = new double[4];
		double[] colorPercentageSS = new double[4];
				
		for(int i=0; i<inputImg.getWidth(); i++)
        {
        	for(int j=0; j<inputImg.getHeight(); j++)
        	{
        		colors[0] = new Color(inputImg.getRGB(i, j)).getRed()+1;        		
        		colors[1] = new Color(inputImg.getRGB(i, j)).getGreen()+1;         		
        		colors[2] = new Color(inputImg.getRGB(i, j)).getBlue()+1;
        		
        		colorsSum = colors[0] + colors[1] + colors[2];
        		
        		for(int k=0; k<colorPercentage.length; k++)
        		{
        			if(k<2)
        			{
        				colorPercentage[k] = colors[k]/colorsSum;
        			}
        			else
        			{
        				colorPercentage[k] = colors[0]/colors[2];
        			}
        			colorPercentageSS[k] += Math.pow(colorPercentage[k]-colorPercentageFromStandard[k], 2);
        		}        		       		              		       		        		
        	}
        }
		
		for(int i=0; i<colorPercentageSS.length; i++)
		{
			colorPercentageSS[i] /= (inputImg.getWidth()*inputImg.getHeight()-1);
		}
								                       
		return colorPercentageSS;		
	}
	
	String getCategoryType()
	{
		final int COLOR_TYPE = 3;
		double[] tValue = new double[inputStandardImg.length];
		int[] categoryN = new int[inputStandardImg.length];
		int testN = inputTestImg.getHeight()*inputTestImg.getWidth();
		
		for(int i=0; i<categoryN.length; i++)
		{
			categoryN[i] = inputStandardImg[i].getWidth()*inputStandardImg[i].getHeight();
		}
				
		for(int i=0,k=0; i<inputStandardImg.length; i++,k++)
		{
			for(int j=0; j<COLOR_TYPE; j++)
			{
				tValue[k] += Math.abs(testColorPercentageMean[j]-colorPercentageMean[i][j])/Math.pow((testColorPercentageSS[j]/testN)+(colorPercentageSS[i][j]/categoryN[i]), 0.5);					
			}
		}
											
		double least = 1000000;
		int resultCategory = 0;
		for(int i=0; i<tValue.length; i++)
		{
			if(least>tValue[i])
			{
				least = tValue[i];
				resultCategory=i;
			}			
		}
		
		switch(resultCategory)
		{
			case 0:
				return "Carapace";				
			case 1:
				return "Background";
			case 2:
				return "Chelate";
			case 3:
				return "Scale";
			default:
				break;
		}
		
		return "";
		
	}
	
	BufferedImage[] getBufferedImageArray()
	{
		return inputStandardImg;
	}
	
	double[][] getColorPercentageMean()
	{
		return colorPercentageMean;
	}
	
	double[][] getColorPercentageSS()
	{
		return colorPercentageSS;
	}
	
	double[] getTestColorPercentageMean()
	{
		return testColorPercentageMean;
	}
	
	double[] getTestColorPercentageSS()
	{
		return testColorPercentageSS;
	}
	
	BufferedImage getInputTestBufferedImage()
	{
		return inputTestImg;
	}
			
}
