package pl.ndsm.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pl.ndsm.model.userInfo.User;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
	public boolean existsByLogin(String login);
	public User save(User user);
}
