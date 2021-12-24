package hr.riteh.fanzonef1.util;

import hr.riteh.fanzonef1.dto.request.VoteDto;
import hr.riteh.fanzonef1.entity.User;
import hr.riteh.fanzonef1.entity.Vote;
import hr.riteh.fanzonef1.service.ClientService;
import hr.riteh.fanzonef1.service.UserService;
import hr.riteh.fanzonef1.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScoreUpdateScheduler {

    private final VoteService voteService;
    private final ClientService clientService;
    private final UserService userService;

    @Autowired
    public ScoreUpdateScheduler(VoteService voteService, ClientService clientService, UserService userService) {
        this.voteService = voteService;
        this.clientService = clientService;
        this.userService = userService;
    }
    //TODO -> ako je response isti kao i zadnji put ili null ne čini ništa
    @Scheduled(fixedRate = 60000)
    private void updateUserPoints(){
        int currentRaces = SchedulerHelper.getRace();
        int currentSeason = SchedulerHelper.getSeason();
        int totalRaces = clientService.seasonRaces(currentSeason);

        List<Vote> voteList = voteService.getVotesByRaceAndSeason(currentRaces, currentSeason);
        if(voteList.isEmpty()) return;
        VoteDto winners = clientService.getRaceWinners(currentRaces, currentSeason);

        for(Vote vote: voteList){
            int pointsToAward = 0;
            if(vote.getN1() == winners.getFirst()){
                pointsToAward += 10;
            }else if(vote.getN2() == winners.getSecond()){
                pointsToAward += 8;
            }else if(vote.getN3() == winners.getThird()){
                pointsToAward +=5;
            }

            if(pointsToAward == 0) continue;
            User user = vote.getUser();
            user.setPoints(user.getPoints() + pointsToAward);
            userService.saveUser(user);
        }


        seasonCheck(currentRaces, totalRaces);
    }

    private void seasonCheck(int currentRaces, int totalRaces){
        currentRaces++;
        if(currentRaces > totalRaces){
            SchedulerHelper.incrementSeason();
            SchedulerHelper.resetRace();
        }else{
            SchedulerHelper.incrementRace();
        }
    }
}
