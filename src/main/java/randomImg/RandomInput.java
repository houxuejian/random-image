package randomImg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RandomInput {
	
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
	
	public static void stdoutRandom() {
		Random r = new Random();
		for (int i = 0; i < 500000; i++) {
			int a1 = r.nextInt(ImgGenerator.W);
			System.out.println(a1);
		}
	}
	
	public static Integer[] gen(int num) {
		int size = (num & 1) == 1 ? num - 1 : num;
		Integer[] list = new Integer[size];
		for (int i = 0; i < list.length; i++) {
			list[i] = (int) getRandom();
		}
//		filter(list);
		return list;
	}
	
	private static double getRandom(){
		Random r = new Random();
		double r1 = r.nextGaussian();
		double r2 = r.nextGaussian();
		double r3 = r.nextGaussian();
		int tmp = (int) (r1/r2 * 2 + (ImgGenerator.W / 2.0));
		tmp = (int) (r1* r1*r1 + (ImgGenerator.W / 2.0));
		tmp = (int) (r1 * 8 + (ImgGenerator.W / 2.0));
		tmp = r.nextInt(ImgGenerator.W);
		return tmp;
	}
	
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
