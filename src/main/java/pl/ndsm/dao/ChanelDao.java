package pl.ndsm.dao;

import org.springframework.data.repository.CrudRepository;

import pl.ndsm.model.userInfo.Chanel;

public interface ChanelDao extends CrudRepository<Chanel, Long> {
	public Chanel save(Chanel websocket);
}
