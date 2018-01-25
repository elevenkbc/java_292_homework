import java.util.Scanner;
public class guess_number_1a2b_game {
	public static void main(String[] args) {
		
		// 生成四個不相同的數字字串，當作目標數字
		String ans_str = "" + ((int) (Math.random()*10));
		boolean generate_done = false;
		while(generate_done == false) {
			char temp_char = (char)(((int) (Math.random()*10) ) + '0');
			int ans_str_len = ans_str.length();
			for(int i = 0; i < ans_str_len; i++) {
				if(temp_char == ans_str.charAt(i)) {
					break;
				} else if( i == (ans_str_len - 1)){
					ans_str = ans_str + temp_char;
				}
				if(ans_str.length() == 4)
					generate_done = true;
			}		
		}
		// 生成四個不相同的數字結束
		Scanner input = new Scanner(System.in);
		System.out.println("是否先顯示答案(測試BUG用) (Y/N)");
		char is_dip = (char) input.next().charAt(0);
		if ((is_dip == 'Y')||(is_dip == 'y')) {
			System.out.println("答案為" + ans_str);
		}
		
		
		//防止白目使用者亂輸入    開始
		boolean guess_done = false;
		while (guess_done == false) {
			String input_Str = "";
			boolean input_correct = false;;
			while (input_correct == false) {
				System.out.println("請輸入四位數字(數字不重複)");
				input_Str = input.next();
				//確認是數字位數正確
				if(input_Str.length() != 4) {
					System.out.println("長度錯誤!!");
					input_correct = false;
				} else {
					input_correct = true;
				}
				if (input_correct) {
					//確認是否是數字
					for (int i = 0; i<4;i++) {
						if((input_Str.charAt(i) < '0')||('9'<input_Str.charAt(i)))
							input_correct = false;
					}
					if(input_correct == false) {
						System.out.println("請輸入數字!!");
					}
				}
				if (input_correct) {
					//確認是否有重複數字，若有重複請使用者重新輸入
					for (int i = 0; i < 4; i++) {
						for (int j = i+1; j < 4; j++) {
							if (input_Str.charAt(i) == input_Str.charAt(j)) {
								input_correct = false;
							}
						}
					}
					if(input_correct == false) {
						System.out.println("輸入數字有重複!!");
					}
				}
			}
			//防止白目使用者亂輸入    結束
			
			// 判斷 ?A ?B A為位置對數字也正確，B為位置錯數字正確
			int num_A = 0, num_B = 0; // 將 A,B初始化為0
			
			//分別對使用者輸入字串的四個數字，進行A、B判別]
			for (int i = 0; i < 4; i++) {
				// 先搜尋使用者輸入第i個數字是否有A(位置正確數字正確)
				if (input_Str.charAt(i) == ans_str.charAt(i)) {
					num_A = num_A + 1;
				} else {
					// 接著搜尋使用者輸入第i個數字是否有B
					// 列舉答案四個數字，確認是否有相同
					for (int j = 0; j < 4; j++) {
						if (input_Str.charAt(i) == ans_str.charAt(j)) {
							num_B = num_B + 1;
						}
					}
				}
				
			}
			
			if (num_A == 4) {
				System.out.println("好棒棒!! 你答對了!!");
				guess_done = true;
			} else {
				System.out.printf("%d A %d B 請繼續輸入 \n", num_A, num_B);
			}
			
		}
		
		input.close(); //釋放 Scanner 物件 
	}

}
