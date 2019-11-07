package uk.ac.soton.ecs.sr2u17.hybridimages;

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
		
		int size = (int) (8.0f * lowSigma + 1.0f); // window is +/- 4 sigmas from the centre of the Gaussian
		if (size % 2 == 0) size++; // size must be odd
		
		float[][] lowKernel = Gaussian2D.createKernelImage(size, lowSigma).pixels;
		
		lowImage.processInplace(new MyConvolution(lowKernel)); // produce low pass of the low image
		
		size = (int) (8.0f * highSigma + 1.0f);
		if (size % 2 == 0) size++;
		
		float[][] hLowKernel = Gaussian2D.createKernelImage(size, highSigma).pixels;
		
		MBFImage hLowPass = highImage.clone();
		hLowPass.processInplace(new MyConvolution(hLowKernel)); // produce low pass of the high image
		
		highImage.subtractInplace(hLowPass); // produce high pass by subtracting its low pass
		
		return lowImage.addInplace(highImage); // return hybrid image by adding low and high
	}
}