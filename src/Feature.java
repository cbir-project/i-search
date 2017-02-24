//FEATURE TESTER CLASS 
// THIS CLASS CALCULATES THE DISTANCE BETWEEN FEATURE OF INPUT IMAGE AND FEATURE DATABASE

import java.io.File;
import java.sql.ResultSet;
import java.util.*;
import javax.swing.*;

public class Feature
{
    public Feature(JFrame frame,String argv[],double d)
    {
			this.d=d;
			
	for(int i=0;i<argv.length;i++)
	System.out.println(argv[i]);
	
        if(argv.length != 2)
        {
            System.out.println("Usage: java Tester <ModuleName> <NumberOfImage> <DirectoryName> <input file name>");
        } else
        {
            Runtime rt = Runtime.getRuntime();
            String modulename = argv[0];
            //String directoryName = argv[2];
            System.out.println(modulename);
            try
            {
              
                fname=modulename;
                System.out.println(fname);
                String query="SELECT * FROM image_feature WHERE feature_name = '" + fname + "'";
				if(MysqlConnector.getMysqlStmt()==null)					
					MysqlConnector.openConnection();
				ResultSet rs = MysqlConnector.getMysqlStmt().executeQuery(query);
				int count = 0;
				filelist = new ArrayList();
				featurelist = new ArrayList();
				while (rs!=null && rs.next())
				{
					filelist.add(rs.getString(1));
					featurelist.add(rs.getString(3));
					count++;
				}
				
				max_count = count;              
                System.out.println(max_count);
                
                feature = new Vector[max_count];
                
                Class moduleClass = Class.forName(modulename);
                module = (FeatureExtractionModule1)moduleClass.newInstance();
                moduleClass = null;
                
                processFile1(new File(argv[1]),fname);                

                for(int i=0;i<featurelist.size();i++)
                {
					processFile(new String((String)featurelist.get(i)));
				}
                
            }
            catch(Exception e)
            {
				System.out.println("ERROR: Cons");
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame,"Database  connection failure!!","warning",JOptionPane.WARNING_MESSAGE);
            }
            return;
        }
    }
    
//EXTRACT FEATURES OF PRESENT INPUT IMAGE
    private static void processFile1(File fileHandle,String featname)
    {
        DiscovirImage1 image = new DiscovirImage1(fileHandle);
        double value=0.0D;
        if(!image.readImage())
        {
            System.out.println("Error in reading image"+fileHandle.getName());
            return;
        }
        input= module.getFeatureVector(image);
        image = null;
    }
    
//CALCUTALE DISTANCE 
    private  void processFile(String Fvector )
    {
        
        Vector fvector=module.string2Vector(Fvector);
        //	System.out.println(fvector);
        System.out.println("count="+count);
        feature[count++] = fvector;
        //image = null;
        if(count == max_count)
        {
            compute();
        }
    }

// COMPUTE DISTANCE
    private void compute()
    {
        Vector distance = new Vector();
        Vector distance1 = new Vector();
       
        
        int max_index = (count * (count - 1)) / 2;
        for(int i = 0; i < count; i++)
        {
                distance.add(new Double(module.compareFeatureVector(input, feature[i])));
        }
        
        //NORMALISE DISTANCES
        
        Object max=Collections.max(distance);
        Object min=Collections.min(distance);
        String maxm=max.toString();
        String minm=min.toString();
        Double m= Double.valueOf(maxm).doubleValue();
        Double mn= Double.valueOf(minm).doubleValue();
        
       // System.out.println("MAX="+m);
       // System.out.println("MIN="+mn);
        
        Double range=(Math.abs(m)-Math.abs(mn));
        
        if(mn!=0.0D)
        {
        	mn=0.01D;
        }
        
        for(int i=0;i<count;i++)
        {
        	distance1.add(new Double(((((Double)distance.elementAt(i)).doubleValue())-mn)/(range)));
        }
        
       
        ArrayList al=new ArrayList();
         ArrayList ol=new ArrayList();
         ol=filelist;
         int i,pos;
         
       while(distance1.size()!=0)
       {
       	
        	i=0;
        	pos=i;
        	for(int j=i+1;(j<distance1.size());j++)
        	{
        		if((((Double)distance1.elementAt(j)).doubleValue())<(((Double)distance1.elementAt(pos)).doubleValue()))
        		{
        			pos=j;
        			minp=(((Double)distance1.elementAt(pos)).doubleValue());
        		}	
        	}
        	if(minp<=d)
        	{
         al.add((String)ol.get(pos));
         System.out.println((String)ol.get(pos)+"="+distance1.get(pos));
        	}
        	
       	distance1.removeElementAt(pos);
       	ol.remove(pos);	        	
        }
         
        
        
        outputList=null;
        outputList=al;
        count=0;
    }
//RETURN THE LIST OF IMAGES WHICH ARE SIMILAR
    public ArrayList getOutputList()
    {
		return outputList;
	}
	public int getOutputListCount()
    {
		return outputList.size();
	}
	public int getFileListCount()
    {
		return max_count;
	}
	

    String fname;
    double minp;
    private static int count = 0;
    private static int max_count = 0;
    private static FeatureExtractionModule1 module = null;
    private static Vector feature[] = null;
    private static Vector input=null;
    private static File children[] ;
    private static double d=0;
    private static JPanel outputPanel;
    private static ArrayList filelist, featurelist, outputList;
}