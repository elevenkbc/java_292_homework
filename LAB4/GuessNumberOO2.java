import java.util.Scanner;

public class GuessNumberOO2 {
	public static void main(String[] args) {

		// 建立一個猜數字遊戲物件
		GuessNumberGame G = new GuessNumberGame(); //預設建構子
//		GuessNumberGame G = new GuessNumberGame(100, 200); // 自定義建構子
		int Mode = G.SelectMode(); // 模式選擇
		G.Play(Mode); // 遊戲主體
	}

}

class GuessNumberGame {
	// constructor
	public GuessNumberGame(int lower_bound, int upper_bound) {
		this.lower_bound = lower_bound;
		this.upper_bound = upper_bound;
		this.Target_number = (int) (Math.random() * (upper_bound - lower_bound) + lower_bound);
	}

	public GuessNumberGame() {
		lower_bound = 1;
		upper_bound = 100;
		Target_number = (int) (Math.random() * 100 + 1);
	}

	// Attribute & Data
	private int Target_number;
	private int lower_bound, upper_bound;

	// Method & Function
	// public method
	public int SelectMode() {
		Scanner input = new Scanner(System.in);
		boolean Selection_Done = false;
		int input_mode = 0;
		while (!Selection_Done) {
			System.out.println("請選擇遊戲模式");
			System.out.println("1. 使用者遊玩  2.AI(範圍內隨機) 3.AI(Bisection)");
			input_mode = input.nextInt();
			if ((input_mode == 1) || (input_mode == 2) || (input_mode == 3)) {
				Selection_Done = true;
			} else {
				System.out.println("輸入錯誤，請重新輸入!!");
			}
		}
		return input_mode;
	}

	public void Play(int Mode) {
		Scanner input = new Scanner(System.in);
		// 用一個 while loop 控制遊戲的進行
		boolean Game_end = false;
		while (!Game_end) {
			PrintHint();
			boolean GuessCorrect;
			switch (Mode) {
			case 1:
				GuessCorrect = UserGuess(input.nextLine()); // 使用者自己玩
				break;
			case 2:
				GuessCorrect = AIRandomGuess(); // AI1 隨機亂猜
				break;
			default:
				GuessCorrect = AIBisectionGuess(); // AI2 Bisection
				break;
			}
			if (GuessCorrect) {
				// 如果猜中遊戲結束，顯示勝利
				Game_end = true;
			} else {
				// 如果猜錯，檢查是否GG
				// 若還有數字可猜，繼續進行while loop
				Game_end = IsGG();
			}
		}
	}

	// private method
	private void PrintHint() {
		System.out.printf("請猜一個%d ~ %d之間的數字 : \n", lower_bound, upper_bound);
	}

	private boolean UserGuess(String input_str) {
		// 排除各種白目使用者亂輸入
		if (Is_Num(input_str)) {
			if (In_Range(input_str)) {
				// 確定輸入字串為符合條件的數字，再將字串轉為int
				int input_int = Integer.parseInt(input_str);
				return IsGuessCorrect(input_int);
			} else {
				// 輸入不再範圍內，判斷為猜錯
				return false;
			}
		} else {
			// 輸入並非數字，判斷為猜錯
			return false;
		}
	}

	private boolean AIRandomGuess() {
		int ai_guess = (int) (Math.random() * (upper_bound - lower_bound) + lower_bound);
		System.out.println("AI 猜的數字為" + ai_guess);
		return IsGuessCorrect(ai_guess);
	}

	private boolean AIBisectionGuess() {
		// 電腦在範圍內，隨機猜一個數字
		// 電腦的策略為:
		// 猜目前 lower_bound 與 upper_bound 的中間點
		int ai_guess = (int) (upper_bound - lower_bound) / 2 + lower_bound;
		System.out.println("AI 猜的數字為" + ai_guess);
		return IsGuessCorrect(ai_guess);
	}

	private boolean IsGG() {
		if (lower_bound == upper_bound) {
			// 當沒有數字可以猜時，遊戲GG
			System.out.printf("GG~~ Gameover!! 正確答案為 %d", Target_number);
			return true;
		} else {
			return false;
		}
	}

	private boolean IsGuessCorrect(int guess_number) {
		// 判斷是否猜中
		if (guess_number == Target_number) {
			// 若答對了
			System.out.println("猜對了~~ 好棒棒!!");
			return true;
		} else if (Target_number > guess_number) {
			// 若答案比猜的數字還大時
			lower_bound = guess_number + 1;
			return false;
		} else if (Target_number < guess_number) {
			// 若答案比猜的數字還小時
			upper_bound = guess_number - 1;
			return false;
		} else {
			return false;
		}
	}

	private boolean Is_Num(String input_str) {
		// 確認使用者輸入是數字
		boolean result = true;
		for (int i = 0; i < input_str.length(); i++) {
			// 逐字檢查String 是否全為 0~9 間的數字
			if ((input_str.charAt(i) < '0') || ('9' < input_str.charAt(i)))
				result = false;
		}
		if (result == false)
			System.out.printf("錯誤!!請輸入數字\n");
		return result;
	}

	private boolean In_Range(String input_num) {
		// 確認使用者輸入是否在 lower_bound ~upper_bound 間
		int int_user_guess = Integer.parseInt(input_num); // 將user_guess 轉型成 int
		boolean result = false;
		if ((lower_bound <= int_user_guess) && (int_user_guess <= upper_bound))
			result = true;
		if (result == false)
			System.out.printf("錯誤!! 請輸入在 %d ~ %d 間的整數\n", lower_bound, upper_bound);
		return result;
	}
}
