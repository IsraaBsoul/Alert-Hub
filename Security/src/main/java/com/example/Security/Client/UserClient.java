package com.example.Security.Client;
import com.example.Security.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user", url = "http://localhost:8088/api/user")
public interface UserClient {
    //Get user by ID
    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable("id") int id);

    //Get user by email (used for sign-in)
    @GetMapping("/by-email")
    UserDto getUserByEmail(@RequestParam("email") String email);

    //Check if email exists (used for sign-up)
    @GetMapping("/exists")
    boolean checkEmailExists(@RequestParam("email") String email);

    //Create new user (used for sign-up)
    @PostMapping
    UserDto createUser(@RequestBody UserDto userDto);
}
    

