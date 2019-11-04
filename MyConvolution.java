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

				float pixel = image.pixels[y][x];
				float sum = pixel * kernel[hkr][hkc]; // Initialising template application sum with kernel middle pixel

				// Loop through the template's pixels to the right and up from the centre
				for(int kxRight = 1; kxRight <= hkc; kxRight++){
					for(int kyUp = 1; kyUp <= hkr; kyUp++){

						if(x + kxRight > cols - 1 || y - kyUp < 0){
							pixel = 0; // Pretend there is 0-padding
						}
						else{
							pixel = image.pixels[y - kyUp][x + kxRight];
						}

						int ky = hkr - kyUp;
						int kx = hkc + kxRight;

						sum += pixel * kernel[ky][kx];
					}
				}

				// Loop through the template's pixels to the left and down from the centre
				for(int kxLeft = 1; kxLeft <= hkc; kxLeft++){
					for(int kyDown = 1; kyDown <= hkr; kyDown++){

						if(x - kxLeft < 0 || y + kyDown > rows - 1){
							pixel = 0; // Pretend there is 0-padding
						}
						else {
							pixel = image.pixels[y + kyDown][x - kxLeft];
						}

						int ky = hkr + kyDown;
						int kx = hkc - kxLeft;

						sum += pixel * kernel[ky][kx];
					}
				}

				copy.pixels[y][x] = sum;
			}
		}
		
		

		image.internalAssign(copy);
	}
}
