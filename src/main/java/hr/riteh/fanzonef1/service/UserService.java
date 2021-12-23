package hr.riteh.fanzonef1.service;

import hr.riteh.fanzonef1.dto.request.CreateUserDto;
import hr.riteh.fanzonef1.dto.request.DeleteUserDto;
import hr.riteh.fanzonef1.dto.request.UpdateUserDto;
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
import java.util.Optional;

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

    public ResponseMessageDto updateUser(UpdateUserDto userDto){
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if(userOptional.isEmpty()){
            return new ResponseMessageDto(false, "User doesn't exist!");
        }

        User user = userOptional.get();
        if(!userDto.getNewPassword().equals(userDto.getConfirmNewPassword())){
            return new ResponseMessageDto(false, "Passwords don't match!");
        }
        if(!userDto.getEmail().equals(user.getEmail())){
            if(userRepository.findByEmail(userDto.getEmail()).isPresent()){
                return new ResponseMessageDto(false, "Email is already registered!");
            }
            user.setEmail(userDto.getEmail());
        }
        user.setPassword(userDto.getNewPassword());

        if(userRepository.update(user) != null){
            return new ResponseMessageDto(true, "User has been updated!");
        }else{
            return new ResponseMessageDto(false, "Couldn't update user in database!");
        }
    }

    public ResponseMessageDto deleteUser(DeleteUserDto userDto) {
        Optional<User> userOptional = userRepository.findByUsername(userDto.getUsername());
        if(userOptional.isEmpty()){
            return new ResponseMessageDto(false, "User doesn't exist!");
        }
        User user = userOptional.get();
        if(!userDto.getEmail().equals(user.getEmail())){
            return new ResponseMessageDto(false, "Wrong email address!");
        }
        if(!userDto.getPassword().equals(user.getPassword())){
            return new ResponseMessageDto(false,"Wrong password!");
        }

        userRepository.delete(user);
        return new ResponseMessageDto(true, "User has been deleted!");
    }
}
