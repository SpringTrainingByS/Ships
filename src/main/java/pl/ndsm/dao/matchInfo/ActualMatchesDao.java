package pl.ndsm.dao.matchInfo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pl.ndsm.model.matchInfo.ActualMatches;

@Repository
@Transactional
public interface ActualMatchesDao extends CrudRepository<ActualMatches, Long>{
	public List<ActualMatches> findAll();
	
	public void deleteByMatchId(long matchId);
}
