//package hr.riteh.fanzonef1.service;
//
//import hr.riteh.fanzonef1.entity.User;
//import hr.riteh.fanzonef1.repository.UserRepository;
//import hr.riteh.fanzonef1.security.UserPrincipal;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserPrincipalService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user = userRepository.findByUsername(username);
//        if(user.isPresent()) return new UserPrincipal(user.get());
//
//        throw new UsernameNotFoundException(username);
//    }
//}
