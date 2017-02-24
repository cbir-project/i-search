//GLOBAL COLOR HISTOGRAM

import java.util.Vector;


public class GlobalColorHistogram extends FeatureExtractionModule1
{

    public GlobalColorHistogram()
    {
        _featureName = "GlobalColorHistogram";
        _featureLength = 64;
        _featureDescription = "Get the global color histogram";
    }

//CALCULATE THE FEATURE FOR GLOBAL COLOR HISTOGRAM

    public Vector getFeatureVector(DiscovirImage1 image)
    {
        Vector globalColorHistogram = new Vector(64);
        int width = image.getWidth();
        int height = image.getHeight();
        int size = width * height;
        int pixels[] = image.getPackedRGBPixel();
        double histogram[] = new double[64];
        for(int i = 0; i < 64; i++)
            histogram[i] = 0.0D;

        for(int i = 0; i < size; i++)
        {
            int k = pixels[i] >> 18 & 0x30 | pixels[i] >> 12 & 0xc | pixels[i] >> 6 & 3;
			
            histogram[k]++;
		}
		
        for(int i = 0; i < 64; i++)
            globalColorHistogram.add(new Double(histogram[i] / (double)size));

        return globalColorHistogram;
    }
    
 //FUNCTION TO DISPLAY HISTOGRAM
 
    public int[] getHistogram(DiscovirImage1 image)
    {
    	try
    	{
    	
    if(!image.readImage())
        {
            System.out.println("Error in reading image");
        }
        else
        {
        int width = image.getWidth();
        int height = image.getHeight();
        int size = width * height;
        int pixels[] = image.getPackedRGBPixel();
        
        int histogram[] = new int[64];
           for(int i = 0; i < 64; i++)
            histogram[i] = 0;

        for(int i = 0; i < size; i++)
        {
           int k = pixels[i] >> 18 & 0x30 | pixels[i] >> 12 & 0xc | pixels[i] >> 6 & 3;
            histogram[k]++;			      
        }
    //Get the maximum value, exclusive of the
    // values at 0 and 64
    int max = 0;
    for(int cnt = 1;cnt < 63;cnt++){
      if(histogram[cnt] > max){
        max = histogram[cnt];
      }//end if
    }

    //Normalize histogram to a peak value of 100
    // based on the max value, exclusive of the
    // values at 0 and 64
      for(int cnt = 0;cnt < 64;cnt++){
      histogram[cnt] = 100 * histogram[cnt]/max;
      }
      return histogram;	
        }
    	}
    	catch(Exception e)
    	{
    	}
    	return null;
    }	
    

   
}