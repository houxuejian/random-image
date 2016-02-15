package randomImg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * 产生或输入随机数
 * @author hou
 *
 */
public class RandomInput {
	
	/**
	 * 标准输入
	 * @return
	 */
	public static List<Integer> getListFromStdin() {
		List<Integer> list = new ArrayList<>();
		
		Scanner sc = new Scanner(System.in);
		while(true) {
			int i = sc.nextInt();
			if (i == -1) {
				break;
			}
			list.add(i);
		}
		sc.close();
		//保证偶数
		if (list.size() % 2 != 0) {
			list.remove(0);
		}
		return list;
	}
	
	/**
	 * 标准输出随机数
	 */
	public static void stdoutRandom() {
		Random r = new Random();
		for (int i = 0; i < 500000; i++) {
			int a1 = r.nextInt(ImgGenerator.W);
			System.out.println(a1);
		}
	}
	
	/**
	 * 生成偶数个随机数，返回集合
	 * @param num
	 * @return
	 */
	public static Integer[] gen(int num) {
		int size = (num & 1) == 1 ? num - 1 : num;
		Integer[] list = new Integer[size];
		for (int i = 0; i < list.length; i++) {
			list[i] = (int) getRandom();
		}
//		filter(list);
		return list;
	}
	
	/**
	 * 产生一个随机数
	 * @return
	 */
	private static double getRandom(){
		Random r = new Random();
		double r1 = r.nextGaussian();
		double r2 = r.nextGaussian();
		double r3 = r.nextGaussian();
		int tmp = (int) (r1/r2 * 2 + (ImgGenerator.W / 2.0));
//		tmp = (int) (r1* r1*r1 + (ImgGenerator.W / 2.0));
//		tmp = (int) (r1 * 8 + (ImgGenerator.W / 2.0));
//		tmp = r.nextInt(ImgGenerator.W);
		return tmp;
	}
	
	/**
	 * 随机数过滤规则
	 * @param integers
	 */
	private static void filter(Integer[] integers) {
		//横纵条纹
		for (int i = 0; i < integers.length; i++) {
			if (integers[i] % 13 == 0 || integers[i] % 9 == 0) {
				integers[i] = 0;
			}
		}
		Random r = new Random();
		//密度变化
		for (int i = 0; i < integers.length; i++) {
			double d = integers[i] / (double)ImgGenerator.W;
			double ran = r.nextDouble();
			if (Math.pow(Math.abs(ran - d), 0.375) < 0.77) {
				integers[i] = 0;
			}
			
		}
	}

}
