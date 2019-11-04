package uk.ac.soton.ecs.sr2u17.hybridimages;

import org.openimaj.image.FImage;
import org.openimaj.image.processor.SinglebandImageProcessor;

public class MyConvolution implements SinglebandImageProcessor<Float, FImage> {
	private float[][] kernel;

	public MyConvolution(float[][] kernel) {
		//note that like the image pixels kernel is indexed by [row][column]
		this.kernel = kernel;
	}

	@Override
	public void processImage(FImage image) {
		// convolve image with kernel and store result back in image
		//
		// hint: use FImage#internalAssign(FImage) to set the contents
		// of your temporary buffer image to the image.
		
		FImage copy = image.clone();
		copy.zero();
		
		int cols = image.width;
		int rows = image.height;
		
		int kc = kernel.length;
		int kr = kernel[0].length;
		
		int hkc = (int) Math.floor(kc/2);
		int hkr = (int) Math.floor(kr/2);
		
		
		
		
		
		
		image.internalAssign(copy);
	}
}