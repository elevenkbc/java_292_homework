import java.util.Scanner;
public class Expection_of_guess_number {
	public static void main(String[] args) {
		// 電腦隨機猜數字次數，總共模擬simulated_num次，並且計算勝利機率期望值
		Scanner input = new Scanner(System.in);
		int simulated_num;
		System.out.printf("電腦隨機猜數字次數，總共模擬 N 次。\n N = ");
		simulated_num = input.nextInt();
		boolean input_done = false;
		boolean is_printf = true;
		while(input_done == false) {
			System.out.println("是否模擬過程 printf 出? 是(Y)/否(N)");
			char answer_chr = input.next().charAt(0);
			if ((answer_chr == 'Y')||(answer_chr == 'y')||(answer_chr == '是')) {
				is_printf = true;
				input_done = true;
			}else if ((answer_chr == 'N')||(answer_chr == 'n')||(answer_chr == '否')){
				is_printf = false;
				input_done = true;
			}else {
				System.out.println("輸入錯誤");
				input_done = false;
			}
		}
		input.close();

			

		int num_of_win = 0; //電腦成功勝利的總次數
		for (int iter_com = 0; iter_com < simulated_num; iter_com++) {
			
			
			int Target_number = (int) (Math.random() * 100 + 1);
			int lower_bound = 1, upper_bound = 100;
			boolean game_done = false;
			while (game_done == false) {
				if (is_printf)
					System.out.printf("請猜一個%d ~ %d之間的數字 : ", lower_bound, upper_bound);
				int int_com_guess;
				// 電腦在範圍內，隨機猜一個數字
				int_com_guess = (int) (Math.random()*(upper_bound-lower_bound +1) + lower_bound);
				if (is_printf)
					System.out.println("AI猜的數字是 : " + int_com_guess);
				// 確認電腦輸入是否在 lower_bound ~upper_bound 間
				boolean input_in_region = false;
				if ((lower_bound <= int_com_guess) && (int_com_guess <= upper_bound))
					input_in_region = true;
				if (input_in_region == false) {
					if (is_printf)
						System.out.printf("錯誤!! 請輸入在 %d ~ %d 間的整數\n", lower_bound, upper_bound);
					continue; // 如果 input_in_region 是false 則顯示錯誤並重新輸入
				}
				
				//判斷是否猜中
				if (int_com_guess == Target_number) {
					//若答對了
					game_done = true;
					if (is_printf)
						System.out.println("猜對了~~ 好棒棒!!");
					num_of_win += 1;
				}else if (Target_number > int_com_guess){
					//若答案比猜的數字還大時
					lower_bound = int_com_guess + 1;
					if (lower_bound == upper_bound) {
						game_done = true;
						if (is_printf)
							System.out.printf("GG~~ Gameover!! 正確答案為 %d \n", Target_number);
					}
					
				}else if (Target_number < int_com_guess){
					//若答案比猜的數字還小時
					upper_bound = int_com_guess - 1;
					if (lower_bound == upper_bound) {
						game_done = true;
						if (is_printf)
							System.out.printf("GG~~ Gameover!! 正確答案為 %d \n", Target_number);
					}
				}

			}
		}
		System.out.printf("總共模擬 %d 次 ，電腦勝利%d 次 \n",simulated_num, num_of_win);
		System.out.printf("在範圍內均勻隨機亂猜的策略下，電腦獲勝機率為 %.4f", (1.0*num_of_win/simulated_num));
	}

}
