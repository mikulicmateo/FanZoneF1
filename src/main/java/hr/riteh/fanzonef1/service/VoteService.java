package hr.riteh.fanzonef1.service;

import hr.riteh.fanzonef1.dto.request.VoteDto;
import hr.riteh.fanzonef1.dto.response.ResponseMessageDto;
import hr.riteh.fanzonef1.entity.User;
import hr.riteh.fanzonef1.entity.Vote;
import hr.riteh.fanzonef1.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VoteService {

    private final UserService userService;

    private final VoteRepository  voteRepository;

    @Autowired
    public VoteService(UserService userService, VoteRepository voteRepository) {
        this.userService = userService;
        this.voteRepository = voteRepository;
    }

    public ResponseMessageDto vote(String username, int race, int season, VoteDto voteDto){
        Optional<User> userOptional = userService.getUserByUsername(username); //TODO trebalo bi odradit login
        if(userOptional.isEmpty()){
            return new ResponseMessageDto(false, "ERROR");
        }
        User user = userOptional.get();
        for (Vote vote: user.getVotes()) {
            if(vote.getRound()==race && vote.getSeason()==season){
                return new ResponseMessageDto(false, "Already voted.");
            }
        }
        if(checkVote(voteDto)) return new ResponseMessageDto(false, "Vote format not correct");
        //TODO pozicije nisu jednake
        Vote vote = new Vote(voteDto.getFirst(), voteDto.getSecond(), voteDto.getThird(), season, race, user);
        voteRepository.save(vote);
        return new ResponseMessageDto(true, "Voted successfully.");
    }

    private boolean checkVote(VoteDto voteDto) {
        if(voteDto == null) return false;
        if(voteDto.getFirst() == 0 || voteDto.getSecond() == 0 || voteDto.getThird() == 0){
            return false;
        }
        return true;
    }

    public void deleteVoteFromUser(User user) {
        voteRepository.deleteByUser(user);
    }
    //TODO -> kako provjeriti i dodijeliti bodove za vote?

    public List<Vote> getVotesByRaceAndSeason(int race, int season){
        return voteRepository.findByRoundAndSeason(race, season);
    }
}
