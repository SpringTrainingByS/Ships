package pl.ndsm.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.ndsm.model.userInfo.UserApp;

@Repository
public interface UserDao extends CrudRepository<UserApp, Long> {
	public boolean existsByUsername(String login);
	public UserApp findByUsername(String username);
	public UserApp findById(long id);
	public UserApp save(UserApp user);
	
	@Query(value = "SELECT id FROM user_app WHERE username = :username", nativeQuery = true)
	public Long findIdByUsername(@Param("username") String username); 
	
	@Query(value = "SELECT id FROM user_app LIMIT 1", nativeQuery = true)
	public long findFirstUser();
}
