package hr.riteh.fanzonef1.service;

import hr.riteh.fanzonef1.dto.request.CredentialsDto;
import hr.riteh.fanzonef1.dto.request.VoteDto;
import hr.riteh.fanzonef1.dto.response.ResponseMessageDto;
import hr.riteh.fanzonef1.entity.User;
import hr.riteh.fanzonef1.entity.Vote;
import hr.riteh.fanzonef1.repository.VoteRepository;
import hr.riteh.fanzonef1.util.Base64Helper;
import hr.riteh.fanzonef1.util.ValidationHelper;
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

    public ResponseMessageDto vote(String authHeader, int race, int season, VoteDto voteDto){

        CredentialsDto credentialsDto = Base64Helper.getDecodedUsernameAndPassword(authHeader);
        if(!ValidationHelper.checkCredentialsDto(credentialsDto))
            return new ResponseMessageDto(false, "No credentials provided");

        Optional<User> userOptional = userService.getUserByUsername(credentialsDto.getUsername());
        if(userOptional.isEmpty()){
            return new ResponseMessageDto(false, "ERROR");
        }
        User user = userOptional.get();
        for (Vote vote: user.getVotes()) {
            if(vote.getRound()==race && vote.getSeason()==season){
                return new ResponseMessageDto(false, "Already voted.");
            }
        }
        if(!ValidationHelper.checkVote(voteDto)) return new ResponseMessageDto(false, "Vote format not correct");
        Vote vote = new Vote(voteDto.getFirst(), voteDto.getSecond(), voteDto.getThird(), season, race, user);
        voteRepository.save(vote);
        return new ResponseMessageDto(true, "Voted successfully.");
    }

    public List<Vote> getVotesByRaceAndSeason(int race, int season){
        return voteRepository.findByRoundAndSeason(race, season);
    }
}
