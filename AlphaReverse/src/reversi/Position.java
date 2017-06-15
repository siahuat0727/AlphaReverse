package reversi;

public class Position {
	private int x, y;

	Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	Position(Position pos) {
		this.x = pos.getX();
		this.y = pos.getY();
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void print(){
		System.out.print("( "+x+","+y+" ) ");
	}
	
	public String toString(){
		return "( "+x+","+y+" ) ";
	}
}
