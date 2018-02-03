public class plot_star_graphic {
	public static void main(String[] srgs) {
		int N = 10;
		char mark = '#';
		graphic1(mark, N);
		graphic2(mark, N);
		graphic3(mark, N);
		graphic4(mark, N);
	}

	public static void graphic1(char mark, int N) {
		for (int i = 0; i < N; i++) {
			int inner_loop_num = i+1;
			for (int j = 0; j < inner_loop_num; j++) {
				System.out.printf("%c", mark);
			}
			System.out.printf("\n");
		}
		System.out.printf("\n");
	}
	public static void graphic2(char mark, int N) {
		for (int i = 0; i < N; i++) {
			int inner_loop_num = i;
			for (int j = 0; j < inner_loop_num; j++) {
				System.out.printf(" ");
			}
			for (int j = inner_loop_num; j<N;j++){
				System.out.printf("%s", mark);
			}
			System.out.printf("\n");
		}
		System.out.printf("\n");
	}
	
	public static void graphic3(char mark, int N) {
		for (int i = 9; i >= 0; i--) {
			int inner_loop_num = i;
			for (int j = 0; j < inner_loop_num; j++) {
				System.out.printf(" ");
			}
			for (int j = inner_loop_num; j < N; j++) {
				System.out.printf("%c", mark);
			}
			System.out.printf("\n");
		}
		System.out.printf("\n");
	}
	public static void graphic4(char mark, int N) {
		for (int i = 0; i < N; i++) {
			int inner_loop_num;
			if (i<=4){
				inner_loop_num = i + 1;
			}else{
				inner_loop_num =  N - i;
			}
			
			for (int j = 0; j < inner_loop_num; j++) {
				System.out.printf("%c", mark);
			}
			System.out.printf("\n");
		}
		System.out.printf("\n");
	}
}
