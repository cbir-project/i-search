//COOCCURANCE


import java.util.Vector;
public class Cooccurence extends FeatureExtractionModule1
{

    public Cooccurence()
    {
        _featureName = "Cooccurence";
        _featureLength = 20;
        _featureDescription = "Get the coocurence.";
    }
    
    //FUNCTION TO CONVERT RGB VALUES TO GREYSCALEVALUES 
    public static int[] RGB2grayscale(int rgb[], int level)
    {
        int Y[] = new int[rgb.length];
        int i = 0;
        for(i = 0; i < rgb.length; i++)
            Y[i] = (int)(((0.29899999999999999D * (double)(rgb[i] >> 16 & 0xff) + 0.58699999999999997D * (double)(rgb[i] >> 8 & 0xff) + 0.114D * (double)(rgb[i] & 0xff)) / 256D) * (double)level + 0.5D);

        return Y;
    }
    
    // FUNCTION CALCULATES FEATURE VECTOR  
    public Vector getFeatureVector(DiscovirImage1 discovirimage)
    {
        double ad[][] = new double[4][];
        Vector vector = new Vector(_featureLength);
        for(int i = 0; i < 4; i++)
            ad[i] = getCooccurenceMatrix(discovirimage, XYSHIFT[i][0], XYSHIFT[i][1]); //GETS THE COOCCURENCE MATRIX FOR 4 DISTANCES

        for(int j = 0; j < 4; j++)                                                     //CALCULATE FEATURES FOR 4 DISTANCES
        {
            vector.add(new Double(analyseMatrix(ad[j], 1))); //DEFAULT CASE
            vector.add(new Double(analyseMatrix(ad[j], 2))); //CONTRAST
            vector.add(new Double(analyseMatrix(ad[j], 3))); //HOMOGENITY
            vector.add(new Double(analyseMatrix(ad[j], 4))); //ENTROPY
            vector.add(new Double(analyseMatrix(ad[j], 5))); //ENERGY
        }
        
        return vector;                                                               
    }
    
    //FUNCTION TO CALCULATE THE COOCCURENCE MATRIX
    private double[] getCooccurenceMatrix(DiscovirImage1 discovirimage, int i, int j)
    {
        int i2 = 0;
        int l2 = discovirimage.getWidth();
        int i3 = discovirimage.getHeight();
        int ai[] = discovirimage.getPackedRGBPixel();
        int ai1[] =RGB2grayscale(ai, 256);
        double ad[] = new double[0x10000];
        for(int k = 0; k < 256; k++)
        {
            for(int j1 = 0; j1 < 256; j1++)
                ad[k * 256 + j1] = 0.0D;

        }

        for(int l = 0; l < i3; l++)
        {
            for(int k1 = 0; k1 < l2; k1++)
                if(k1 + i < l2 && k1 + i >= 0 && l + j < i3 && l + j >= 0)
                {
                    int j2 = ai1[l * l2 + k1];
                    int k2 = ai1[(l + j) * l2 + k1 + i];
                    ad[j2 * 256 + k2]++;
                    i2++;
                }

        }

        for(int i1 = 0; i1 < 256; i1++)
        {
            for(int l1 = 0; l1 < 256; l1++)
                ad[i1 * 256 + l1] = ad[i1 * 256 + l1] / (double)i2;

        }

        return ad; 
    }
    
    // FUNCTION TO CALCULATE FEATURES
    public double analyseMatrix(double ad[], int i)
    {
        double d = 0.0D;
        switch(i)
        {
        default:
            break;

        case 1: // DEFAULT CASE FOR SUMMATION
            for(int j = 0; j < 256; j++)
            {
                for(int k2 = 0; k2 < 256; k2++)
                    if(ad[j * 256 + k2] > d)
                        d = ad[j * 256 + k2];
            }

            break;


        case 2: // CONTRAST
            for(int l = 0; l < 256; l++)
            {
                for(int i3 = 0; i3 < 256; i3++)
                    d += ad[l * 256 + i3] * Math.pow((double)Math.abs(l - i3), 2D);

            }

            break;

        case 3: // HOMOGENITY
            for(int j1 = 0; j1 < 256; j1++)
            {
                for(int k3 = 0; k3 < 256; k3++)
                    d += ad[j1 * 256 + k3] / (double)(1 + Math.abs(j1 - k3));

            }

            break;

        case 4: // ENTROPY
            for(int i2 = 0; i2 < 256; i2++)
            {
                for(int j4 = 0; j4 < 256; j4++)
                    if(ad[i2 * 256 + j4] != 0.0D)
                        d += ad[i2 * 256 + j4] * Math.log(ad[i2 * 256 + j4]);

            }

            d = -d;
            break;

        case 5: // ENERGY
            for(int j2 = 0; j2 < 256; j2++)
            {
                for(int k4 = 0; k4 < 256; k4++)
                    d += ad[j2 * 256 + k4] * ad[j2 * 256 + k4];

            }

            break;
        }
        return d;
    }
    
 //FUNCTION TO DISPLAY VALUES   
    public double getFeatureValues(DiscovirImage1 discovirimage,String x)
    {
        double ad[][] = new double[4][];
        double energy=0.0D,entropy=0.0D,contrast=0.0D,homogenity=0.0D;
        double d=0;;
    
        for(int i = 0; i < 4; i++)
            ad[i] = getCooccurenceMatrix(discovirimage, XYSHIFT[i][0], XYSHIFT[i][1]);

        for(int j = 0; j < 4; j++)
        {
            d+=analyseMatrix(ad[j], 1);
        }
        for(int j = 0; j < 4; j++)
        {
        	contrast+=analyseMatrix(ad[j],2);
            energy+=analyseMatrix(ad[j], 5);
            entropy+=analyseMatrix(ad[j], 4);
            homogenity+=analyseMatrix(ad[j],3 );
        }
        if(x=="contrast")
        {
        	contrast=(d+contrast)/4;
        	return contrast;
        }
        if(x=="homogenity")
        {
        	homogenity=(d+homogenity)/4;
        	return homogenity;
        }
        if(x=="energy")
        {
        	energy=(d+energy)/4;
        	return energy;
        }
        if(x=="entropy")
        {
        	entropy=(d+entropy)/4;
        	return entropy;
        }
        return 0;     
        
     }

    final int GRAY_LEVEL = 256;
    final int NUM_TYPE = 5;
    final int NUM_SHIFT = 4;
    final int XYSHIFT[][] = {
        {
            1, 1       //AT DISTANCE 1
        }, {
            4, 4       //AT DISTANCE 2
        }, {
            9, 9       //AT DISTANCE 3
        }, {
            16, 16     //AT DISTANCE 4
        }
    };
}