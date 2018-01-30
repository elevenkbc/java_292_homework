import java.util.Arrays; //為了使用 Array copy

public class Compare_Sort_time {
	public static void main(String[] args) {
		// 產生一個 N*2000的 2D array
		// 其每一個 row 為一組要排序的 array
		int N = 500;
		int[][] test_data = new int[N][2000];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < 2000; j++)
				test_data[i][j] = j;
		}

		// 把每一個row 都洗亂
		for (int i = 0; i < N; i++) {
			shuffle(test_data[i]);
		}
<<<<<<< HEAD

		// Copy test_data �� InsertionSort �Ƨ�
		// �Ҧ����ƧǪk���O��ۦP���Ǫ�test_data�A���դ~����
		// �ƻs2D test_data
		int[][] data_InsertionSort = new int[N][2000];
		for (int i = 0; i < N; i++) {
=======
		
		//Copy test_data 
		//使得不同的的排序法，均對相同順序的test_data做排序，這樣測試才公平
		//複製2D test_data
		int[][] data_InsertionSort= new int[N][2000];
		for(int i = 0; i<N;i++) {
>>>>>>> 3dc36eb94f48a2a61dbcdc0b626e4d0edc60a0ec
			data_InsertionSort[i] = Arrays.copyOf(test_data[i], test_data[i].length);
		}
		int[][] data_BubbleSort = new int[N][2000];
		for (int i = 0; i < N; i++) {
			data_BubbleSort[i] = Arrays.copyOf(test_data[i], test_data[i].length);
		}
		int[][] data_SelectionSort = new int[N][2000];
		for (int i = 0; i < N; i++) {
			data_SelectionSort[i] = Arrays.copyOf(test_data[i], test_data[i].length);
		}
		int[][] data_QuickSort = new int[N][2000];
		for (int i = 0; i < N; i++) {
			data_QuickSort[i] = Arrays.copyOf(test_data[i], test_data[i].length);
		}
<<<<<<< HEAD
		// �ƻs2D test_data ����

		System.out.printf("%d �����׬� 2000 ��int[] �Ƨ�:\n", N);
=======
		//複製2D test_data 結束
		
		
		System.out.printf("%d 筆長度為 2000 的int[] 排序:\n",N);
>>>>>>> 3dc36eb94f48a2a61dbcdc0b626e4d0edc60a0ec
		long t1, t2;
		t1 = System.currentTimeMillis();
		// 對每一個row 做InsertionSort，並計時
		for (int i = 0; i < N; i++) {
			InsertionSort(data_InsertionSort[i]);
		}
		t2 = System.currentTimeMillis();
		System.out.printf("InsertionSort花了： %d 毫秒\n", ((t2 - t1)));

		t1 = System.currentTimeMillis();
		// 對每一個row 做BubbleSort，並計時
		for (int i = 0; i < N; i++) {
			BubbleSort(data_BubbleSort[i]);
		}
		t2 = System.currentTimeMillis();
		System.out.printf("BubbleSort花了： %d 毫秒\n", ((t2 - t1)));

		t1 = System.currentTimeMillis();
		// 對每一個row 做SelectionSort，並計時
		for (int i = 0; i < N; i++) {
			SelectionSort(data_SelectionSort[i]);
		}
		t2 = System.currentTimeMillis();
		System.out.printf("SelectionSort花了： %d 毫秒\n", ((t2 - t1)));

		t1 = System.currentTimeMillis();
		// 對每一個row 做Quick_Sort，並計時
		for (int i = 0; i < N; i++) {
			Quick_Sort(data_QuickSort[i], 0, 1999);
		}
		t2 = System.currentTimeMillis();
		System.out.printf("Quick_Sort花了： %d 毫秒\n", ((t2 - t1)));

	}

	public static void Quick_Sort(int[] x, int left, int right) {
		if (right > left) {
			int P = Separate(x, left, right);
			Quick_Sort(x, left, P - 1);
			Quick_Sort(x, P + 1, right);
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

	public static void SelectionSort(int[] x) {
		// Selection Sort
		for (int i = 0; i < x.length; i++) {
			for (int j = (i + 1); j < x.length; j++) {
				if (x[i] > x[j]) {
					// swap
					int temp;
					temp = x[i];
					x[i] = x[j];
					x[j] = temp;
				}
			}
		}
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

	public static void shuffle(int[] x) {
		// Fisher Yates shuffle
		int N = x.length;
		for (int i = N - 1; i >= 0; i--) {
			int j = (int) (Math.random() * (i + 1));
			// swap
			int temp;
			temp = x[j];
			x[j] = x[i];
			x[i] = temp;
		}
	}

	public static void PrintArray(int[] x) {
		for (int i : x)
			System.out.printf(" %d,", i);
		System.out.printf("\n");
	}

}
