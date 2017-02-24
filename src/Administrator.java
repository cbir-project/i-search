//ADMINISTRATOR PANEL TO ADD IMAGES TO DATABASE AND EXTRACT FEATURES

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Administrator extends JFrame implements ActionListener
{
	JLabel l1;
	JMenu fileMenu,extract;
	JMenuBar bar;
	JMenuItem addNew,exit,local,global,edge,cooccurence,extractall,display;

	Container content;
	File file;
	FeatureStorage Store;
	AddNew1 a1;
	String path;
	DisplayFeatures df;
    JPanel jp;
	Boolean check=false;
	//INITIALISING THE FRAME
	public Administrator()
	{
		super("Adminitrator");
		content=getContentPane();
		
		content.setLayout(new BorderLayout());
		bar=new JMenuBar();
		setJMenuBar(bar);
		fileMenu=new JMenu("File");
		extract=new JMenu("Extract Features");
		
		bar.add(fileMenu);
		bar.add(extract);
		
		addNew=new JMenuItem("Add new Image");
		exit=new JMenuItem("Exit");
		local=new JMenuItem("LocalColorHistogram");
		global=new JMenuItem("GlobalColorHistogram");
		edge=new JMenuItem("EdgeFrequency");
		cooccurence=new JMenuItem("Cooccurence");
		extractall=new JMenuItem("EXTRACT ALL");
		display=new JMenuItem("Display Features");
		
		
		addNew.addActionListener(this);
		exit.addActionListener(this);
		global.addActionListener(this);
		local.addActionListener(this);
		edge.addActionListener(this);
		cooccurence.addActionListener(this);
        extractall.addActionListener(this);
        display.addActionListener(this);
        
		fileMenu.add(addNew);
		fileMenu.add(exit);
		extract.add(global);
		extract.add(local);
        extract.add(edge);
	    extract.add(cooccurence);
	    extract.add(extractall);
	    bar.add(display);
	    
	    jp=new JPanel();
		jp.setLayout(new BorderLayout());
		ImageIcon icon=new ImageIcon("CBIR.jpg");
		l1=new JLabel(icon);
		jp.add("Center",l1);
		content.add("Center",jp);
		
		setSize(400,450);
		setVisible(true);
		//setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	//ACTIONS FOR EACH MENUITEM
	public void actionPerformed(ActionEvent ae)
	{
		Object object=ae.getSource();

		if(object==addNew)
		{
			a1=new AddNew1(this,l1);
			check=true;
			
		}
		if(object==exit)
		{
			System.exit(0);
		}
		if(object==global)
		{   
		   if(check)
			{
			path=a1.getPath();
			System.out.println(path);
			
			if(path!=null&&!path.equals(""))
			{ 
				String [] argv={"GlobalColorHistogram",path};
				Store=new FeatureStorage(argv);
				System.out.println("GCH INSERTED");
			}
			}
			else
				JOptionPane.showMessageDialog(this,"Please select input image file");
			
		}
		if(object==local)
		{  
			if(check)
			{
			path=a1.getPath();
			System.out.println(path);
			
			if(path!=null&&!path.equals(""))
			{ 
				String [] argv={"LocalColorHistogram",path};
				Store=new FeatureStorage(argv);
				System.out.println("LCH INSERTED");
			}
			}
			else
				JOptionPane.showMessageDialog(this,"Please select input image file");
	    }
	    if(object==edge)
		{
		    
		    if(check)
			{
				path=a1.getPath();
			System.out.println(path);
			
			if(path!=null&&!path.equals(""))
			{ 
				String [] argv={"Edgefrequency",path};
				Store=new FeatureStorage(argv);
				System.out.println("EDGEFREQUENCY INSERTED");
					}
					
			}
			else
				JOptionPane.showMessageDialog(this,"Please select input image file");
	    }
	    if(object==cooccurence)
		{	  
			if(check)
			{     
			path=a1.getPath();
			System.out.println(path);
			
			if(path!=null&&!path.equals(""))
			{ 
				String [] argv={"Cooccurence",path};
				Store=new FeatureStorage(argv);
				System.out.println("COOCCURENCE INSERTED");
			}
			}
			else
				JOptionPane.showMessageDialog(this,"Please select input image file");
	    }
	    if(object==extractall)
	    {
	    	if(check)
		{
	    		path=a1.getPath();
			System.out.println("Image Path: "+path);
			
	       if(path!=null&&!path.equals(""))
			{
				String [] argv={"GlobalColorHistogram",path};
				Store=new FeatureStorage(argv);
				System.out.println("GCH INSERTED");
				
				String [] argv1={"LocalColorHistogram",path};
				Store=new FeatureStorage(argv1);
				System.out.println("LCH INSERTED");
				
				String [] argv2={"Edgefrequency",path};
				Store=new FeatureStorage(argv2);
				System.out.println("EDGEFREQUENCY INSERTED");
				
				String [] argv3={"Cooccurence",path};
				Store=new FeatureStorage(argv3);
				System.out.println("COOCCURENCE INSERTED");	
					
				JOptionPane.showMessageDialog(this,"ALL FEATURES EXTRACTED !");		 
					
	         }
			}
	         else
				JOptionPane.showMessageDialog(this,"Please select input image file");
	    
	      }
	      if(object==display)
	      {
	      	if(check)
			{
	      	path=a1.getPath();
			System.out.println(path);
			
	       if(path!=null&&!path.equals(""))
			{
				df=new DisplayFeatures(new File(path));
	      	
	        }
			}
	        else
				JOptionPane.showMessageDialog(this,"Please select input image file");
	      }
	}
	public static void main(String args[])
	{
		new Administrator();
	}
	
}