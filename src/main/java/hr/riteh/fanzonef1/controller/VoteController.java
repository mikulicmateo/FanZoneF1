package hr.riteh.fanzonef1.controller;

import hr.riteh.fanzonef1.dto.request.VoteDto;
import hr.riteh.fanzonef1.dto.response.CurrentRaceDto;
import hr.riteh.fanzonef1.dto.response.ResponseMessageDto;
import hr.riteh.fanzonef1.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "vote")
@CrossOrigin(origins="http://localhost:3000")
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping(path = "{season}/{race}")
    public ResponseEntity<ResponseMessageDto> vote(@RequestHeader(value = "Authorization") String authHeader, @PathVariable int race,
                                                   @PathVariable int season, @RequestBody VoteDto voteDto){
        //TODO napraviti da se ne može glasati više utrka unaprijed
        ResponseMessageDto message = voteService.vote(authHeader, race, season, voteDto);
        if(message.isSuccess()){
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path="/currentrace")
    public ResponseEntity<CurrentRaceDto> getCurrentRace(){
        return new ResponseEntity<>(voteService.getCurrentRace(), HttpStatus.OK );
    }

}
