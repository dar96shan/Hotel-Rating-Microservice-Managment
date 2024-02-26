package com.micro.UserService.controller;

import com.micro.UserService.entity.User;
import com.micro.UserService.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


   private final Logger logger = LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        User createUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUser);
    }

    //single user get
    //int retryCount= 1;
    @GetMapping("/{userId}")
  //  @CircuitBreaker(name="ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
  //  @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name="userRateLimiter" , fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
       // logger.info("Retry count : {} ", retryCount);
        //retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //creating fallBack method for circuitbreaker

    public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex){
       // logger.info("Fallback is executed because service is down :", ex.getMessage());

        ex.printStackTrace();
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created dummy beacuse some service id down")
                .userId("123")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //all user
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAlluser(){
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
