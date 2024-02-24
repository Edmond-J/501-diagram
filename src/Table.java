import java.awt.Color;

import ecs100.UI;

public class Table extends Shape {
	private int row;
	private int col;
//	private double width;
//	private double length;

	Table() {
	}

	Table(double x, double y, int r, int c, double w, double l, int size) {
		super.setX(x);
		super.setY(y);
		super.setWidth(w);
		super.setLength(l);
		row=r;
		col=c;
		super.setSize(size);
	}

	Table(double x1, double y1, double x2, double y2, int r, int c, int size) {
		super.setX(x1);
		super.setY(y1);
		super.setWidth(x2-x1);
		super.setLength(y2-y1);
		row=r;
		col=c;
		super.setSize(size);
	}
	public void drag(double x1, double y1, double x2, double y2) {
		setX(getX()+(x2-x1));
		if (getX() < 0) {
			setX(0);
		}
		if(getX()>800-getWidth()){
			setX(800-getWidth());
		}
		setY(getY()+(y2-y1));
		if (getY() < 53+getLength()/row/2) {
			setY(53+getLength()/row/2);
		}
		if(getY()>650-getLength()){
			setY(650-getLength());
		}
	}

	public void draw() {
		
		UI.setColor(Color.lightGray);
		UI.fillRect(getX(), getY()-getLength()/row/2, getWidth(), getLength()/row/2);
		UI.setLineWidth(getSize());
		UI.setColor(Color.black);
		for (int i = 0; i <= col; i++) {
			UI.drawLine(getX()+getWidth()/col*i, getY()-getLength()/row/2, getX()+getWidth()/col*i, getY()+getLength());
		}
		for (int i = 0; i <= row; i++) {
			UI.drawLine(getX(), getY()+getLength()/row*i, getX()+getWidth(), getY()+getLength()/row*i);
		}
		UI.drawLine(getX(), getY()-getLength()/row/2, getX()+getWidth(), getY()-getLength()/row/2);
		
	}

	public boolean containsOf(double xn, double yn) {
		if (xn > getX() && yn > getY() && xn < (getX()+getWidth()) && yn < (getY()+getLength())) {
			return true;
		} else {
			return false;
		}
	}

	public String write() {
		String str = "Table "+getX()+" "+getY()+" "+row+" "+col+" "+getWidth()+" "+getLength()+" "+getSize();
		return str;
	}
}
