package hybridimages;

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

		// Loop through the pixels of the convolution result
		for(int x = 0; x < cols; x++){
			for(int y = 0; y < rows; y++){
				
				float sum = 0; float pixel;
				
				// Loop through the kernel
				for(int r = -hkr; r <= hkr; r++) {
					for(int c = -hkc; c <= hkc; c++) {
						
						if(x + c > cols - 1 || x + c < 0 || y + r > rows - 1 || y + r < 0) {
							pixel = 0; // Pretend there is 0-padding
						}
						else {
							pixel = image.pixels[y + r][x + c];
						}
						
						sum += pixel * kernel[c + hkc][r + hkr];
					}
				}

				copy.pixels[y][x] = sum;
			}
		}

		image.internalAssign(copy);
	}
}
