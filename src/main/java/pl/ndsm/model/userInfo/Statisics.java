package pl.ndsm.model.userInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Statisics {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	private long won;
	
	private long lose;

	public Statisics() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getWon() {
		return won;
	}

	public void setWon(long won) {
		this.won = won;
	}

	public long getLose() {
		return lose;
	}

	public void setLose(long lose) {
		this.lose = lose;
	}
	
	

}
