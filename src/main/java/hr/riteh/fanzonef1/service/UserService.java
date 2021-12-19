package hr.riteh.fanzonef1.service;

import hr.riteh.fanzonef1.dto.response.UserStandingsResponseDto;
import hr.riteh.fanzonef1.entity.User;
import hr.riteh.fanzonef1.repository.UserRepository;
import hr.riteh.fanzonef1.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserStandingsResponseDto> getUserStandings(){
        List<User> users = userRepository.findAllByOrderByPointsDesc();
        List<UserStandingsResponseDto> standingsDtoList = new ArrayList<>();
        for(User user : users){
            standingsDtoList.add(DtoMapper.mapUserToUserStandingsResponseDto(user));
        }
        return standingsDtoList;
    }
}
