package hr.riteh.fanzonef1.service;

import hr.riteh.fanzonef1.dto.request.CreateUserDto;
import hr.riteh.fanzonef1.dto.request.CredentialsDto;
import hr.riteh.fanzonef1.dto.request.DeleteUserDto;
import hr.riteh.fanzonef1.dto.request.UpdateUserDto;
import hr.riteh.fanzonef1.dto.response.ResponseMessageDto;
import hr.riteh.fanzonef1.dto.response.UserStandingsResponseDto;
import hr.riteh.fanzonef1.entity.User;
import hr.riteh.fanzonef1.repository.UserRepository;
import hr.riteh.fanzonef1.security.UserPrincipal;
import hr.riteh.fanzonef1.util.Base64Helper;
import hr.riteh.fanzonef1.util.DtoMapper;
import hr.riteh.fanzonef1.util.ValidationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
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
        if(!ValidationHelper.checkDate(userDto.getDateOfBirth())){
            return new ResponseMessageDto(false, "User must be 18 years old.");
        }
        if(getUserByUsername(userDto.getUsername()).isPresent()){
            return new ResponseMessageDto(false, "Username already exists!");
        }
        if(getUserByEmail(userDto.getEmail()).isPresent()){
            return new ResponseMessageDto(false, "E-mail already registered!");
        }
        User user = new User(userDto.getUsername(),userDto.getEmail(),passwordEncoder.encode(userDto.getPassword()),userDto.getDateOfBirth());
        User savedUser = userRepository.save(user);
        if(savedUser != null){
            return new ResponseMessageDto(true, "User created.");
        }else{
            return new ResponseMessageDto(false, "Failed saving user to database");
        }
    }

    private Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public ResponseMessageDto updateUser(UpdateUserDto userDto, String authHeader){
        Optional<User> userOptional = getUserByUsername(Base64Helper.getDecodedUsernameAndPassword(authHeader).getUsername());
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
        user.setPassword(passwordEncoder.encode(userDto.getNewPassword()));

        if(userRepository.save(user) != null){
            return new ResponseMessageDto(true, "User has been updated!");
        }else{
            return new ResponseMessageDto(false, "Couldn't update user in database!");
        }
    }

    public ResponseMessageDto deleteUser(DeleteUserDto userDto, String authHeader) {
        CredentialsDto credentialsDto = Base64Helper.getDecodedUsernameAndPassword(authHeader);
        Optional<User> userOptional = getUserByUsername(credentialsDto.getUsername());
        if(userOptional.isEmpty()){
            return new ResponseMessageDto(false, "User doesn't exist!");
        }
        User user = userOptional.get();
        if(!userDto.getEmail().equals(user.getEmail())){
            return new ResponseMessageDto(false, "Wrong email address!");
        }

        userRepository.delete(user);
        return new ResponseMessageDto(true, "User has been deleted!");
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()) return new UserPrincipal(user.get());

        throw new UsernameNotFoundException(username);
    }
}
