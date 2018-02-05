package pl.ndsm.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.ndsm.model.userInfo.UserApp;

@Repository
public interface UserDao extends CrudRepository<UserApp, Long> {
	public boolean existsByUsername(String login);
	public UserApp findByUsername(String username);
	public UserApp save(UserApp user);
}
