package com.project.server.rest;

import com.project.server.business.UserService;
import com.project.server.business.security.JwtTokenUtil;
import com.project.server.rest.exceptions.validators.UserValidator;
import openapi.project.api.AuthenticationApi;
import openapi.project.model.JwtAuthenticationRequest;
import openapi.project.model.JwtAuthenticationResponse;
import openapi.project.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController implements AuthenticationApi {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @InitBinder("user")
    protected void initBinderForUser(WebDataBinder binder) {
        binder.addValidators(new UserValidator());
    }


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> login(@Valid JwtAuthenticationRequest jwtAuthenticationRequest) {
        UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
                jwtAuthenticationRequest.getUsername(),
                jwtAuthenticationRequest.getPassword()
        );
        try {
            final Authentication authentication = authenticationManager.authenticate(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        catch (BadCredentialsException e) {
            throw e;
        }
        // Reload password post-security so we can generate token
        final String token = jwtTokenUtil.generateToken(user.getName());

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setAccessToken(token);

        logger.info("User {} has logged in", user.getName());
        return ResponseEntity.ok(jwtAuthenticationResponse);

    }

    @Override
    public ResponseEntity<Void> register(@Valid User user) {
        userService.saveUser(user);
        logger.info("User {} has registered", user.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
