
public class Scorer {
	private String name;
	private int score;
	public Scorer(){
		setName("");
		setScore(0);
	}
	public Scorer(String name, int score){
		setName(name);
		setScore(score);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
}
