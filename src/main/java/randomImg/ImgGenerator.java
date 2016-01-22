package randomImg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public final class ImgGenerator {
//	public static final int W = 380;
	public static final int W = 420;
	public static final int X = 8;
	public static final int Y = 30;
	
	public static final Color BACKCOLOR = Color.BLACK;
	public static final int COLOR = 0xFFBBEFFF;
	
	private ImgGenerator(int i) {
		if (i == 0) {
			List<Integer> list = RandomInput.getListFromStdin();
			Integer[] integers = new Integer[list.size()];
			
			showImage(list.toArray(integers));
		} else {
			showImage(RandomInput.gen(1400000));
		}
	}
	
	
	public void showImage(Integer[] integers) {
		final BufferedImage img = new BufferedImage(W + X, W + Y, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.getGraphics();
		g.setColor(BACKCOLOR);
		g.fillRect(0, 0, W, W);		
		
		for (int i = 0; i < integers.length; i += 2) {
			img.setRGB(integers[i] + X, integers[i + 1] + Y, COLOR);
		}
		
		img.flush();
//		WritableRaster copyData = img.copyData(null);
//		BufferedImage b2 = new BufferedImage(img.getColorModel(), copyData, img.getColorModel().isAlphaPremultiplied(), null);
//		createFile(img);
		JFrame jFrame = new JFrame("随机数") {
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				g.drawImage(img, 0, 0, null);
			}
		};
		
		jFrame.setBackground(Color.WHITE);
		jFrame.setSize(W, W);
		jFrame.setLocation(350, 150);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
	}
	
	private void createFile(BufferedImage img) {
		File file = new File("h:/a.png");
		try {
			file.createNewFile();
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		int i = JOptionPane.showConfirmDialog(null, "输入随机数？ -1结束");
		if (i == 0 || i == 1) {
			new ImgGenerator(i);
		}
	}

}
