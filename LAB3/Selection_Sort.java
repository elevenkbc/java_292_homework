public class Selection_Sort {
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
		SelectionSort(x);
		System.out.println("SelectionSort排序後:");
		PrintArray(x);
	}

	public static void SelectionSort(int[] x) {
		// 選擇性排列開始
		for (int i = 0; i < x.length; i++) {
			for (int j = (i + 1); j < x.length; j++) {
				if (x[i] >= x[j]) {
					// 交換
					int temp;
					temp = x[i];
					x[i] = x[j];
					x[j] = temp;
				}
			}
		}
	}

	public static void PrintArray(int[] x) {
		for (int i : x)
			System.out.printf(" %d,", i);
		System.out.printf("\n");
	}
}
