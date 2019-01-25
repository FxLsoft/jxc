package com.jeeplus.modules.tools.utils;

import java.awt.image.BufferedImage;

import jp.sourceforge.qrcode.data.QRCodeImage;

/**
 * author: dove19900520
 * URL: http://dove19900520.iteye.com/blog/2176374
 */
public class TwoDimensionCodeImage implements QRCodeImage {

	BufferedImage bufImg;

	public TwoDimensionCodeImage(BufferedImage bufImg) {
		this.bufImg = bufImg;
	}

	@Override
	public int getHeight() {
		return bufImg.getHeight();
	}

	@Override
	public int getPixel(int x, int y) {
		return bufImg.getRGB(x, y);
	}

	@Override
	public int getWidth() {
		return bufImg.getWidth();
	}

}
