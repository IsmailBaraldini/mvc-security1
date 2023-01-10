package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController
{
    @Autowired
    private UserManager userManager;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody RequestDTO registrationRequest) {
        User user = new User();
        user.setPassword(registrationRequest.getPassword());
        user.setLogin(registrationRequest.getLogin());
        userManager.save(user);
        return "OK";
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody RequestDTO request) {
        User user = userManager.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin());
        return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
    }

    @GetMapping("/admin/test")
    public ResponseEntity<String> adminGet() {
        return new ResponseEntity<>("Hello from Admin", HttpStatus.OK);
    }

    @GetMapping("/user/test")
    public ResponseEntity<String> userGet() {
        return new ResponseEntity<>("Hello from User", HttpStatus.OK);
    }
}
