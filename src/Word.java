import java.awt.Color;

import ecs100.UI;

public class Word extends Shape {
	private String body;

	public Word(double x, double y, int r, int g, int b, int size, String body) {
		super.setX(x);
		super.setY(y);
		super.setC(new Color(r, g, b));
		super.setSize(size);
		this.body = body;
	}

	public void draw() {
		UI.setColor(getC());
		UI.setFontSize(getSize());
		UI.drawString(body, getX(), getY());
	}

	public boolean containsOf(double xn, double yn) {
		if (xn > getX() && yn > getY()-getSize() && xn < (getX()+getSize()/2*body.length()) && yn < getY()) {
			return true;
		} else {
			return false;
		}
	}

	public String write() {
		String str = "Word "+getX()+" "+getY()+" "+getC().getRed()+" "+getC().getGreen()+" "+getC().getBlue()+" "+getSize()+" "+body;
		return str;
	}
}
