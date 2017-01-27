package io.dimitris.duix;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class CachedPDFRenderer extends PDFRenderer {
	
	protected HashMap<Integer, BufferedImage> cache = new HashMap<Integer, BufferedImage>();
	
	public CachedPDFRenderer(PDDocument document) {
		super(document);
	}
	
	@Override
	public BufferedImage renderImage(int pageIndex, float scale) throws IOException {
		
		if (scale == 1.0) {
			BufferedImage image = cache.get(pageIndex);
			if (image == null) {
				image = super.renderImage(pageIndex, scale);
				cache.put(pageIndex, image);
			}
			return image;
		}
		
		BufferedImage image = renderImage(pageIndex);
		
		BufferedImage scaled = new BufferedImage((int)(image.getWidth()*scale), (int)(image.getHeight()*scale), BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(scale, scale);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
		scaled = scaleOp.filter(image, scaled);
		
		return scaled;
	}
	
}
