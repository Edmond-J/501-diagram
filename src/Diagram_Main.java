import ecs100.UI;
import ecs100.UIFileChooser;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Diagram_Main {
	private int page = 1;
	private int pageMax;
	private boolean slideMode = false;
	private boolean dragging;
	private Shape shSelect;
	private Color pen;// the current color
	private int penSize = 2;
	private double x1, y1, x2, y2;// coordinate for mouse action
	private double R = 127, G = 127, B = 127;// RGB slider
	private ArrayList<PPM> ppmList = new ArrayList<PPM>();
	private ArrayList<Shape> shaList = new ArrayList<Shape>();

	public Diagram_Main() {
		UI.initialise();
		UI.addButton("Open diagram", this::loadSelectJAM);
		UI.addButton("Open image", this::loadPPM);
		UI.addButton("Slide show mode", this::slideShow);
		UI.addButton("New slide page", this::newPage);
		UI.addButton("Save", this::saveSlidePage);
		UI.addButton("Save as", this::saveAs);
		UI.addSlider("R:", 0, 255, this::setR);
		UI.addSlider("G:", 0, 255, this::setG);
		UI.addSlider("B:", 0, 255, this::setB);
		UI.addSlider("Pen:", 0, 6, this::setPenSize);
		layout();
		UI.setMouseListener(this::buttonClickDoMouse);
	}

	/*implement the tool bar*/
	public void layout() {
		UI.setColor(Color.white);
		UI.fillRect(0, 0, 700, 50);// tool bar
		UI.setColor(Color.gray);
		UI.setLineWidth(2);
		UI.drawRect(0, 0, 50, 50);// button frame
		UI.drawRect(50, 0, 50, 50);
		UI.drawRect(100, 0, 50, 50);
		UI.drawRect(150, 0, 50, 50);
		UI.drawRect(200, 0, 50, 50);
		UI.drawRect(250, 0, 50, 50);
		UI.drawRect(300, 0, 50, 50);
		UI.drawRect(350, 0, 50, 50);
		UI.drawRect(400, 0, 50, 50);
		UI.drawRect(450, 0, 50, 50);
		UI.drawRect(500, 0, 50, 50);
		UI.drawImage("img/img-diagram/line.jpg", 52, 2);
		UI.drawImage("img/img-diagram/rect.jpg", 102, 2);
		UI.drawImage("img/img-diagram/oval.jpg", 152, 2);
		UI.drawImage("img/img-diagram/word.jpg", 202, 2);
		UI.drawImage("img/img-diagram/table.jpg", 252, 2);
		UI.drawImage("img/img-diagram/drag.jpg", 302, 2);
		UI.drawImage("img/img-diagram/inver.jpg", 352, 2);
		UI.drawImage("img/img-diagram/form.jpg", 402, 2);
		UI.drawImage("img/img-diagram/dele.jpg", 452, 2);
		UI.drawImage("img/img-diagram/cross.jpg", 502, 2);
		UI.setColor(Color.black);
		UI.setLineWidth(1);
		UI.drawRect(0, 51, 801, 601);// work area
		penConfig();
	}

	/*highlight the button in the tool bar*/
	public void activeButton(int i) {
		UI.clearText();
		UI.setColor(Color.blue);
		UI.setLineWidth(5);
		UI.drawLine(50*i+2, 48, 48+50*i, 48);
	}

	public void buttonClickDoMouse(String action, double x, double y) {
		layout();
		if (action.equals("clicked")) {
			if (x > 0 && x < 50 && y > 0 && y < 50) {
				activeButton(0);
				fillColor();
				UI.println("Fill: click inside a shape to fill the color");
			}
			if (x > 50 && x < 100 && y > 0 && y < 50) {
				activeButton(1);
				drawLine();
				UI.println("Draw Line: drag your mouse to draw a line");
			}
			if (x > 100 && x < 150 && y > 0 && y < 50) {
				activeButton(2);
				drawRectangle();
				UI.println("Draw Rectangle: drag your mouse to draw a\nrectangle");
			}
			if (x > 150 && x < 200 && y > 0 && y < 50) {
				activeButton(3);
				drawOval();
				UI.println("Draw Oval: drag your mouse to draw a oval");
			}
			if (x > 200 && x < 250 && y > 0 && y < 50) {
				activeButton(4);
				drawString();
				UI.println("Insert String: click on the pane to add a\nstring, and input the size and the body");
			}
			if (x > 250 && x < 300 && y > 0 && y < 50) {
				activeButton(5);
				drawTable();
				UI.println("Insert Table: drag your mouse to provide\nthe dimension, and input the row and colunm");
			}
			if (x > 300 && x < 350 && y > 0 && y < 50) {
				activeButton(6);
				rotateDrag();
				UI.println("Rotate&Drag: click on a object to rotate\n(only rectangle, oval and image), drag a object\nto move");
			}
			if (x > 350 && x < 400 && y > 0 && y < 50) {
				activeButton(7);
				invert();
				UI.println("Invert: click on a image to invert it");
			}
			if (x > 400 && x < 450 && y > 0 && y < 50) {
				activeButton(8);
				formatShape();
				UI.println("Formation: drag your mouse to encircle a\nset of shapes, it will reset them to average\nsize and position");
			}
			if (x > 450 && x < 500 && y > 0 && y < 50) {
				activeButton(9);
				delete();
				UI.println("Delete: click on a object to delete it");
			}
			if (x > 500 && x < 550 && y > 0 && y < 50) {
				activeButton(10);
				drawCross();
				UI.println("Draw Line: drag your mouse to draw a horizontal\nor vertical line");
			}
		}
	}

	public void setR(double r) {
		R = r;
//		UI.println("R: "+R);
		penConfig();
	}

	public void setG(double g) {
		G = g;
//		UI.println("G: "+G);
		penConfig();
	}

	public void setB(double b) {
		B = b;
//		UI.println("B: "+B);
		penConfig();
	}

	public void setPenSize(double p) {
		penSize = (int)p;
		penConfig();
	}

	/*configure the RGB channel and the pen size*/
	public void penConfig() {
		pen = new Color((int)R, (int)G, (int)B);
		UI.setColor(pen);
		UI.setLineWidth(penSize);
		UI.fillRect(1, 1, 48, 48);// color indicator
	}

	/*	clear the graphics area and redraw all the elements*/
	public void refresh() {
		UI.setColor(Color.white);
		UI.fillRect(1, 52, 800, 600);
		for (PPM p : ppmList) {
			p.draw();
		}
		for (Shape sh : shaList) {
			sh.draw();
		}
//		UI.setColor(Color.white);
//		UI.fillRect(0, 653, 1000, 200);
//		UI.fillRect(802, 0, 200, 800);
	}

	public void refreshShape() {
		UI.setColor(Color.white);
		UI.fillRect(1, 52, 800, 600);
		for (Shape sh : shaList) {
			sh.draw();
		}
		UI.setColor(Color.white);
		UI.fillRect(0, 653, 1000, 200);
		UI.fillRect(802, 0, 200, 800);
	}

	public int countFiles(String path) {
		File folder = new File(path);
		int count = 0;
		File[] files = folder.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				count++;
			}
		}
		return count;
	}

	public void slideShow() {
		UI.clearText();
		slideMode = true;
		if (countFiles("data/ppt-diagram") == 0) {
			newPage();
		}
		try {
			ppmList = new ArrayList<PPM>();
			shaList = new ArrayList<Shape>();
			refresh();
			Scanner sc = new Scanner(new File("data/ppt-diagram/"+page));
			loadJAM(sc);
			UI.setColor(Color.white);
			UI.fillRect(710, 0, 800, 50);
			UI.setFontSize(15);
			UI.setColor(Color.darkGray);
			UI.drawString("page "+page, 715, 45);
			UI.setFontSize(25);
			UI.drawString("Slide", 715, 25);
			UI.println("Next Page: 'space','right','1'\nPrevious Page: 'backspace','left','2'");
		} catch (IOException e) {
			UI.println("Error: "+e);
		}
		UI.setKeyListener(this::slideShowOnKey);
	}

	public void slideShowOnKey(String action) {
		pageMax = countFiles("data/ppt-diagram");
		if (action.equals("空格") || action.equals("向右箭头") || action.equals("1")) {
			try {
				PrintStream out = new PrintStream(new File("data/ppt-diagram/"+page));
				saveAuto(out);
				if (page < pageMax) {
					page++;
					slideShow();
				} else {
					UI.println("Last page!");
				}
			} catch (IOException e) {
				UI.println("Error");
			}
		}
		if (action.equals("Backspace") || action.equals("向左箭头") || action.equals("2")) {
			try {
				PrintStream out = new PrintStream(new File("data/ppt-diagram/"+page));
				saveAuto(out);
				if (page > 1) {
					page--;
					slideShow();
				} else {
					UI.println("First page!");
				}
			} catch (IOException e) {
				UI.println("Error");
			}
		}
	}

	public void newPage() {
		slideMode = true;
		pageMax = countFiles("data/ppt-diagram");
		ppmList = new ArrayList<PPM>();
		shaList = new ArrayList<Shape>();
		refresh();
		try {
			pageMax++;
			PrintStream out = new PrintStream(new File("data/ppt-diagram/"+pageMax));
			saveAuto(out);
			page = pageMax;
			slideShow();
		} catch (IOException e) {
		}
	}

	public void loadSelectJAM() {
		try {
			Scanner sc = new Scanner(new File(UIFileChooser.open()));
			loadJAM(sc);
		} catch (IOException e) {
			UI.println("Error: "+e);
		}
	}

	public void loadJAM(Scanner sc) {
		ppmList = new ArrayList<PPM>();
		shaList = new ArrayList<Shape>();
		refresh();
		while (sc.hasNext()) {
			String indicate = sc.nextLine();
			if (!indicate.equals("P3")) {
				break;
			}
			int colunm = sc.nextInt();
			int row = sc.nextInt();
			sc.nextLine();
			Color[][] co = new Color[row][colunm];
			double x = sc.nextDouble();
			double y = sc.nextDouble();
			sc.nextLine();
			sc.nextLine();
			while (sc.hasNextInt()) {
				for (int r = 0; r < row; r++) {
					for (int l = 0; l < colunm; l++) {
						co[r][l] = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
						UI.setColor(co[r][l]);
						UI.drawRect(l+x, r+y, 1, 1);
					}
				}
			}
			UI.setColor(pen);
			sc.nextLine();
			ppmList.add(new PPM(x, y, row, colunm, co));
		}
		while (sc.hasNext()) {
			String shape = sc.next();
			switch (shape) {
			case "Rectangle":
				Rectangle rec = new Rectangle(sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(),
						sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextBoolean(), sc.nextInt(),
						sc.nextInt(), sc.nextInt());
				rec.draw();
				shaList.add(rec);
				break;
			case "Line":
				Line lin = new Line(sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextInt(),
						sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextBoolean());
				lin.draw();
				shaList.add(lin);
				break;
			case "Oval":
				Oval ova = new Oval(sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextDouble(), sc.nextInt(),
						sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextBoolean(), sc.nextInt(), sc.nextInt(),
						sc.nextInt());
				ova.draw();
				shaList.add(ova);
				break;
			case "Word":
				Word wor = new Word(sc.nextDouble(), sc.nextDouble(), sc.nextInt(), sc.nextInt(), sc.nextInt(),
						sc.nextInt(), sc.nextLine().strip());
				wor.draw();
				shaList.add(wor);
				break;
			case "Table":
				Table tab = new Table(sc.nextDouble(), sc.nextDouble(), sc.nextInt(), sc.nextInt(), sc.nextDouble(),
						sc.nextDouble(), sc.nextInt());
				tab.draw();
				shaList.add(tab);
				break;
			}
		}
		sc.close();
	}

	public void loadPPM() {
//		UI.setColor(new Color(/* red */ 1.0f, /* green */ 1.0f, /* blue */ 1.0f));
//		UI.fillRect(0, 0, 1000, 1000);
		try {
			Scanner sc = new Scanner(new File(UIFileChooser.open()));
			sc.nextLine();
			int colunm = sc.nextInt();
			int row = sc.nextInt();
			Color[][] co = new Color[row][colunm];
			sc.nextLine();
			sc.nextLine();
			while (sc.hasNext()) {
				for (int r = 0; r < row; r++) {
					for (int l = 0; l < colunm; l++) {
						co[r][l] = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
						UI.setColor(co[r][l]);
						UI.drawRect(l+5, r+55, 1, 1);
					}
				}
			}
			UI.setColor(pen);
			ppmList.add(new PPM(5, 55, row, colunm, co));
			sc.close();
		} catch (IOException e) {
			UI.println("Error: "+e);
		}
	}

	public void fillColor() {
		UI.setMouseListener(this::fillColorDoMouse);
	}

	public void fillColorDoMouse(String action, double x, double y) {
		if (action.equals("clicked") && y < 50) {
			buttonClickDoMouse(action, x, y);
		}
		if (action.equals("clicked")) {
			for (Shape sh : shaList) {
				if (sh.containsOf(x, y)) {
					sh.fill(pen);
				}
			}
		}
	}

	public void drawLine() {
		UI.setMouseListener(this::drawLineDoMouse);
	}

	public void drawLineDoMouse(String action, double x, double y) {
		if (action.equals("clicked") && y < 50) {
			buttonClickDoMouse(action, x, y);
		}
		if (y > 50) {
			if (action.equals("pressed")) {
				x1 = x;
				y1 = y;
			}
			if (action.equals("released")) {
				x2 = x;
				y2 = y;
				penConfig();
				Line lin = new Line(x1, y1, x2, y2, pen, penSize, false);
				lin.draw();
				shaList.add(lin);
			}
		}
	}

	public void drawCross() {
		UI.setMouseListener(this::drawCrossDoMouse);
	}

	public void drawCrossDoMouse(String action, double x, double y) {
		if (action.equals("clicked") && y < 50) {
			buttonClickDoMouse(action, x, y);
		}
		if (y > 50) {
			if (action.equals("pressed")) {
				x1 = x;
				y1 = y;
			}
			if (action.equals("released")) {
				x2 = x;
				y2 = y;
				penConfig();
				Line lin = new Line(x1, y1, x2, y2, pen, penSize, true);
				lin.drawCross();
				shaList.add(lin);
			}
		}
	}

	public void drawRectangle() {
		UI.setMouseListener(this::drawRectangleDoMouse);
	}

	public void drawRectangleDoMouse(String action, double x, double y) {
		if (action.equals("clicked") && y < 50) {
			buttonClickDoMouse(action, x, y);
		}
		if (y > 50) {
			if (action.equals("pressed")) {
				x1 = x;
				y1 = y;
			}
			if (action.equals("released")) {
				x2 = x;
				y2 = y;
				penConfig();
				UI.drawRect(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
				Rectangle rec = new Rectangle(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2), pen,
						penSize, false);
				shaList.add(rec);
			}
		}
	}

	public void drawOval() {
		UI.setMouseListener(this::drawOvalDoMouse);
	}

	public void drawOvalDoMouse(String action, double x, double y) {
		if (action.equals("clicked") && y < 50) {
			buttonClickDoMouse(action, x, y);
		}
		if (y > 50) {
			if (action.equals("pressed")) {
				x1 = x;
				y1 = y;
			}
			if (action.equals("released")) {
				x2 = x;
				y2 = y;
				penConfig();
				UI.drawOval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
				Oval ova = new Oval(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2), pen, penSize,
						false);
				shaList.add(ova);
			}
		}
	}

	public void drawString() {
		UI.setMouseListener(this::drawStringDoMouse);
	}

	public void drawStringDoMouse(String action, double x, double y) {
		if (action.equals("clicked") && y < 50) {
			buttonClickDoMouse(action, x, y);
		}
		if (y > 50) {
			if (action.equals("clicked")) {
				int size = UI.askInt("size?");
				String str = UI.askString("input:");
				penConfig();
//				UI.drawString(str, x, y);
				Word wor = new Word(x, y, pen.getRed(), pen.getGreen(), pen.getBlue(), size, str);
				wor.draw();
				shaList.add(wor);
			}
		}
	}

	public void drawTable() {
		UI.setMouseListener(this::drawTableDoMouse);
	}

	public void drawTableDoMouse(String action, double x, double y) {
		if (action.equals("clicked") && y < 50) {
			buttonClickDoMouse(action, x, y);
		}
		if (y > 50) {
			if (action.equals("pressed")) {
				x1 = x;
				y1 = y;
			}
			if (action.equals("released")) {
				x2 = x;
				y2 = y;
				int row = UI.askInt("number of rows:");
				int colunm = UI.askInt("number of colunm:");
				Table tab = new Table(Math.min(x1, x2), Math.min(y1, y2), Math.max(x1, x2), Math.max(y1, y2), row,
						colunm, penSize);
				tab.draw();
				shaList.add(tab);
			}
		}
	}

	public void invert() {
		UI.setMouseListener(this::invertDoMouse);
	}

	public void invertDoMouse(String action, double x, double y) {
		if (action.equals("clicked") && y < 50) {
			buttonClickDoMouse(action, x, y);
		}
		if (action.equals("clicked")) {
			for (PPM p : ppmList) {
				if (p.containsOf(x, y)) {
					p.invert();
				}
			}
		}
	}

	public void rotateDrag() {
		UI.setMouseMotionListener(this::rotateDragDoMouse);
	}

	public void rotateDragDoMouse(String action, double x, double y) {
		if (action.equals("clicked") && y < 50) {
			buttonClickDoMouse(action, x, y);
		}
		if (action.equals("clicked")) {
			for (int i = ppmList.size()-1; i >= 0; i--) {
				PPM p = ppmList.get(i);
				if (p.containsOf(x, y)) {
					p.rotate();
					ppmList.add(p);
					ppmList.remove(p);
					refresh();
					break;
				}
			}
			for (int i = shaList.size()-1; i >= 0; i--) {
				Shape sh = shaList.get(i);
				if (sh.containsOf(x, y)) {
					sh.rotate();
					shaList.add(sh);
					shaList.remove(sh);
					refresh();
					break;
				}
			}
		}
		// move
		if (action.equals("pressed")) {
			x1 = x;
			y1 = y;
		}
		if (action.equals("dragged")) {
			x2 = x;
			y2 = y;
			for (int i = shaList.size()-1; i >= 0 && !dragging; i--) {
				if (shaList.get(i).containsOf(x1, y1)) {
					shSelect = shaList.get(i);
					dragging = true;
					shaList.add(shaList.get(i));
					shaList.remove(shaList.get(i));
//					refresh(); 
					break;
				}
			}
			if (shSelect != null) {
				shSelect.drag(x1, y1, x2, y2);
				refreshShape();
				x1 = x2;
				y1 = y2;
			}// smoothly dragging
		}
		if (action.equals("released")) {
			x2 = x;
			y2 = y;
			dragging = false;
			shSelect = null;
			refresh();
			for (int i = ppmList.size()-1; i >= 0; i--) {
				if (ppmList.get(i).containsOf(x1, y1)) {
					ppmList.get(i).drag(x1, y1, x2, y2);
					ppmList.add(ppmList.get(i));
					ppmList.remove(ppmList.get(i));
					refresh();
					break;
				}
			}
		}
	}

	public void formatShape() {
		UI.setMouseListener(this::formatShapeDoMouse);
	}

	public void formatShapeDoMouse(String action, double x, double y) {
		if (action.equals("clicked") && y < 50) {
			buttonClickDoMouse(action, x, y);
		}
		if (action.equals("pressed")) {
			x1 = x;
			y1 = y;
		}
		if (action.equals("released")) {
			x2 = x;
			y2 = y;
			double sumx = 0;
			double sumy = 0;
			double sumw = 0;
			double suml = 0;
			int count = 0;
			for (Shape sh : shaList) {
				if (sh.isInsideOf(x1, y1, x2, y2)) {
					sumx += sh.getX();
					sumy += sh.getY();
					sumw += sh.getWidth();
					suml += sh.getLength();
					count++;
				}
			}
			for (Shape sh : shaList) {
				if (sh.isInsideOf(x1, y1, x2, y2)) {
					if (Math.abs(x2-x1) > Math.abs(y2-y1)) {
						sh.setY(sumy/count);
						sh.setLength(suml/count);
						sh.setWidth(sumw/count);
					} else {
						sh.setX(sumx/count);
						sh.setLength(suml/count);
						sh.setWidth(sumw/count);
					}
				}
			}
			refresh();
		}
	}

	public void delete() {
		UI.setMouseListener(this::deleteDoMouse);
	}

	public void deleteDoMouse(String action, double x, double y) {
		if (action.equals("clicked") && y < 50) {
			buttonClickDoMouse(action, x, y);
		}
		if (y > 50) {
			if (action.equals("clicked")) {
				for (Shape sh : shaList) {
					if (sh.containsOf(x, y)) {
						shaList.remove(sh);
						refresh();
						break;
					}
				}
				for (PPM p : ppmList) {
					if (p.containsOf(x, y)) {
						ppmList.remove(p);
						refresh();
						break;
					}
				}
			}
		}
	}

	public void saveSlidePage() {
		try {
			if (slideMode) {
				PrintStream out = new PrintStream(new File("data/ppt-diagram/"+page));
				saveAuto(out);
			} else {
				saveAs();
			}
			UI.println("page "+page+" saved successfully");
		} catch (IOException e) {
			UI.println("Error: "+e);
		}
	}

	public void saveAs() {
		try {
			PrintStream out = new PrintStream(new File(UIFileChooser.save()));
			saveAuto(out);
		} catch (IOException e) {
			UI.println("Error: "+e);
		}
	}

	public void saveAuto(PrintStream out) {
		for (PPM p : ppmList) {
			out.println("P3");
			out.printf("%d %d\n", p.getColunm(), p.getRow());
			out.printf("%.1f %.1f\n", p.getX(), p.getY());
			out.println(255);
			int enter = 1;
			for (int r = 0; r < p.getRow(); r++) {
				for (int l = 0; l < p.getColunm(); l++, enter++) {
					out.print(p.getCo()[r][l].getRed()+" ");
					out.print(p.getCo()[r][l].getGreen()+" ");
					out.print(p.getCo()[r][l].getBlue()+" ");
					if (enter%510 == 0) {
						out.println();
					}
				}
			}
			out.println();
		}
		out.println("*************************");
		for (Shape s : shaList) {
			out.println(s.write());
		}
		out.close();
	}

	public static void main(String[] args) {
		System.setProperty("prism.allowhidpi", "true");
		new Diagram_Main();
	}
}
