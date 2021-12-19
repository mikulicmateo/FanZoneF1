package hr.riteh.fanzonef1.service;

import hr.riteh.fanzonef1.dto.request.CreateUserDto;
import hr.riteh.fanzonef1.dto.response.ResponseMessageDto;
import hr.riteh.fanzonef1.dto.response.UserStandingsResponseDto;
import hr.riteh.fanzonef1.entity.User;
import hr.riteh.fanzonef1.repository.UserRepository;
import hr.riteh.fanzonef1.util.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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

    public ResponseMessageDto createUser(CreateUserDto userDto){
        if(userRepository.findByUsername(userDto.getUsername()).isPresent()){
            return new ResponseMessageDto(false, "Username already exists!");
        }
        User user = new User(userDto.getUsername(),userDto.getEmail(),userDto.getPassword(),userDto.getDateOfBirth());
        User savedUser = userRepository.save(user);
        if(savedUser != null){
            return new ResponseMessageDto(true, "User created.");
        }else{
            return new ResponseMessageDto(false, "Failed saving user to database");
        }
    }
}
