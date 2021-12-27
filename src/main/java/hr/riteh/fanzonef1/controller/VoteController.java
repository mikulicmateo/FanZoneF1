package hr.riteh.fanzonef1.controller;

import hr.riteh.fanzonef1.dto.request.VoteDto;
import hr.riteh.fanzonef1.dto.response.ResponseMessageDto;
import hr.riteh.fanzonef1.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "vote")
@CrossOrigin
public class VoteController {

    private final VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping(path = "{season}/{race}")
    public ResponseEntity<ResponseMessageDto> vote(@RequestHeader("username") String username, @PathVariable int race,
                                                   @PathVariable int season, @RequestBody VoteDto voteDto){
        ResponseMessageDto message = voteService.vote(username, race, season, voteDto);
        if(message.isSuccess()){
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

}
