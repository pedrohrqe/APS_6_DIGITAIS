package Biometrics;

import java.awt.image.BufferedImageOp;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class CFingerPrintGraphics {

	public int FP_IMAGE_WIDTH = 323;
	public int FP_IMAGE_HEIGHT = 352;

	public CFingerPrintGraphics() {
	}

	public CFingerPrintGraphics(int width, int height) {
		FP_IMAGE_WIDTH = width;
		FP_IMAGE_HEIGHT = height;
	}

	public BufferedImage BinerizeImage(BufferedImage m_image, int max, int min) {
		BufferedImage m_ImageBuffer = new BufferedImage(FP_IMAGE_WIDTH, FP_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i <= FP_IMAGE_WIDTH - 1; i++) {
			for (int j = 0; j <= FP_IMAGE_HEIGHT - 1; j++) {
				Color c = new Color(m_image.getRGB(i, j));
				if ((c.getBlue() <= max) && (c.getBlue() >= min) && (c.getRed() <= max) && (c.getRed() >= min)
						&& (c.getGreen() <= max) && (c.getGreen() >= min))
					m_ImageBuffer.setRGB(i, j, 0);
				else
					m_ImageBuffer.setRGB(i, j, Color.white.getRGB());
			}
		}
		return m_ImageBuffer;
	}

	public BufferedImage getGreyFingerPrintImage(BufferedImage m_original_image) {
		BufferedImage m_result_image = new BufferedImage(FP_IMAGE_WIDTH, FP_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
		float mask[] = { -1f / 5, 1f / 5, -1f / 5, 1f / 5, 9f / 5, 1f / 5, -1f / 5, 1f / 5, -1f / 5 };

		Kernel k = new Kernel(3, 3, mask);
		BufferedImageOp con = new ConvolveOp(k, ConvolveOp.EDGE_NO_OP, null);
		con.filter(m_original_image, m_result_image);
		return m_result_image;
	}
}