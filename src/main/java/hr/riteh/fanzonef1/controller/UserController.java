package hr.riteh.fanzonef1.controller;

import hr.riteh.fanzonef1.dto.request.CreateUserDto;
import hr.riteh.fanzonef1.dto.response.ResponseMessageDto;
import hr.riteh.fanzonef1.dto.response.UserStandingsResponseDto;
import hr.riteh.fanzonef1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping(path = "user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/standings")
    public ResponseEntity<List<UserStandingsResponseDto>> getStandings(){
        List<UserStandingsResponseDto> standingsList = userService.getUserStandings();
        return new ResponseEntity<>(standingsList, HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<ResponseMessageDto> createUser(@RequestBody CreateUserDto userDto){
        ResponseMessageDto message = userService.createUser(userDto);
        if(message.isSuccess()){
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
