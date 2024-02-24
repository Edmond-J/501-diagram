import java.awt.Color;

import ecs100.UI;

public class PPM {
	private double x;
	private double y;
	private int row;
	private int colunm;
	private Color co[][];

	PPM() {
	}

	PPM(int r, int c, Color co[][]) {
		this.row = r;
		this.colunm = c;
		this.co = co;
	}

	PPM(double x, double y, int r, int c, Color co[][]) {
		this.x = x;
		this.y = y;
		this.row = r;
		this.colunm = c;
		this.co = co;
	}

	void draw() {
		for (int r = 0; r < row; r++) {
			for (int l = 0; l < colunm; l++) {
				UI.setColor(co[r][l]);
				UI.drawRect(l+x, r+y, 1, 1);
			}
		}
	}


	boolean containsOf(double xn, double yn) {
		if (xn > x && yn > y && xn < (x+colunm) && yn < (y+row)) {
			return true;
		} else {
			return false;
		}
	}


	void rotate() {
		Color clone[][] = co.clone();// 克隆一个镜像,clone和co指向同一个地址
		co = new Color[colunm][row];// 初始化co数组，co撤销指向原地址的指针。交换长宽比
		for (int r = 0; r < row; r++) {
			for (int l = 0; l < colunm; l++) {
//				UI.setColor(clone[r][l]);// 利用镜像里的数据来设置颜色
//				UI.drawRect(row-r+(int)x, l+(int)y, 1, 1);
				// 输出到画布上(row-r,l)的位置
				co[l][row-r-1] = clone[r][l];
			}
		}
		int temp;
		temp = row;
		row = colunm;
		colunm = temp;// 交换行列，这样可以用新的行列数据保存文件，并可以连续旋转。
	}

	void invert() {
		for (int r = 0; r < row; r++) {
			for (int l = 0; l < colunm; l++) {
				int red = co[r][l].getRed();
				int green = co[r][l].getGreen();
				int blue = co[r][l].getBlue();
				co[r][l] = new Color(255-red, 255-green, 255-blue);
				UI.setColor(co[r][l]);
				UI.drawRect(l+x, r+y, 1, 1);
			}
		}
	}
	
	 void drag(double x1, double y1,double x2,double y2){
			x = x+(x2-x1);
			if (x < 0) {
				x = 1;
			}
			y = y+(y2-y1);
			if (y < 52) {
				y = 52;
			}
	 }

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getRow() {
		return row;
	}

	public int getColunm() {
		return colunm;
	}

	public Color[][] getCo() {
		return co;
	}
	 
	 
}
