
public class FastPowDemo {
	public static void main(String[] args) {

		long t1, t2;
		double base = 1.0000001;
		int exponent = 1000000000;

		System.out.printf("計算 %.7f ^ %d :\n", base, exponent);
		t1 = System.currentTimeMillis();
		System.out.printf("= %.0f \n", stupid_Pow(base, exponent));
		t2 = System.currentTimeMillis();
		System.out.println("stupipd method 花了" + (t2 - t1) + "毫秒");
		
		System.out.println("");
		t1 = System.currentTimeMillis();
		System.out.printf("= %.0f \n", Fast_Pow_base_on_2(base, exponent));
		t2 = System.currentTimeMillis();
		System.out.println("Fast power base on 2 花了" + (t2 - t1) + "毫秒");
		
		System.out.println("");
		t1 = System.currentTimeMillis();
		System.out.printf("= %.0f \n", Fast_Pow_base_on_3(base, exponent));
		t2 = System.currentTimeMillis();
		System.out.println("Fast power base on 3 花了" + (t2 - t1) + "毫秒");
	}

	public static double stupid_Pow(double base, int exponent) {
		double result = 1;
		for (int i = 0; i < exponent; i++)
			result *= base;
		return result;
	}

	public static double Fast_Pow_base_on_2(double base, int exponent) {
		if (exponent > 1) {
			// 將exponent 拆成 exponent = 2^N + r
			// 2^N 用 2次方的快速算法 Pow2 函數計算
			// 剩下來的r 部分用recursive
			int r = exponent;
			int power2 = 1;
			int N = 0;
			while (true) {
				power2 *= 2;
				N += 1;
				if (power2 > r) {
					N -= 1;
					power2 /= 2;
					r -= power2;
					break;
				}
			}
			double first_part = Pow2(base, power2);
			double second_part = Fast_Pow_base_on_2(base, r);
			return first_part * second_part;
		} else if (exponent == 1) {
			return base; // base^1 = base
		} else {
			return 1.0; // base^0 = 1
		}
	}

	public static double Pow2(double base, int exponent) {
		// 輸入 exponent 必須是 2^n 的形式，才能使用快速算法
		if (exponent == 2) {
			return base * base;
		} else {
			double temp_Pow = Pow2(base, exponent / 2);
			return temp_Pow * temp_Pow;
		}
	}

	public static double Fast_Pow_base_on_3(double base, int exponent) {
		if (exponent > 2) {
			// 將exponent 拆成 exponent = 3^N + r
			// 3^N 用 3次方的快速算法 Pow3 函數計算
			// 剩下來的r 部分用recursive
			int r = exponent;
			int power3 = 1;
			int N = 0;
			while (true) {
				power3 *= 3;
				N += 1;
				if (power3 > r) {
					N -= 1;
					power3 /= 3;
					r -= power3;
					break;
				}
			}
			double first_part = Pow3(base, power3);
			double second_part = Fast_Pow_base_on_3(base, r);
			return first_part * second_part;
		} else if (exponent == 2) {
			return base * base; // base^2 = base*base
		} else if (exponent == 1) {
			return base; // base^1 = base
		} else {
			return 1.0; // base^0 = 1
		}

	}

	public static double Pow3(double base, int exponent) {
		// 輸入 exponent 必須是 3^n 的形式，才能使用快速算法
		if (exponent == 3) {
			return base * base * base;
		} else {
			double temp_Pow = Pow3(base, exponent / 3);
			return temp_Pow * temp_Pow * temp_Pow;
		}
	}

}
