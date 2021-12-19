package hr.riteh.fanzonef1.controller;

import hr.riteh.fanzonef1.dto.response.UserStandingsResponseDto;
import hr.riteh.fanzonef1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
