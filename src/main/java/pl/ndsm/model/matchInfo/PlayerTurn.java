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
@Table(name = "player_turn")
public class PlayerTurn {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	@JoinColumn(name = "match_info_id")
	private MatchInfo matchInfo;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private UserApp user;

	public PlayerTurn() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MatchInfo getMatchInfo() {
		return matchInfo;
	}

	public void setMatchInfo(MatchInfo matchInfo) {
		this.matchInfo = matchInfo;
	}

	public UserApp getUser() {
		return user;
	}

	public void setUser(UserApp user) {
		this.user = user;
	}

}
