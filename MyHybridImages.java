package hybridimages;

import java.io.File;
import java.io.IOException;

import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.MBFImage;
import org.openimaj.image.processing.convolution.Gaussian2D;

public class MyHybridImages {
	/**
	 * Compute a hybrid image combining low-pass and high-pass filtered images
	 *
	 * @param lowImage
	 *            the image to which apply the low pass filter
	 * @param lowSigma
	 *            the standard deviation of the low-pass filter
	 * @param highImage
	 *            the image to which apply the high pass filter
	 * @param highSigma
	 *            the standard deviation of the low-pass component of computing the
	 *            high-pass filtered image
	 * @return the computed hybrid image
	 */
	public static MBFImage makeHybrid(MBFImage lowImage, float lowSigma, MBFImage highImage, float highSigma) {
		//implement your hybrid images functionality here. 
		//Your submitted code must contain this method, but you can add 
		//additional static methods or implement the functionality through
		//instance methods on the `MyHybridImages` class of which you can create 
		//an instance of here if you so wish.
		//Note that the input images are expected to have the same size, and the output
		//image will also have the same height & width as the inputs.
		
		int size = (int) (8.0f * lowSigma + 1.0f); // (this implies the window is +/- 4 sigmas from the centre of the Gaussian)
		if (size % 2 == 0) size++; // size must be odd
		
		float[][] lowKernel = Gaussian2D.createKernelImage(size, lowSigma).pixels;
		
		lowImage.processInplace(new MyConvolution(lowKernel));
		
		DisplayUtilities.display(lowImage); //TEST
		
		size = (int) (8.0f * highSigma + 1.0f);
		if (size % 2 == 0) size++;
		
		float[][] hLowKernel = Gaussian2D.createKernelImage(size, highSigma).pixels;
		
		MBFImage hLowPass = highImage.clone();
		hLowPass.processInplace(new MyConvolution(hLowKernel));
		highImage.subtractInplace(hLowPass);
		
		DisplayUtilities.display(highImage); //TEST
		
		return lowImage.addInplace(highImage);
	}
	
	public static void main(String args[]){
		
		try {
			MBFImage highImg = ImageUtilities.readMBF(new File("C:/Users/Ioana/Desktop/Signe/OpenIMAJ-Tutorial01/src/main/java/hybridimages/data/cat.bmp"));
			MBFImage lowImg = ImageUtilities.readMBF(new File("C:/Users/Ioana/Desktop/Signe/OpenIMAJ-Tutorial01/src/main/java/hybridimages/data/dog.bmp"));
			
			MBFImage hybrid = makeHybrid(lowImg, 4, highImg, 4);
			
			DisplayUtilities.display(hybrid);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}