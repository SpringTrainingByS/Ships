package pl.ndsm.model.shipInfo.fieldLocation;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pl.ndsm.model.shipInfo.Ship;

@Entity
@Table(name = "ship_size_2")
public class ShipSize2 extends ShipSize {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ship_id")
	private Ship ship;
	
	private String field1;
	
	private String field2;

	public ShipSize2() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	@Override
	public String toString() {
		return "ShipSize2 [id=" + id + ", ship=" + ship + ", field1=" + field1 + ", field2=" + field2 + "]";
	}
	
}
