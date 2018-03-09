import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JFrame;

class blocks { // 一堆方塊的集合，初始位置均在畫面正中間上面兩格
	int[][] Positionx; // x座標
	int[][] Positiony; // y座標
	char[] blocks_char; // 每一個不同方塊對應的字元
	int current_block; // 當前掉落中方塊的index
	int Max_blocks; //要掉落的方塊最大的index
	int width;
	int height;
	
	boolean block_drop() {
		// 方塊掉落函數
		// 回傳是否 GameOver
		int[] tempy = new int[4];
		for (int i = 0; i < 4; i++)
			tempy[i] = Positiony[current_block][i] + 1; // 每一個方塊部件往下移動一格

		if (Is_overlap(current_block, Positionx[current_block], tempy)) {
			current_block += 1; // 目前移動的 current_block index + 1
			return Is_Game_Over(tempy);
		} else if ((tempy[0] >= height) || (tempy[1] >= height) || (tempy[2] >= height) || (tempy[3] >= height)) {
			current_block += 1; // 目前移動的 current_block index + 1
			return false;
		} else {
			// 若沒有撞到底，也沒有與其他方塊重疊，則往下移動一格
			for (int i = 0; i < 4; i++)
				Positiony[current_block][i] = tempy[i];
			return false; // 回傳尚未GameOver
		}

	}
	boolean Is_no_blocks() {
		if(current_block ==  Max_blocks) {
			return true;
		}else {
			return false;
		}
	}
	boolean Is_Game_Over(int[] tempy) {
		//如果目前方塊有一個部件Y座標高於遊戲最高點
		//則回傳 true
		if ((tempy[0] < 0) || (tempy[1] < 0) || (tempy[2] < 0) || (tempy[3] < 0)) {
			return true;
		} else {
			return false;
		}
		
	}
	boolean Is_overlap(int current_block, int[] tempx, int[] tempy) {
		// 確認當前移動中放塊，是否有跟以往的任一個方塊有重疊
		// 當前移動方塊x座標 tempx， 當前移動方塊 y座標 tempy
		boolean overlap_with_other_block = false;
		for (int i = 0; i < current_block; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					if ((Positionx[i][j] == tempx[k]) && (Positiony[i][j] == tempy[k])) {
						overlap_with_other_block = true;
						break;
					}
				}
			}
		}
		return overlap_with_other_block;
	}
	boolean Is_inside(int[] currentx, int[] currenty) {
		//確認 currentx currenty 是否在遊戲邊界之中
		boolean Inside = true;
		for(int i = 0; i<currentx.length; i++) {
			if((currentx[i] < 1)||( width < currentx[i]) ||(currenty[i] < 0)||( height <= currenty[i])) {
				Inside = false;
			}
		}
		return Inside;
	}
	void rotate_cw(int[] x, int[] y) {
		// 順時針90度旋轉
		int[] tempx = new int[4];
		int[] tempy = new int[4];
		// 1. 先將中心移動到(0,0)
		for (int i = 0; i < 4; i++) {
			tempx[i] = x[i] - x[0];
			tempy[i] = y[i] - y[0];
		}
		// 2. x座標變號
		for (int i = 0; i < 4; i++) {
			if (tempx[i] != 0)
				tempx[i] = (-1) * tempx[i];
		}
		// 3. x座標 y座標 交換
		int temp;
		for (int i = 0; i < 4; i++) {
			temp = tempx[i];
			tempx[i] = tempy[i];
			tempy[i] = temp;
		}

		// 4. 將方塊中心移回 x[0] , y[0]
		for (int i = 0; i < 4; i++) {
			tempx[i] = tempx[i] + x[0];
			tempy[i] = tempy[i] + y[0];
		}

		// 輸出座標
		for (int i = 0; i < 4; i++) {
			x[i] = tempx[i];
			y[i] = tempy[i];
		}
	}
	void rotate_ccw(int[] x, int[] y) {
		//逆時針90度旋轉
		int[] tempx = new int[4];
		int[] tempy = new int[4];
		// 1. 先將中心移動到(0,0)
		for (int i = 0; i < 4; i++) {
			tempx[i] = x[i] - x[0];
			tempy[i] = y[i] - y[0];
		}

		// 2. x座標 y座標 交換
		int temp;
		for (int i = 0; i < 4; i++) {
			temp = tempx[i];
			tempx[i] = tempy[i];
			tempy[i] = temp;
		}
		// 3. x座標變號
		for (int i = 0; i < 4; i++) {
			if (tempx[i] != 0)
				tempx[i] = (-1) * tempx[i];
		}

		// 4. 將方塊中心移回 x[0] , y[0]
		for (int i = 0; i < 4; i++) {
			tempx[i] = tempx[i] + x[0];
			tempy[i] = tempy[i] + y[0];
		}
		
		//輸出座標
		for (int i = 0; i < 4; i++) {
			x[i] = tempx[i];
			y[i] = tempy[i];
		}
	}
	char[][] blocks_to_draw_format(){
		char[][] output = new char[height][width+2];		
		for (int i = 0; i<=current_block; i++) {
			for (int j = 0; j<4; j++) {
				if ((0<=Positiony[i][j])&&(Positiony[i][j]<height)&&(1<=Positionx[i][j])&&(Positionx[i][j]<width+1)) {
					output[Positiony[i][j]][Positionx[i][j]] = blocks_char[i];
				}
			}
		}
		return output;
	}
	boolean is_vanish_event(boolean[] vanish_row) {
		//回傳是否有消去方塊得分事件，並且更新vanish_row
		boolean output = false;
		//將 vanish_row 歸零
		for (int i = 0; i<height; i++) {
			vanish_row[i] = false;
		}
		int[] accumulated = new int[height];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < current_block; j++) {
				for (int k = 0; k < 4; k++) {
					if (Positiony[j][k] == i) {
						accumulated[i] += 1;
					}
				}
			}
			if(accumulated[i] == width) {
				output = true;
				vanish_row[i] = true;
			}
		}
		
		return output;
	}	
	blocks(int width, int height) { // constructor
		this.width = width;
		this.height = height;
		Max_blocks = 150;
		Positionx = new int[Max_blocks][4];
		Positiony = new int[Max_blocks][4];
		blocks_char = new char[Max_blocks];
		current_block = 0;
		
		char[] CharTable = { 'A', 'B', 'C', 'D', 'E' };
		for (int i = 0; i < Max_blocks; i++) {
			blocks_char[i] = CharTable[i%5];
		}
		
		
		int random_shape_type; // 每一個方塊的形狀，利用隨機生成
		int random_rotate; //每一個方塊的隨機旋轉, 0,1,2,3, 表順時針旋轉 0 90 180 270 度
		int initialx = (this.width)/2; // 掉落中 方塊中心的起始x座標
		int initialy = -2; // 掉落中 方塊中心的起始y座標
		
		for (int i = 0; i < Max_blocks; i++) {
			random_shape_type = (int) (Math.random() * 6) + 1; //隨機產生方塊型態
			random_rotate = (int) (Math.random() * 4); //隨機產生方塊旋轉
			// (i的中心座標x, i的中心座標y) (Positionx[i][0], Positiony[i][0])
			switch (random_shape_type) {
			case 1:
				// 方塊型態1: (中間為旋轉中心)
				//   X
				//   X
				//   X X
				Positionx[i][0] = initialx;
				Positionx[i][1] = initialx;
				Positionx[i][2] = initialx;
				Positionx[i][3] = initialx + 1;
				Positiony[i][0] = initialy;
				Positiony[i][1] = initialy - 1;
				Positiony[i][2] = initialy + 1;
				Positiony[i][3] = initialy + 1;
				break;
			case 2:
				// 方塊型態2: (左上為旋轉中心)
				// X X
				// X X
				Positionx[i][0] = initialx;
				Positionx[i][1] = initialx;
				Positionx[i][2] = initialx + 1;
				Positionx[i][3] = initialx + 1;
				Positiony[i][0] = initialy;
				Positiony[i][1] = initialy + 1;
				Positiony[i][2] = initialy + 1;
				Positiony[i][3] = initialy;
				break;
			case 3:
				// 方塊型態3: (中下為旋轉中心)
				//   X
				// X X X
				Positionx[i][0] = initialx;
				Positionx[i][1] = initialx;
				Positionx[i][2] = initialx - 1;
				Positionx[i][3] = initialx + 1;
				Positiony[i][0] = initialy;
				Positiony[i][1] = initialy - 1;
				Positiony[i][2] = initialy;
				Positiony[i][3] = initialy;
				break;
			case 4:
				// 方塊型態4: (中右為旋轉中心)
				//   X
				//   X X
				//     X
				Positionx[i][0] = initialx;
				Positionx[i][1] = initialx - 1;
				Positionx[i][2] = initialx - 1;
				Positionx[i][3] = initialx;
				Positiony[i][0] = initialy;
				Positiony[i][1] = initialy - 1;
				Positiony[i][2] = initialy;
				Positiony[i][3] = initialy + 1;
				break;
			case 5:
				// 方塊型態5: (中右為旋轉中心)
				//     X
				//   X X
				//   X
				Positionx[i][0] = initialx;
				Positionx[i][1] = initialx;
				Positionx[i][2] = initialx - 1;
				Positionx[i][3] = initialx - 1;
				Positiony[i][0] = initialy;
				Positiony[i][1] = initialy - 1;
				Positiony[i][2] = initialy;
				Positiony[i][3] = initialy + 1;
				break;
			case 6:
				// 方塊型態6: (上面數下來第二個為旋轉中心)
				//     X
				//     X
				//     X
				//     x
				Positionx[i][0] = initialx;
				Positionx[i][1] = initialx;
				Positionx[i][2] = initialx;
				Positionx[i][3] = initialx;
				Positiony[i][0] = initialy;
				Positiony[i][1] = initialy - 1;
				Positiony[i][2] = initialy + 1;
				Positiony[i][3] = initialy + 2;
				break;
			default:

			}
			//隨機順時針旋轉 random_rotate 次
			for (int j = 0; j < random_rotate; j++)
				rotate_cw(Positionx[i], Positiony[i]);

		}

	}
}

public class Tetris extends JFrame {
	static int width = 17; // 遊戲邊框的寬
	static int height = 20; // 遊戲邊框的高
	static int score = 0;
	static int sleep_time = 100; //延遲時間
	static char keyboard_input = '0'; //0 表示未接受任何鍵盤輸入
	static boolean GameOver = false;
	static boolean[] vanish_row = new boolean[height];
	static blocks BLOCKS = new blocks(width, height); // 存放全部有關方塊的data

	public static void main(String... arg) throws IOException, InterruptedException {
		new Tetris(); // new一個視窗來監聽鍵盤
		while (!GameOver && !BLOCKS.Is_no_blocks()) {
			
			//第一次更新畫面，執行掉落方塊函數，也執行接受鍵盤按鍵函數
			GameOver = BLOCKS.block_drop();		
			keyboard_event_update(BLOCKS);
			if (GameOver||BLOCKS.Is_no_blocks()) {
				break;
			}else {
				Draw();
			}
			Thread.sleep(sleep_time);
			CleanScreen();
			
			//第二次更新畫面，只執行接受鍵盤按鍵函數
			keyboard_event_update(BLOCKS);
			if (GameOver||BLOCKS.Is_no_blocks()) {
				break;
			}else {
				Draw();
			}
			Thread.sleep(sleep_time);
			CleanScreen();

		}
		if (GameOver) {
			Draw_GG_screen();
		} else if (BLOCKS.Is_no_blocks()) {
			Draw_No_block_screen();
		}
	}
	public static void CleanScreen() throws IOException, InterruptedException {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();// 清除螢幕
	}

	public static void keyboard_event_update(blocks B) {
		int[] tempx = new int[4];
		int[] tempy = new int[4];
		boolean is_overlap;
		boolean is_inside;
		switch (keyboard_input) {
		case 's':
			// 方塊順時針旋轉 90 度
			// 先把 XY 座標複製出來
			for (int i = 0; i < B.Positionx[B.current_block].length; i++) {
				tempx[i] = B.Positionx[B.current_block][i];
				tempy[i] = B.Positiony[B.current_block][i];
			}

			B.rotate_cw(tempx, tempy);
			is_overlap = B.Is_overlap(B.current_block, tempx, tempy);
			is_inside = B.Is_inside(tempx, tempy);

			// 如果尚重疊 且在範圍內 則旋轉
			if ((!is_overlap) && is_inside) {
				for (int i = 0; i < 4; i++) {
					B.Positionx[B.current_block][i] = tempx[i];
					B.Positiony[B.current_block][i] = tempy[i];
				}
			}
			break;
		case 'w':
			// 方塊逆時針旋轉 90 度
			for (int i = 0; i < B.Positionx[B.current_block].length; i++) {
				tempx[i] = B.Positionx[B.current_block][i];
				tempy[i] = B.Positiony[B.current_block][i];
			}

			B.rotate_ccw(tempx, tempy);
			is_overlap = B.Is_overlap(B.current_block, tempx, tempy);
			is_inside = B.Is_inside(tempx, tempy);

			// 如果尚重疊 且在範圍內 則旋轉
			if ((!is_overlap) && is_inside) {
				for (int i = 0; i < 4; i++) {
					B.Positionx[B.current_block][i] = tempx[i];
					B.Positiony[B.current_block][i] = tempy[i];
				}
			}
			break;
		case 'a':
			// 移動方塊往左
			for (int i = 0; i < 4; i++)
				tempx[i] = B.Positionx[B.current_block][i] - 1;

			is_overlap = B.Is_overlap(B.current_block, tempx, B.Positiony[B.current_block]);
			is_inside = B.Is_inside(tempx, B.Positiony[B.current_block]);

			if ((!is_overlap) && is_inside) {
				for (int i = 0; i < 4; i++)
					B.Positionx[B.current_block][i] = tempx[i];
			}
			break;
		case 'd':
			// 移動方塊往右
			for (int i = 0; i < 4; i++)
				tempx[i] = B.Positionx[B.current_block][i] + 1;

			is_overlap = B.Is_overlap(B.current_block, tempx, B.Positiony[B.current_block]);
			is_inside = B.Is_inside(tempx, B.Positiony[B.current_block]);

			if ((!is_overlap) && is_inside) {
				for (int i = 0; i < 4; i++)
					B.Positionx[B.current_block][i] = tempx[i];
			}
			break;
		}
		keyboard_input = '0'; //回復初始狀態，Keyboard 未接受任何字元
	}
	

	public static void Draw() throws IOException, InterruptedException {
		char[][] draw_format = BLOCKS.blocks_to_draw_format();
		boolean IsVanish = BLOCKS.is_vanish_event(vanish_row);
		if (!IsVanish) {
			//如果沒有消失事件
			// 圖上邊
			System.out.println("   Tetris Game");
			for (int i = 0; i < width + 2; i++)
				System.out.printf("#");
			System.out.printf("\n");
			// 圖中間
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width + 2; j++) {
					if (j == 0) {
						System.out.printf("#");
					} else if (j == width + 1) {
						System.out.printf("#");
					} else {
						System.out.printf("%c", draw_format[i][j]);
					}
				}
				System.out.printf("\n");
			}
			// 圖下邊
			for (int i = 0; i < width + 2; i++)
				System.out.printf("#");
			System.out.printf("\n");
			System.out.println(" 'a' : left, 'd': right");
			System.out.println(" 's' : rotate clockwise");
			System.out.println(" 'w' : rotate counter clockwise");
			System.out.println("Score : " + score);
			System.out.println("Speed : " + sleep_time + "%");

		} else {

			// 如果有消失事件
			// 1. 將要消去的row 用 @ 列印
			// 圖上邊
			System.out.println("   Tetris Game");
			for (int i = 0; i < width + 2; i++)
				System.out.printf("#");
			System.out.printf("\n");
			// 圖中間
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width + 2; j++) {
					if (j == 0) {
						System.out.printf("#");
					} else if (j == width + 1) {
						System.out.printf("#");
					} else if (vanish_row[i]){
						System.out.printf("@");
					}else {
						System.out.printf("%c", draw_format[i][j]);
					}
				}
				System.out.printf("\n");
			}
			// 圖下邊
			for (int i = 0; i < width + 2; i++)
				System.out.printf("#");
			System.out.printf("\n");
			System.out.println(" 'a' : left, 'd': right");
			System.out.println(" 's' : rotate clockwise");
			System.out.println(" 'w' : rotate counter clockwise");
			System.out.println("Score : " + score);
			System.out.println("Speed : " + sleep_time + "%");
			Thread.sleep(sleep_time);
			CleanScreen();
			
			// 2. 將要消去的方塊設定為999
			for ( int i = 0; i<BLOCKS.current_block; i++) {
				for (int  j = 0; j<4; j++) {
					if((0<=BLOCKS.Positiony[i][j])&&(BLOCKS.Positiony[i][j]<height)) {
						if(vanish_row[BLOCKS.Positiony[i][j]]){
							BLOCKS.Positionx[i][j] = 999;
							BLOCKS.Positiony[i][j] = 999;
						}
					}	
				}
			}
			
			// 3. 將要消去的row上面的方塊都移動往下，直到撞到其他方塊，或撞到底板
			int vanish_num = 0;// 計算此次消去幾行
			for (int i = 0; i < height; i++) {
				if (vanish_row[i]) {
					vanish_num += 1;
				}
			}
			
			score += vanish_num*100; //加分
			
			for (int v = 0; v < BLOCKS.height; v++) {
				if (vanish_row[v]) {
					for (int i = 0; i < BLOCKS.current_block; i++) {
						for (int j = 0; j < 4; j++) {
							if ((0 <= BLOCKS.Positiony[i][j]) && (BLOCKS.Positiony[i][j] < height)) {
								if (BLOCKS.Positiony[i][j] < v) {
									BLOCKS.Positiony[i][j] += 1;
								}
							}
						}
					}
				}
			}
			BLOCKS.is_vanish_event(vanish_row); //更新 vanish_row
			
			
			
			draw_format = BLOCKS.blocks_to_draw_format();
			// 圖上邊
			System.out.println("   Tetris Game");
			for (int i = 0; i < width + 2; i++)
				System.out.printf("#");
			System.out.printf("\n");
			// 圖中間
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width + 2; j++) {
					if (j == 0) {
						System.out.printf("#");
					} else if (j == width + 1) {
						System.out.printf("#");
					} else {
						System.out.printf("%c", draw_format[i][j]);
					}
				}
				System.out.printf("\n");
			}
			// 圖下邊
			for (int i = 0; i < width + 2; i++)
				System.out.printf("#");
			System.out.printf("\n");
			System.out.println(" 'a' : left, 'd': right");
			System.out.println(" 's' : rotate clockwise");
			System.out.println(" 'w' : rotate counter clockwise");
			System.out.println("Score : " + score);
			System.out.println("Speed : " + sleep_time + "%");
		}

	}

	public static void Draw_GG_screen() {
		// 列印GAME OVER 畫面
		System.out.println("   Tetris Game");
		for (int i = 0; i < width + 2; i++)
			System.out.printf("#");
		System.out.printf("\n");
		for (int i = 0; i < height; i++) {
			if (i != height / 2) {
				for (int j = 0; j < width + 2; j++) {
					if (j == 0) {
						System.out.printf("#");
					} else if (j == width + 1) {
						System.out.printf("#");
					} else {
						System.out.printf(" ");
					}
				}
				System.out.printf("\n");
			} else {

				System.out.printf("#");
				for (int j = 0; j < (width - 10) / 2; j++) {
					System.out.printf(" ");
				}
				System.out.printf("GAME OVER!");
				for (int j = 0; j < (width - 10) / 2; j++) {
					System.out.printf(" ");
				}
				if (width % 2 != 0)
					System.out.printf(" ");
				System.out.printf("#\n");
			}
		}
		for (int i = 0; i < width + 2; i++)
			System.out.printf("#");
		System.out.printf("\n");
		System.out.println(" 'a' : left, 'd': right");
		System.out.println(" 's' : rotate clockwise");
		System.out.println(" 'w' : rotate counter clockwise");
		System.out.println("Score : " + score);
		System.out.println("Speed : " + sleep_time + "%");
	}
	
	public static void Draw_No_block_screen() {
		// 列印已經沒有可掉落的blocks畫面
		System.out.println("   Tetris Game");
		for (int i = 0; i < width + 2; i++)
			System.out.printf("#");
		System.out.printf("\n");
		for (int i = 0; i < height; i++) {
			if ( (i != height / 2) && (i != (height / 2 + 1))) {
				for (int j = 0; j < width + 2; j++) {
					if (j == 0) {
						System.out.printf("#");
					} else if (j == width + 1) {
						System.out.printf("#");
					} else {
						System.out.printf(" ");
					}
				}
				System.out.printf("\n");
			} else if(i == height / 2){
				System.out.printf("#");
				for (int j = 0; j < (width - 10) / 2; j++) {
					System.out.printf(" ");
				}
				System.out.printf("GAME OVER!");
				for (int j = 0; j < (width - 10) / 2; j++) {
					System.out.printf(" ");
				}
				if (width % 2 != 0)
					System.out.printf(" ");
				System.out.printf("#\n");
			} else {
				System.out.printf("#");
				for (int j = 0; j < (width - 10) / 2; j++) {
					System.out.printf(" ");
				}
				System.out.printf("NO Blocks!");
				for (int j = 0; j < (width - 10) / 2; j++) {
					System.out.printf(" ");
				}
				if (width % 2 != 0)
					System.out.printf(" ");
				System.out.printf("#\n");
			}
		}
		for (int i = 0; i < width + 2; i++)
			System.out.printf("#");
		System.out.printf("\n");
		System.out.println(" 'a' : left, 'd': right");
		System.out.println(" 's' : rotate clockwise");
		System.out.println(" 'w' : rotate counter clockwise");
		System.out.println("Score : " + score);
		System.out.println("Speed : " + sleep_time + "%");
	}
	

	public Tetris() { // constructor
		this.setSize(250, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("監聽鍵盤輸入");
		this.setVisible(true);
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				keyboard_input = Character.toLowerCase(c); //統一轉回小寫
			}
		});
	}
}
