package pl.ndsm.dao.matchInfo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ndsm.model.matchInfo.QuantitySunk;

@Repository
@Transactional
public interface QuantitySunkDao extends CrudRepository<QuantitySunk, Long>{
	public QuantitySunk findByUserId(long userId);
	
	@Modifying
	@Query(value = "INSERT INTO quantity_sunk (user_id) VALUES (:userId)", nativeQuery = true)
	public void insert(@Param("userId") long userId);
}
