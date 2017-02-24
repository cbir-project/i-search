// LOCAL COLOR HISTOGRAM
import java.util.*;
import java.lang.*;

public class LocalColorHistogram extends FeatureExtractionModule1 {


	public LocalColorHistogram() {
		super();
		this._featureName="LocalColorHistogram";
		this._featureLength=1024;
        this._featureDescription="Get the local color histogram";
	};

	public Vector getFeatureVector(DiscovirImage1 image) {
		return getFeatureVector(image, 4);
	}

//CALCULATE FEATURE VECTOR FOR LOCAL COLOR HISTOGRAM

	public Vector getFeatureVector(DiscovirImage1 image, int d) {
		final int dimen = d*d;
		Vector localColorHistogram=new Vector(dimen*64);

		int i=0, j=0, k=0;
		int width=image.getWidth();
		int height=image.getHeight();
		int size=width*height;
		int[] pixels=image.getPackedRGBPixel();
		double[][] histogram = new double[dimen][64];

		for(i=0 ; i<dimen ; i++){
			histogram[i] = new double[64];
			for(j=0 ; j<64 ; j++){
				histogram[i][j] = 0.0;
			}
		}

		for(i=0 ; i<size ; i++){
//			k = pixels[i*3]/64*16 + pixels[i*3+1]/64*4 + pixels[i*3+2]/64;
			k = (pixels[i]>>18 & 0x30) | (pixels[i]>>12 & 0xc) | (pixels[i]>>6 & 0x3);
			j =	decideLocal(i, width, height, d);
			histogram[j][k] += 1.0;
		}

		for(i=0 ; i<dimen ; i++)
			for(j=0 ; j<64 ; j++)
				localColorHistogram.add(new Double(histogram[i][j]/(size/dimen)));


		return localColorHistogram;
	}
//DECIDE THE LOCALITY OF THE PIXEL
	private int decideLocal(double j, double w, double h, int d){
		double localWidth = w/d;
		double localHeight = h/d;

		int area = (int)(j/w/localHeight)*d + (int)((j%w)/localWidth);
		return area;
	}

}
