import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import java.io.IOException;

class snake {
	int x, y, L;
	int[] tailx, taily;

	snake(int W, int H) {
		x = W/2;
		y = H/2;
		L = 0;
		tailx = new int[W*H];
		taily = new int[W*H];
	}

	void eatfood() {
		this.L += 1;
	}
}

class map {
	int height;
	int width;
	map(int W, int H){
		height = H;
		width = W;
	}
}

class food {
	int x;
	int y;

	void reset(int W, int H, snake s) {
		this.x = (int) (Math.random() * W + 1);
		this.y = (int) (Math.random() * H);
		// 食物是否掉在頭上
		if ((this.x == s.x) && (this.y == s.y)) {
			s.eatfood();
			reset(W, H, s);
		}
		// 食物是否掉在尾巴上
		if (s.L > 0) {
			boolean Food_on_tail = false;
			for (int i = 0; i < s.L; i++) {
				if ((this.x == s.tailx[i]) && (this.y == s.taily[i]))
					Food_on_tail = true;
			}
			if (Food_on_tail)
			{
				s.eatfood();
				reset(W, H, s);
			}		
		}
	}

	food(int W, int H) {
		this.x = (int) (Math.random() * W + 1);
		this.y = (int) (Math.random() * H);
	}

}

public class SnakeGame_nowall extends JFrame {

	static char dir = '0';// 原始狀態是停止的
	static int score = 0;
	static boolean GameOver = false;
	static int Width = 20; 
	static int Height = 16;
	static int Speed = 0;
	
	public static void main(String... arg) throws IOException, InterruptedException {
		
		
		new SnakeGame_nowall(); // new一個視窗來監聽鍵盤
		snake S = new snake(Width, Height);
		map M = new map(Width, Height);
		food F = new food(Width, Height);
		
		//用一個迴圈來控制貪吃蛇遊戲
		while (!GameOver) {
			CleanScreen();
			Update(S, F, M, dir, Width, Height);
			Draw(S, M, F, GameOver);
			Speed = UpdateSpeed(score);
		}

	}
	
	public static int UpdateSpeed(int score) throws InterruptedException {
		if((270 - score)>100) {
			Thread.sleep(270 - score);
			return (int) (score/(1.7));
		}else {
			Thread.sleep(100);
			return 100;
		}
		
		//分數超過100達到最快
		
	}
	public static void Update(snake s, food f, map m, char dir, int W, int H) {
		if (s.L > 0) {
			// 若是有尾巴，就更新尾巴
			for (int i = s.L - 1; i >= 1; i--) {
				// 尾巴的後一節跟著前面一節
				s.tailx[i] = s.tailx[i - 1];
				s.taily[i] = s.taily[i - 1];
			}
			// 尾巴的第一節跟著頭
			s.tailx[0] = s.x;
			s.taily[0] = s.y;
		}
		//更新頭的位置
		switch (dir) {
		case 'w':
			s.y -= 1;
			break;
		case 's':
			s.y += 1;
			break;
		case 'a':
			s.x -= 1;
			break;
		case 'd':
			s.x += 1;
			break;
		}
		// 是否撞到牆 
		if((s.x == 0)||(s.x == m.width+1)||(s.y == -1)||(s.y == m.height)) {
			//撞到牆壁後，就從對面的牆壁跑出來
			if(s.x == 0){
				s.x = m.width;
			} else if(s.x == m.width+1){
				s.x = 1;
			} else if(s.y == -1){
				s.y = m.height - 1;
			} else { // s.y = m.height 
				s.y = 0;
			}
		}
		// 是否撞到身體
		for (int i = 0; i < s.L; i++) {
			if ((s.x == s.tailx[i]) && (s.y == s.taily[i])) {
				GameOver = true;
			}
		}
		// 如果吃到食物
		if ((s.x == f.x) && (s.y == f.y)) {
			f.reset(W, H, s);
			s.eatfood();
			score += 10;
		}

	}

	public static void CleanScreen() throws IOException, InterruptedException {
		new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();// 清除螢幕
	}

	public static void Draw(snake s, map m, food f, boolean Gameover) {
		int W = m.width;
		int H = m.height;
		int snake_x = s.x;
		int snake_y = s.y;
		int food_x = f.x;
		int food_y = f.y;
		for (int i = 0; i < W + 2; i++)
			System.out.printf("#");
		System.out.printf("\n");

		//如果還在進行遊戲的話
		if (!Gameover) {
			// 地圖中間
			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W + 2; j++) {
					if (j == 0) {
						System.out.printf("#");
					} else if (j == W + 1) {
						System.out.printf("#");
					} else if ((i == food_y) && (j == food_x)) {
						System.out.printf("F");
					} else if ((i == snake_y) && (j == snake_x)) {
						System.out.printf("Q");
					} else if (s.L > 0) {
						boolean is_tail = false;
						for (int k = 0; k < s.L; k++) {
							if ((s.tailx[k] == j) && (s.taily[k] == i)) {
								System.out.printf("O");
								is_tail = true;
								break;
							}
						}
						if (!is_tail)
							System.out.printf(" ");
					} else {
						System.out.printf(" ");
					}
				}
				System.out.printf("\n");
			}
		}else {
		//如果已經GG了，列印GAME OVER 畫面
			for (int i = 0; i < H; i++) {
				if (i!= H/2) {
					for (int j = 0; j < W + 2; j++) {
						if (j == 0) {
							System.out.printf("#");
						} else if (j == W + 1) {
							System.out.printf("#");
						} else {
							System.out.printf(" ");
						}
					}
					System.out.printf("\n");
				}else {
					 
					System.out.printf("#");
					for (int j = 0; j<(W-10)/2; j++) {
						System.out.printf(" ");
					}
					System.out.printf("GAME OVER!");
					for (int j = 0; j<(W-10)/2; j++) {
						System.out.printf(" ");
					}
					if(W%2 != 0)
						System.out.printf(" ");
					System.out.printf("#\n");
				}	
			}
			
		}
		
		for (int i = 0; i < W + 2; i++)
			System.out.printf("#");
		System.out.printf("\n");
		System.out.println("Score : " + score);
		System.out.println("Speed : " + Speed +"%");
	}

	public SnakeGame_nowall() { // constructor
		this.setSize(250, 150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("監聽鍵盤輸入");
		this.setVisible(true);
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				dir = e.getKeyChar();
			}
		});
	}
}
