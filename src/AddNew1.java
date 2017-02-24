// CREATES A USER INTERFACE TO ADD A NEW IMAGE INTO DATABASE THROUGH ADMINISTRATOR PANEL


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AddNew1 extends JDialog implements ActionListener
{
	JLabel l1,l2,l3,label;
	JButton select,add;
	JTextField name,description,path;	
	JMenuItem addNew,exitAction;
	private static String retpath;
	Image image;
	
	//CONSTRUCTER TO INSTANTIATE DIALOG
	public AddNew1(JFrame frame,JLabel label)
	{

	  super(frame,"Dialog");
	  this.label=label;

		GridBagLayout gridbag=new GridBagLayout();
		setLayout(gridbag);
		GridBagConstraints constraints;
		constraints =new GridBagConstraints();
		l1=new JLabel("  Name",JLabel.LEFT);
		constraints.anchor=GridBagConstraints.WEST;
		cons(constraints,0,1,1,1,1,40);
		gridbag.setConstraints(l1,constraints);
		add(l1);
		name=new JTextField(15);
		cons(constraints,1,1,1,1,1,40);
		gridbag.setConstraints(name,constraints);
		add(name);
		l2=new JLabel("  Select Image",JLabel.LEFT);
		cons(constraints,0,2,1,1,1,40);
		constraints.anchor=GridBagConstraints.WEST;
		gridbag.setConstraints(l2,constraints);
		add(l2);
		path=new JTextField(15);
		path.setEditable(false);
		cons(constraints,1,2,1,1,1,40);
		gridbag.setConstraints(path,constraints);
		add(path);
		select=new JButton("Browse");
		select.addActionListener(this);
		cons(constraints,2,2,1,1,1,40);
		gridbag.setConstraints(select,constraints);
		add(select);

		l3=new JLabel("  Description");
		cons(constraints,0,3,1,1,1,40);
		constraints.anchor=GridBagConstraints.WEST;
		gridbag.setConstraints(l3,constraints);
		add(l3);

		description=new JTextField(15);
		cons(constraints,1,3,1,1,1,40);
		constraints.anchor=GridBagConstraints.WEST;
		gridbag.setConstraints(description,constraints);
		add(description);

		add=new JButton("Insert");
		add.addActionListener(this);
		cons(constraints,1,4,1,1,1,40);
		constraints.anchor=GridBagConstraints.CENTER;
		gridbag.setConstraints(add,constraints);
		add(add);
		setResizable(false);
		setLocation(100,100);
		setSize(400,170);
		//pack();
		setVisible(true);

	}
	//ACTIONS DESIGNDED FOR EACH BUTTON IN THE DIALOG BOX
	public void actionPerformed(ActionEvent ae)
	{	
		Object object=ae.getSource();
		if(object==select)
		{
			JFileChooser jfc=new JFileChooser(".");
			int returnValue=jfc.showOpenDialog(this);
			if(returnValue==JFileChooser.APPROVE_OPTION)
			{
				path.setText(jfc.getSelectedFile().getPath());
				label.setIcon(new ImageIcon(getImage(path.getText())));
				retpath=jfc.getSelectedFile().getPath();
				image=getImage(path.getText());
			}
		}
		if(object==add&&!path.getText().equals("")&&!name.getText().equals("")&&!description.getText().equals(""))
		{	
			if(Util.isImage(path.getText()))
			{   
				String strPath = path.getText().replace("\\", "\\\\");
				String imageInfoFields = "name, description, path";
				String imageInfoValues = "('" + name.getText() + "', '" + description.getText() + "', '" + strPath + "')";
				String sql = "insert into image_info (" + imageInfoFields + ") values "+ imageInfoValues;
				System.out.println("Image Path: "+path.getText());
				if(MysqlConnector.updateRecord(sql))
				{
					JOptionPane.showMessageDialog(this,"Insert Sucessfull!!","warning",JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(this,"Duplicate Entry!!","warning",JOptionPane.INFORMATION_MESSAGE);
					dispose();
					
				}

			}
			else
			{
				JOptionPane.showMessageDialog(this,"Please select Image Files","warning",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	public String getPath()
	{
		return (retpath);
	}
	
	public Image getImage()
	{
		return (image);
	}
	
	
	// TO LOAD AN IMAGE
	public Image getImage(String path)
	{
		String extension="";
		Image theImage =null;
		FileInputStream in =null;
		for(int i=(path.length()-1);i>=0;i--)
		{
			String ch=String.valueOf(path.charAt(i));
			if(ch.equals("."))
			{
				break;
			}
			extension+=ch;
			//System.out.println(extension);
		}
		if(extension.equalsIgnoreCase("PMB"))
		{
			try
			{
    		in = new FileInputStream(path);
			}
			catch(Exception e)
			{
			}
    		BMPLoader1 bmp=new BMPLoader1();
    		theImage = bmp.read(in);
		}
		else if(extension.equalsIgnoreCase("gpj"))
		{
			theImage=new ImageIcon(path).getImage();
		}

		else if(extension.equalsIgnoreCase("fig"))
			theImage=new ImageIcon(path).getImage();

		else
			theImage=new ImageIcon("Null.jpg").getImage();
		return theImage;
	}
	void cons(GridBagConstraints gbc,int gx,int gy,int gw,int gh,int wx,int wy)
	{
		gbc.gridx=gx;
		gbc.gridy=gy;
		gbc.gridwidth=gw;
		gbc.gridheight=gh;
		gbc.weightx=wx;
		gbc.weighty=wy;
	}
}

