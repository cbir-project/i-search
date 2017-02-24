//THIS IS THE ABSTRACT CLASS EXTENDED BY ALL TECHNIQUES

import java.awt.image.BufferedImage;
import java.util.StringTokenizer;
import java.util.Vector;



public abstract class FeatureExtractionModule1
{

    public FeatureExtractionModule1()
    {
        _featureLength = 0;
        _featureName = "FeatureExtractionModule";
        _featureDescription = "This is the abstract class for Discovir Feature Extraction Module";
         
    }

    public String getFeatureName()
    {
        return _featureName;
    }

    public int getFeatureLength()
    {
        return _featureLength;
    }


    public abstract Vector getFeatureVector(DiscovirImage1 discovirimage);

    public Vector getFeatureVector(BufferedImage image)
    {
        DiscovirImage1 dImage = new DiscovirImage1(image);
        return getFeatureVector(dImage);
    }
    
//COMPARE FEATURES VECTORS BY EUCLIDEAN DISTANCE
    public double compareFeatureVector(Vector a, Vector b)
    {
        int noOfDimension = a.size();
        if(noOfDimension != b.size())
            return 99999.899999999994D;
        double total = 0.0D;
 
        for(int i = 0; i < noOfDimension; i++)
        {
            double doubleA = ((Double)a.elementAt(i)).doubleValue();
            double doubleB = ((Double)b.elementAt(i)).doubleValue();
       
            total += (doubleA - doubleB) * (doubleA - doubleB);
        }
        
         System.out.println();
        return Math.sqrt(total / (double)noOfDimension);
        
        
    }

    public double compareFeatureString(String a, String b)
    {
        return compareFeatureVector(string2Vector(a), string2Vector(b));
    }
    
//FUNCTION TO CONVERT VECTOR TO STRING
    public String vector2String(Vector vec)
    {
        StringBuffer temp = new StringBuffer();
        for(int i = 0; i < vec.size() - 1; i++)
            temp.append(((Double)vec.elementAt(i)).doubleValue() + ",");

        temp.append(((Double)vec.elementAt(vec.size() - 1)).doubleValue());
        return temp.toString();
    }
     
    
       
//FUNCTION TO CONVERT STRING TO VECTOR
    public Vector string2Vector(String str)
    {
        StringTokenizer strToken = new StringTokenizer(str, ",");
        Vector vec = new Vector(strToken.countTokens());
        for(; strToken.hasMoreElements(); vec.add(new Double(strToken.nextToken())));
        return vec;
    }

    protected String _featureName;
    protected int _featureLength;
    protected Double _featureVersion;
    protected String _featureCategory;
    protected String _featureDescription;
  

}