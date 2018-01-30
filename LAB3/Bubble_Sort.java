public class Bubble_Sort {
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
		BubbleSort(x);
		System.out.println("BubbleSort排序後:");
		PrintArray(x);

	}

	public static void BubbleSort(int[] x) {
		// Bubble Sort
		for (int i = 0; i < x.length; i++) {
			for (int j = 0; j < (x.length - 1 - i); j++) {
				if (x[j] > x[j + 1]) {
					// swap
					int temp;
					temp = x[j + 1];
					x[j + 1] = x[j];
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
