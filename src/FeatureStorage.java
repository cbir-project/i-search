//CLASS FOR STORING FEATURE IN DATABASE

import java.io.File;
import java.io.PrintStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class FeatureStorage
{   
    public FeatureStorage(String argv[])        
    {

            String modulename = argv[0];
            System.out.println(modulename);
             try
            {                        
                Class moduleClass = Class.forName(modulename);
                module = (FeatureExtractionModule1)moduleClass.newInstance();
                moduleClass = null;
			    processFile(new File(argv[1]));	
            }
            catch(Exception e)
            {
				System.out.println("ERROR: Cons");
                e.printStackTrace();
            }
            return;
        }
 

    private  void processFile(File fileHandle)
    {
        DiscovirImage1 image = new DiscovirImage1(fileHandle);
        if(!image.readImage())
        {
            System.out.println("Error in reading image");
            return;
        }
       // System.out.println("count="+count);
        
        Vector feature1= module.getFeatureVector(image);
        String fname=module.getFeatureName();
        String fvector =module.vector2String(feature1);
        
       // System.out.println(fname);
        String filename=fileHandle.getPath().replace("\\", "\\\\");
        
        String deleteQuery = "DELETE FROM image_feature WHERE file_name='" + filename + "' AND feature_name='" + fname + "'" ;        
        MysqlConnector.updateRecord(deleteQuery);
        
        String query = "INSERT INTO image_feature values('" + filename + "', '" + fname +"', '" + fvector + "')";       
        
        if(MysqlConnector.updateRecord(query))
		{
			System.out.println("INSERTED");
		}
		else
		{
			System.out.println("ALREADYINSERTED");
		}
			
        
        image = null;

    }
    

    private static int count = 0;
    private static int max_count = 0;
    private static FeatureExtractionModule1 module = null;
    private static Vector feature[] = null;
    private static Vector input=null;
    private static File children[] ;
    private static double d=0;
    private static ArrayList list,outputList;
}
    	