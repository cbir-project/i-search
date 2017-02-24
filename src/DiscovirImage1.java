//CLASS TO DISCOVER IMAGE PROPERTIES


import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class DiscovirImage1
{

    public DiscovirImage1(String filename)
    {
        _image = null;
        _filename = filename;
    }

    public DiscovirImage1(File fileHandle)
    {
        _image = null;
        try
        {
            _filename = fileHandle.getCanonicalPath();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public DiscovirImage1(BufferedImage image)
    {
        _image = image;
        _filename = "temp";
    }

    public DiscovirImage1(BufferedImage image, String filename)
    {
        _image = image;
        _filename = filename;
    }
    
//TO READ IMAGE FILE
    public boolean readImage()
    {
        try
        {
            _image = ImageIO.read(new File(_filename));
            _colorModel = _image.getColorModel();
        }
        catch(Exception e)
        {
			System.out.println("read Image="+_filename);
            return false;
        }
        return true;
    }
//GET WIDTH OF IMAGE
    public int getWidth()
    {
        return _image.getWidth();
    }
//GET HEIGHT OF IMAGE
    public int getHeight()
    {
        return _image.getHeight();
    }
//TO GET IMAGE FILENAME
    public String getFileName()
    {
        return _filename;
    }

    public int[] getPackedRGBPixel()
    {
        return _image.getRGB(0, 0, _image.getWidth(), _image.getHeight(), null, 0, _image.getWidth());
    }

    public int[] getRGBPixel()
    {
        int size = _image.getHeight() * _image.getWidth();
        int pixel[] = new int[size];
        int pixels[] = new int[size * 3];
        PixelGrabber pg = new PixelGrabber(_image, 0, 0, _image.getWidth(), _image.getHeight(), pixel, 0, _image.getWidth());
        try
        {
            pg.grabPixels();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        if((pg.getStatus() & 0x80) != 0)
        {
            System.out.println("Error in grabbing pixel");
            return null;
        }
        for(int i = 0; i < size; i++)
        {
            pixels[i * 3] = pixel[i] >> 16 & 0xff;
            pixels[i * 3 + 1] = pixel[i] >> 8 & 0xff;
            pixels[i * 3 + 2] = pixel[i] & 0xff;
        }

        return pixels;
    }

    public int getPackedRGBPixel(int x, int y)
    {
        return _image.getRGB(x, y);
    }

    public int[] getRGBPixel(int x, int y)
    {
        int pixel[] = new int[1];
        int pixels[] = new int[3];
        PixelGrabber pg = new PixelGrabber(_image, x, y, 1, 1, pixel, 0, _image.getWidth());
        try
        {
            pg.grabPixels();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }
        if((pg.getStatus() & 0x80) != 0)
        {
            System.out.println("Error in grabbing pixel");
            return null;
        } else
        {
            pixels[0] = pixel[0] >> 16 & 0xff;
            pixels[1] = pixel[0] >> 8 & 0xff;
            pixels[2] = pixel[0] & 0xff;
            return pixels;
        }
    }

   
    protected void finalize()
        throws Throwable
    {
        _image = null;
        _filename = null;
        _colorModel = null;
    }

    private BufferedImage _image;
    private String _filename;
    private ColorModel _colorModel;
}