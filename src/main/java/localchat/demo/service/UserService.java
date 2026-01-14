package localchat.demo.service;

import localchat.demo.entity.User;
import localchat.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Generate nickname
    private String generateNickname(){
        String[] adjectives = {"Funny","Evil","Weird","Gloomy","Smart","Bright","Fast","Mysterious","Tall","Short"};
        String[] nouns = {"Bunny","Wolf","Racoon","Fox","Owl","Gazelle","Crow","Mice","Cat","Dog"};
        String randomAdj = adjectives[(int)(Math.random()*adjectives.length)];
        String randomNoun = nouns[(int)(Math.random()*nouns.length)];
        return randomAdj+((int)(Math.random()*999))+randomNoun;
    }
    //create user by mac adress
    public User CreateUserByUuid(String uuid){
        //check if user already exists
        Optional<User> existingUser = userRepository.findByUuid(uuid);
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
        user.setUuid(uuid);
        user.setCreatedAt(LocalDateTime.now());
        //save user
        return userRepository.save(user);
    }
    //get user by uuid
    public User getUserByUuid(String uuid){
        return userRepository.findByUuid(uuid).orElse(null);
    }
    //get user by nickname
    public User getUserByNickname(String nickname){
        return userRepository.findByNickname(nickname).orElse(null);
    }

    public String getNicknameByUuid(String uuid){ return userRepository.findNicknameByUuid(uuid).orElse(null);}

}
