package uk.ac.soton.ecs.sr2u17.hybridimages;

import org.openimaj.image.MBFImage;

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
		
		lowKernel = Gaussian2D.createKernelImage(size, lowSigma).pixels;
		
		lowImage.processInPlace(new MyConvolution(lowKernel));
		
		size = (int) (8.0f * highSigma + 1.0f);
		if (size % 2 == 0) size++;
		
		hLowKernel = Gaussian2D.createKernelImage(size, highSigma).pixels;
		
		MBFImage hLowPass = highImage.clone();
		hLowPass.processInPlace(new MyConvolution(hLowKernel));
		highImage.subtractInPlace(hLowPass);
		
		return lowImage.addInPlace(highImage);
	}
	
	public static void main(String args[]){
		
		MBFImage lowImg = ImageUtilities.readMBF(new File());
		MBFImage highImg = ImageUtilities.readMBF(new File());
		
		MBFImage hybrid = makeHybrid(lowImg, 4, highImg, 4);
		
		DisplayUtilities.display(hybrid);
	}
}