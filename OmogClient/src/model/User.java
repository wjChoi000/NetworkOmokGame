package model;

public class User {
	private String id;
	private String password;
	private String name;
	private int win;
	private int lose;
	private int draw;
	
	public User(String id, String password, String name, int win, int lose, int draw){
		this.id = id;
		this.password = password;
		this.name = name;
		this.win = win;
		this.lose = lose;
		this.draw = draw;
	}
	public User(){
		
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWin() {
		return win;
	}
	public void setWin(int win) {
		this.win = win;
	}
	public int getLose() {
		return lose;
	}
	public void setLose(int lose) {
		this.lose = lose;
	}
	public int getDraw() {
		return draw;
	}
	public void setDraw(int draw) {
		this.draw = draw;
	}
	
	public String toStringRating(){
		return win+" / "+lose+" / "+draw;
	}
}
