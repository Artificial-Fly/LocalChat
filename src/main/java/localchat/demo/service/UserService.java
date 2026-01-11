package localchat.demo.service;

import localchat.demo.entity.User;
import localchat.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    //Generate nickname
    private String generateNickname(){
        String[] adjectives = {"Funny","Evil","Weird","Gloomy","Smart","Bright","Fast","Mysterious","Tall","Short"};
        String[] nouns = {"Bunny","Wolf","Racoon","Fox","Owl","Gazelle","Crow","Mice","Cat","Dog"};
        String randomAdj = adjectives[(int)(Math.random()*adjectives.length)];
        String randomNoun = nouns[(int)(Math.random()*nouns.length)];
        return randomAdj+randomNoun;
    }
    //create user by mac adress
    public User CreateUserByIp(String ipAdress){
        //check if user already exists
        Optional<User> existingUser = userRepository.findByIpAdress(ipAdress);
        if(existingUser.isPresent()){
            return existingUser.get();
        }
        //generate unique nickname
        String nickname;
        do{
            nickname = generateNickname();
        }while(userRepository.existsByNickname(nickname));
        //set user data
        User user = new User();
        user.setNickname(nickname);
        user.setIpAdress(ipAdress);
        user.setCreatedAt(LocalDateTime.now());
        //save user
        return userRepository.save(user);
    }
    //get user by mac adress
    public User getUserByIp(String ipAdress){
        return userRepository.findByIpAdress(ipAdress).orElse(null);
    }
    //get user by nickname
    public User getUserByNickname(String nickname){
        return userRepository.findByNickname(nickname).orElse(null);
    }

}
