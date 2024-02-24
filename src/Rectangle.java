import java.awt.Color;

import ecs100.UI;

public class Rectangle extends Shape {
//	double x;
//	double y;
//	private double width;
//	private double length;
	private boolean solid;
	private Color fill;

	public Rectangle() {
	}

	public Rectangle(double x, double y, double w, double l, int r, int g, int b, int size, boolean sol, int rf, int gf,
			int bf) {
		super.setX(x);
		super.setY(y);
		super.setWidth(w);
		super.setLength(l);
		super.setC(new Color(r, g, b));
		super.setSize(size);
		solid = sol;
		fill = new Color(rf, gf, bf);
	}

	public Rectangle(double x, double y, double w, double l, Color c, int size, boolean sol) {
		super.setX(x);
		super.setY(y);
		super.setWidth(w);
		super.setLength(l);
		super.setC(c);
		super.setSize(size);
		solid = sol;
		fill = Color.white;
	}
	
	public boolean containsOf(double xn, double yn) {
		if (xn > getX() && yn > getY() && xn < (getX()+getWidth()) && yn < (getY()+getLength())) {
			return true;
		} else {
			return false;
		}
	}

	public void draw() {
		if (solid) {
			fill();
		}
		UI.setColor(getC());
		UI.setLineWidth(getSize());
		UI.drawRect(getX(), getY(), getWidth(), getLength());
	}

	public void fill(Color c) {
		UI.setColor(c);
		UI.fillRect(getX()+getSize()/2, getY()+getSize()/2, getWidth()-getSize(), getLength()-getSize());
		fill = new Color(c.getRed(), c.getGreen(), c.getBlue());
		solid = true;
	}

	public void fill() {
		UI.setColor(fill);
		UI.fillRect(getX()+getSize()/2, getY()+getSize()/2, getWidth()-getSize(), getLength()-getSize());
		solid = true;
	}

	public void rotate() {
		double temp = getWidth();
		setWidth(getLength());
		setLength(temp);
	}

	public String write() {
		String str = "Rectangle "+getX()+" "+getY()+" "+getWidth()+" "+getLength()+" "+getC().getRed()+" "+getC().getGreen()+" "+getC().getBlue()+" "+getSize()
				+" "+solid+" "+fill.getRed()+" "+fill.getGreen()+" "+fill.getBlue();
		return str;
	}
}
