package com.project.server.rest;

import com.project.server.business.UserService;
import com.project.server.business.security.AccessTokenUtil;
import com.project.server.business.security.RefreshTokenUtil;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
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
    private AccessTokenUtil accessTokenUtil;

    @Autowired
    private RefreshTokenUtil refreshTokenUtil;

    private final HttpServletRequest request;

    @Autowired
    public AuthenticationController(HttpServletRequest request) {
        this.request = request;
    }

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
                jwtAuthenticationRequest.getEmail(),
                jwtAuthenticationRequest.getPassword()
        );

        final Authentication authentication = authenticationManager.authenticate(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final String accessToken = accessTokenUtil.generateToken(user.getName());
        final String refreshToken = refreshTokenUtil.generateToken(user.getName());

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setAccessToken(accessToken);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        logger.info("User {} has logged in", user.getName());
        return ResponseEntity.ok(jwtAuthenticationResponse);

    }

    @Override
    public ResponseEntity<User> register(@Valid User user) {
        userService.saveUser(user);
        logger.info("User {} has registered", user.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
