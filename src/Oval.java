import java.awt.Color;

import ecs100.UI;

public class Oval extends Shape {
//	double x;
//	double y;
//	private double width;
//	private double length;
	private boolean solid;
	private Color fill;

	public Oval() {
	}

	public Oval(double x, double y, double w, double l, int r, int g, int b, int size, boolean solid, int rf, int gf,
			int bf) {
		super.setX(x);
		super.setY(y);
		super.setWidth(w);
		super.setLength(l);
		super.setC(new Color(r, g, b));
		super.setSize(size);
		this.solid = solid;
		this.fill = new Color(rf, gf, bf);
	}

	public Oval(double x, double y, double w, double l, Color c, int size, boolean solid) {
		super.setX(x);
		super.setY(y);
		setWidth(w);
		setLength(l);
		super.setC(c);
		super.setSize(size);
		this.solid = solid;
		this.fill = Color.white;
	}

	public boolean containsOf(double xn, double yn) {
		double f = (xn-(getX()+getWidth()/2))*(xn-(getX()+getWidth()/2))/(getWidth()*getWidth()/4)
				+(yn-(getY()+getLength()/2))*(yn-(getY()+getLength()/2))/(getLength()*getLength()/4);
		if (f < 1) {
			return true;
		} else return false;
	}

	public void draw() {
		if (solid) {
			fill();
		}
		UI.setColor(getC());
		UI.setLineWidth(getSize());
		UI.drawOval(getX(), getY(), getWidth(), getLength());
	}

	public void fill(Color c) {
		UI.setColor(c);
		UI.fillOval(getX(), getY(), getWidth(), getLength());
		fill = new Color(c.getRed(), c.getGreen(), c.getBlue());
		solid = true;
	}

	public void fill() {
		UI.setColor(fill);
		UI.fillOval(getX(), getY(), getWidth()-getSize(), getLength());
		solid = true;
	}

	public void rotate() {
		double temp = getWidth();
		setWidth(getLength());
		setLength(temp);
	}

	public String write() {
		String str = "Oval "+getX()+" "+getY()+" "+getWidth()+" "+getLength()+" "+getC().getRed()+" "+getC().getGreen()+" "+getC().getBlue()+" "+getSize()+" "
				+solid+" "+fill.getRed()+" "+fill.getGreen()+" "+fill.getBlue();
		return str;
	}
}
