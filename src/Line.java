import java.awt.Color;

import ecs100.UI;

public class Line extends Shape {
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	boolean rightAng;

	public Line() {
	}

	public Line(double x1, double y1, double x2, double y2, int r, int g, int b, int s, boolean ra) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		super.setC(new Color(r, g, b));
		super.setSize(s);
		this.rightAng = ra;
	}

	public Line(double x1, double y1, double x2, double y2, Color c, int s, boolean ra) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		super.setC(c);
		super.setSize(s);
		this.rightAng = ra;
	}

	public void drag(double x1, double y1, double x2, double y2) {
		this.x1 += x2-x1;
		this.x2 += x2-x1;
		this.y1 += y2-y1;
		this.y2 += y2-y1;
	}

	public void drawCross() {
		UI.setColor(getC());
		UI.setLineWidth(getSize());
		if (Math.abs(x2-x1) < Math.abs(y2-y1)) {
			UI.drawLine(x1, y1, x1, y2);
		} else {
			UI.drawLine(x1, y1, x2, y1);
		}
	}

	public void draw() {
		if(!rightAng){
		UI.setColor(getC());
		UI.setLineWidth(getSize());
		UI.drawLine(x1, y1, x2, y2);
		}
		else{
			drawCross();
		}
	}

	// void erase(){
//		UI.setColor(c);
//		UI.setLineWidth(size);
//		UI.invertLine(x1, y1, x2, y2);
//	}
	public boolean containsOf(double xn, double yn) {
		double m = (y2-y1)/(x2-x1);
		if (xn >= Math.min(x1, x2) && xn <= Math.max(x1, x2) && yn > (m*xn+y1-m*x1-5) && yn < (m*xn+y1-m*x1+5)) {
			return true;
		} else {
			return false;
		}
	}

	public String write() {
		String str = null;
		str = "Line "+x1+" "+y1+" "+x2+" "+y2+" "+getC().getRed()+" "+getC().getGreen()+" "+getC().getBlue()+" "+getSize()+" "+rightAng;
		return str;
	}
}
