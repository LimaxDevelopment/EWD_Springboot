package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import domain.Ticket;

public interface TicketRepository extends CrudRepository<Ticket, Long> {
	
	@Query("SELECT t FROM Ticket t WHERE t.totalTicketsMatch > 0 ORDER BY t.sport, t.date")
	List<Ticket> findTickets();

	@Query("SELECT t FROM Ticket t WHERE t.userId = :userId AND t.matchId = :matchId")
	Ticket findByIds(@Param("userId") Long userId, @Param("matchId") Long matchId);
}
