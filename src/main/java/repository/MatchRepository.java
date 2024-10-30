package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {
	
	List<Match> findAll();
	
	@Query("SELECT DISTINCT m FROM Match m WHERE m.sport LIKE %:nameSport% ORDER BY m.date")
	List<Match> findByNameOfSport(@Param("nameSport") String nameSport);
	
	@Query("SELECT DISTINCT m FROM Match m WHERE m.olympicNr1 LIKE :olympicNr1")
	List<Match> findByNameOlympicNr(@Param("olympicNr") String olympicNr);
	
	
}
