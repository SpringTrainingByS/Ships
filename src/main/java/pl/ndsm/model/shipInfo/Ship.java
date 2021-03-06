package pl.ndsm.model.shipInfo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import pl.ndsm.model.userInfo.UserApp;

@Entity
public class Ship {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "state_of_ship_id")
	private StateOfShip stateOfShip;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private UserApp user;
	
	@Column(name = "shot_count", columnDefinition = "Integer default 0")
	private int shotCount;
	
	private int size;

	public Ship() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public StateOfShip getStateOfShip() {
		return stateOfShip;
	}

	public void setStateOfShip(StateOfShip stateOfShip) {
		this.stateOfShip = stateOfShip;
	}

	public UserApp getUser() {
		return user;
	}

	public void setUser(UserApp user) {
		this.user = user;
	}

	public int getShotCount() {
		return shotCount;
	}

	public void setShotCount(int shotCount) {
		this.shotCount = shotCount;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Ship [id=" + id + ", stateOfShip=" + stateOfShip + ", user=" + user + ", shotCount=" + shotCount
				+ ", size=" + size + "]";
	}
	
	
	
}
