package pl.ndsm.model.userInfo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class UserState {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private UserApp user;
	
	@OneToOne
	@JoinColumn(name = "state_id")
	private StateOfUser state;

	public UserState() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserApp getUser() {
		return user;
	}

	public void setUser(UserApp user) {
		this.user = user;
	}

	public StateOfUser getState() {
		return state;
	}

	public void setState(StateOfUser state) {
		this.state = state;
	}
	
	
}
