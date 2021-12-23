package hr.riteh.fanzonef1.repository;

import hr.riteh.fanzonef1.entity.User;
import hr.riteh.fanzonef1.entity.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    Vote save(Vote vote);
    Optional<Vote> findById(long id);
    List<Vote> findByUser(User user);
}
