package randomImg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;


import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 显示图像的类
 * @author hou
 *
 */
public final class ImgGenerator {
//	public static final int W = 380;
	public static final int W = 420;
	public static final int X = 8;
	public static final int Y = 30;
	
	public static final Color BACKCOLOR = Color.BLACK;
	public static final int COLOR = 0XFF0E10;
	public static final int COLOR2 = 0xFFBBEFFF;
	
	public static final int POINTS = 40000;
	
	final BufferedImage img = new BufferedImage(W, W , BufferedImage.TYPE_INT_RGB);
	
	/**
	 * 构造方法
	 * 参数为0时从控制台输入随机数
	 * @param i
	 */
	private ImgGenerator(int i) {
		init();
		if (i == 0) {
			List<Integer> list = RandomInput.getListFromStdin();
			Integer[] integers = new Integer[list.size()];
			
			drawRandom(list.toArray(integers));
			showImage();
		} else {
//			showImage(RandomInput.gen(1400000));
			Integer[] integers = RandomInput.gen(POINTS);
			Map<Integer, Long> map = probabilityDensity(integers);
			
			drawRandom(integers);
			drawProbabilityDensity(map);
			showImage();
		}
	}
	
	/**
	 * 根据位置随机数计算每个位置的概率密度
	 * @param list
	 * @return
	 */
	public Map<Integer, Long> probabilityDensity(Integer[] list){
		Map<Integer, Long> map = Arrays.stream(list).collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
		Map<Integer, Long> map2 = map.entrySet().stream().collect(Collectors.<Entry<Integer, Long>, Integer, Long>toMap(e -> e.getKey(), e -> W-1-(long)(e.getValue()  *W / POINTS)));
		
		return map2;
	}
	
	/**
	 * 画出随机点图
	 * @param integers
	 */
	public void drawRandom(Integer[] integers) {
		for (int i = 0; i < integers.length; i += 2) {
			if (0 < integers[i] && integers[i] < img.getWidth()) {
				if (0 < integers[i + 1] && integers[i + 1] < img.getHeight()) {
					img.setRGB(integers[i], integers[i + 1], COLOR);
				}
			}
		}
		img.flush();
	}
	
	/**
	 * 画出概率密度曲线
	 * @param map
	 */
	public void drawProbabilityDensity(Map<Integer, Long> map) {
//		map.forEach((k,v) -> {
//			if (0 < k  && k < img.getWidth()) {
//				img.setRGB(k, v.intValue(), COLOR2);
//			}
//		});
		final Graphics g = img.getGraphics();
		g.setColor(new Color(COLOR2));
		
		TreeMap<Integer, Long> treeMap = new TreeMap<>(map);
		//逐个点画线
		treeMap.entrySet().stream().reduce((pre, cur) -> {
			g.drawLine(pre.getKey(), pre.getValue().intValue(), cur.getKey(), cur.getValue().intValue());
			return cur;
		});
		img.flush();
	}
	
	/**
	 * 初始化图像
	 */
	public void init() {
		Graphics g = img.getGraphics();
		g.setColor(BACKCOLOR);
		g.fillRect(0, 0, W, W);
	}
	
	public void showImage() {
//		WritableRaster copyData = img.copyData(null);
//		BufferedImage b2 = new BufferedImage(img.getColorModel(), copyData, img.getColorModel().isAlphaPremultiplied(), null);
//		createFile(img);
		JFrame jFrame = new JFrame("随机数") {
			private static final long serialVersionUID = 1L;
			@Override
			public void paint(Graphics g) {
				g.drawImage(img, X, Y, null);
			}
		};
		
		jFrame.setBackground(Color.BLACK);
		jFrame.setSize(W + X + X, W + X + Y);
		jFrame.setLocation(350, 150);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
		jFrame.repaint();
	}
	
	/**
	 * 导出文件
	 * @param img
	 */
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
