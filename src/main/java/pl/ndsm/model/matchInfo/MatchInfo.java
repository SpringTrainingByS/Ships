package pl.ndsm.model.matchInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pl.ndsm.model.userInfo.UserApp;

@Entity
public class MatchInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	@JoinColumn(name = "user_id_1")
	private UserApp user1;
	
	@OneToOne
	@JoinColumn(name = "user_id_2")
	private UserApp user2;
	
	public MatchInfo() {
	
	}

	public MatchInfo(UserApp user1, UserApp user2) {
		this.user1 = user1;
		this.user2 = user2;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserApp getUser1() {
		return user1;
	}

	public void setUser1(UserApp user1) {
		this.user1 = user1;
	}

	public UserApp getUser2() {
		return user2;
	}

	public void setUser2(UserApp user2) {
		this.user2 = user2;
	}
	
	
	
	
}
