import java.awt.Color;

public abstract class Shape {
	private double x;
	private double y;
	private Color c;
	private int size;
	private double width;
	private double length;

	public abstract void draw();

	public void fill() {
	}

	public void fill(Color c) {
	}

	public boolean containsOf(double xn, double yn) {
		return false;
	}

	public boolean isInsideOf(double x1, double y1, double x2, double y2) {
		if (x > Math.min(x1, x2) && x < Math.max(x1, x2) && y > Math.min(y1, y2) && y < Math.max(y1, y2)) {
			return true;
		} else {
			return false;
		}
	}

	public void rotate() {
	}

	public String write() {
		return null;
	}

	public void drag(double x1, double y1, double x2, double y2) {
		x = x+(x2-x1);
		if (x < 0) {
			x = 0;
		}
		if (x > 800-width) {
			x = 800-width;
		}
		y = y+(y2-y1);
		if (y < 53) {
			y = 53;
		}
		if (y > 650-length) {
			y = 650-length;
		}
	}

	public void move(double x2, double y2) {
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
}
