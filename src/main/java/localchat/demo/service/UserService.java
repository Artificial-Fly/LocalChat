package localchat.demo.service;

import localchat.demo.entity.User;
import localchat.demo.exception.UserNotFoundException;
import localchat.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private static Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    //Generate nickname
    private String generateNickname(){
        log.debug("Generating nickname");
        String[] adjectives = {"Funny","Evil","Weird","Gloomy","Smart","Bright","Fast","Mysterious","Tall","Short"};
        String[] nouns = {"Bunny","Wolf","Racoon","Fox","Owl","Gazelle","Crow","Mice","Cat","Dog"};
        String randomAdj = adjectives[(int)(Math.random()*adjectives.length)];
        String randomNoun = nouns[(int)(Math.random()*nouns.length)];
        String randomNickname = randomAdj+((int)(Math.random()*999))+randomNoun;
        log.info("Random nickaname {} generated", randomNickname);
        return randomNickname;
    }
    //create user by mac adress
    public User CreateUserByUuid(String uuid){
        log.debug("Creating new user or getting already existing");
        //check if user already exists
        Optional<User> existingUser = userRepository.findByUuid(uuid);
        if(existingUser.isPresent()){
            log.debug("User {} ({}) exists",existingUser.get().getNickname(), existingUser.get().getId());
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
        User savedUser = userRepository.save(user);
        log.info("User ({}, {}, {}) is saved", savedUser.getId(), savedUser.getUuid(), savedUser.getNickname());
        return savedUser;
    }
    //get user by uuid
    public User getUserByUuid(String uuid){
        log.debug("Getting user by uuid");
        User getUser = userRepository.findByUuid(uuid).orElseThrow(()-> new UserNotFoundException("User not found by uuid"));
        log.info("Got user {} ({}) by uuid {}", getUser.getId(), getUser.getNickname(), getUser.getUuid());
        return getUser;
    }
    //get user by nickname
    public User getUserByNickname(String nickname){
        log.debug("Getting user by nickname");
        User getUser = userRepository.findByNickname(nickname).orElseThrow(()-> new UserNotFoundException("User not found by nickname"));
        log.info("Got user {} ({}) by nickname {}", getUser.getId(), getUser.getUuid(), getUser.getNickname());
        return getUser;
    }

    public String getNicknameByUuid(String uuid){
        log.debug("Getting Nickname by uuid");
        String nickname = userRepository.findNicknameByUuid(uuid).orElse(null);
        log.info("Got nickname {} by uuid {}", nickname, uuid);
        return nickname;
    }

}
