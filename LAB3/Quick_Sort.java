public class Quick_Sort {
	public static void main(String[] args) {
		// 產生一個array
		int N = 100;
		int[] x = new int[N];
		for (int i = 0; i < N; i++)
			x[i] = i;

		// Fisher Yates shuffle
		for (int i = N - 1; i >= 0; i--) {
			int j = (int) (Math.random() * (i + 1));
			// swap
			int temp;
			temp = x[j];
			x[j] = x[i];
			x[i] = temp;
		}
		System.out.println("未排序數列:");
		PrintArray(x);
		QuickSort(x, 0, N - 1);
		System.out.println("QuickSort排序後:");
		PrintArray(x);
	}

	public static void QuickSort(int[] x, int left, int right) {
		if (right > left) {
			int P = Separate(x, left, right);
			QuickSort(x, left, P - 1);
			QuickSort(x, P + 1, right);
		}
	}

	public static int Separate(int[] x, int left, int right) {
		// 一層的Separate
		// left 與 right 當前要分離的子陣列中的開始索引 與結束索引
		// 回傳值為Separate 後 povit 位置的的索引
		int Povit = x[right]; // 拿最後一個element 當作Povit
		int i = left - 1;// i表示 小於Povit的子陣列的最後一個元位置
		// j 為現在正在判斷的下標位置
		for (int j = left; j < right; j++) {
			if (x[j] < Povit) {
				i += 1;
				// swap
				int temp;
				temp = x[j];
				x[j] = x[i];
				x[i] = temp;
			}
		}
		// 最後將 Povit 放回它的位置
		// swap
		int temp;
		temp = x[i + 1];
		x[i + 1] = x[right];
		x[right] = temp;
		return i + 1;
	}

	public static void PrintArray(int[] x) {
		for (int i : x)
			System.out.printf(" %d,", i);
		System.out.printf("\n");
	}

}
