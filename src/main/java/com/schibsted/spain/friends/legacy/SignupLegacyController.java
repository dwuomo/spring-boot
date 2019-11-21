package com.schibsted.spain.friends.legacy;

import com.schibsted.spain.friends.src.application.usecase.signup.SignUpRequest;
import com.schibsted.spain.friends.src.application.usecase.signup.SignUpUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
public class SignupLegacyController {

    @Autowired
    private SignUpUseCase signUpUseCase;

    @PostMapping
    void signUp(
            @RequestParam("username") String username,
            @RequestHeader("X-Password") String password
    ) throws Exception {
        SignUpRequest request = new SignUpRequest(username, password);
        this.signUpUseCase.handle(request);
    }
}
