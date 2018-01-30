public class Insertion_Sort {
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
		InsertionSort(x);
		System.out.println("InsertionSort排序後:");
		PrintArray(x);

	}

	public static void InsertionSort(int[] x) {
		// Insertion Sort
		for (int i = 1; i < x.length; i++) {
			int temp = x[i];
			int search_ind = i - 1;
			while (x[search_ind] > temp) {
				x[search_ind + 1] = x[search_ind];
				x[search_ind] = temp;
				search_ind -= 1;
				if (search_ind < 0)
					break;
			}
		}
	}

	public static void PrintArray(int[] x) {
		for (int i : x)
			System.out.printf(" %d,", i);
		System.out.printf("\n");
	}

}
