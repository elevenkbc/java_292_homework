import java.util.Scanner;

public class guess_number_game {
	public static void main(String[] args) {
		int Target_number = (int) (Math.random() * 100 + 1);
		int lower_bound = 1, upper_bound = 100;
		boolean game_done = false;
		Scanner input = new Scanner(System.in);
		while (game_done == false) {
			System.out.printf("請猜一個%d ~ %d之間的數字 : ", lower_bound, upper_bound);
			String user_guess;
			user_guess = input.nextLine();
			// 確認使用者輸入是數字
			boolean input_is_num = true;
			for (int i = 0; i < user_guess.length(); i++) {
				// 逐字檢查String 是否全為 0~9 間的數字
				if ((user_guess.charAt(i) < '0') || ('9' < user_guess.charAt(i)))
					input_is_num = false;
			}
			if (input_is_num == false) {
				System.out.println("錯誤!! 請輸入數字!!");
				continue; // 如果 input_is_num 是false 則顯示錯誤並重新輸入
			}
			int int_user_guess = Integer.parseInt(user_guess); // 將user_guess 轉型成 int
			// 確認使用者輸入是否在 lower_bound ~upper_bound 間
			boolean input_in_region = false;
			if ((lower_bound <= int_user_guess) && (int_user_guess <= upper_bound))
				input_in_region = true;
			if (input_in_region == false) {
				System.out.printf("錯誤!! 請輸入在 %d ~ %d 間的整數\n", lower_bound, upper_bound);
				continue; // 如果 input_in_region 是false 則顯示錯誤並重新輸入
			}	
			//判斷是否猜中
			if (int_user_guess == Target_number) {
				//若答對了
				game_done = true;
				System.out.println("猜對了~~ 好棒棒!!");
			}else if (Target_number > int_user_guess){
				//若答案比猜的數字還大時
				lower_bound = int_user_guess + 1;
				if (lower_bound == upper_bound) {
					//當沒有數字可以猜時，遊戲GG
					game_done = true;
					System.out.printf("GG~~ Gameover!! 正確答案為 %d", Target_number);
				}	
			}else if (Target_number < int_user_guess){
				//若答案比猜的數字還小時
				upper_bound = int_user_guess - 1;
				if (lower_bound == upper_bound) {
					//當沒有數字可以猜時，遊戲GG
					game_done = true;
					System.out.printf("GG~~ Gameover!! 正確答案為 %d", Target_number);
				}
			}
		}
		input.close();
	}
}
