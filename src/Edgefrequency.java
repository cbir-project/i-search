//EDGE FREQUENCY

import java.util.*;


public class Edgefrequency extends FeatureExtractionModule1 {


	public Edgefrequency() {
		super();
		this._featureName="Edgefrequency1";
		this._featureLength=25;
		this._featureDescription="Get the edge frequency.";

	};
	
	//FUNCTION TO CONVERT RGB VALUES TO GREYSCALEVALUES
    public static int[] RGB2grayscale(int rgb[], int level)
    {
        int Y[] = new int[rgb.length];
        int i = 0;
        for(i = 0; i < rgb.length; i++)
            Y[i] = (int)(((0.29899999999999999D * (double)(rgb[i] >> 16 & 0xff) + 0.58699999999999997D * (double)(rgb[i] >> 8 & 0xff) + 0.114D * (double)(rgb[i] & 0xff)) / 256D) * (double)level + 0.5D);

        return Y;
    }
    
   //FUNCTION TO CALCULATE TEXTURE
	public Vector getFeatureVector(DiscovirImage1 image) {
		Vector edgeFrequency=new Vector(25);

		int i=0, k=0, up=0, down=0, left=0, right=0;
		double texture=0;
		int width=image.getWidth();
		int height=image.getHeight();
		int size=width*height;
		int[] pixels=image.getPackedRGBPixel();
		pixels = RGB2grayscale(pixels, 256);

		int[] gradient = new int[size];           
		for(i=1 ; i<50 ; i+=2){                           //CALULATE TEXTURE FOR 25 ODD DISTANCES 
			for(k=0 ; k<size ; k++){                      //CALCULATE EDGE GRADIENT 4 DIRECTIONS

				if(k >= i*width)
					up = Math.abs(pixels[k]-pixels[k-i*width]);
				else
					up = 0;

				if(((width*height)-k) > (i*width))
					down = Math.abs(pixels[k]-pixels[k+i*width]);
				else
					down = 0;

				if(i < (width-(k%width)))
					right = Math.abs(pixels[k]-pixels[k+i]);
				else
					right = 0;

				if(i <= (k%width))
					left = Math.abs(pixels[k]-pixels[k-i]);
				else
					left = 0;

				gradient[k]=up+down+right+left;
			}

			for(k=0 ; k<size ; k++)
				texture = texture + gradient[k];

			texture = texture/size;                     
			edgeFrequency.add(new Double(texture));
		}

		return edgeFrequency;          
	}

public double getFeatureValues(DiscovirImage1 image) {
		int i=0, k=0, up=0, down=0, left=0, right=0;
		double texture=0;
		int width=image.getWidth();
		int height=image.getHeight();
		int size=width*height;
		int[] pixels=image.getPackedRGBPixel();
		pixels = RGB2grayscale(pixels, 256);
        double text=0;
		int[] gradient = new int[size];           
		for(i=1 ; i<50 ; i+=2){                           //CALULATE TEXTURE FOR 25 ODD DISTANCES 
			for(k=0 ; k<size ; k++){                      //CALCULATE EDGE GRADIENT 4 DIRECTIONS

				if(k >= i*width)
					up = Math.abs(pixels[k]-pixels[k-i*width]);
				else
					up = 0;

				if(((width*height)-k) > (i*width))
					down = Math.abs(pixels[k]-pixels[k+i*width]);
				else
					down = 0;

				if(i < (width-(k%width)))
					right = Math.abs(pixels[k]-pixels[k+i]);
				else
					right = 0;

				if(i <= (k%width))
					left = Math.abs(pixels[k]-pixels[k-i]);
				else
					left = 0;

				gradient[k]=up+down+right+left;
			}

			for(k=0 ; k<size ; k++)
				texture = texture + gradient[k];
				 
		text+=(texture/size);                    
		}
		text=(text/25);
		
		return text;      
	}
 }
