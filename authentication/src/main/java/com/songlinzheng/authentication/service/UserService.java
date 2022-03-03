package com.songlinzheng.authentication.service;

import com.songlinzheng.authentication.DTO.JWTRefreshRequest;
import com.songlinzheng.authentication.DTO.JWTRequest;
import com.songlinzheng.authentication.DTO.JWTResponse;
import com.songlinzheng.authentication.config.security.JWTUserDetailsService;
import com.songlinzheng.authentication.entity.User;
import com.songlinzheng.authentication.util.JWT.JWTUtils;
import com.songlinzheng.authentication.util.Jedis.JedisUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RestController
@Slf4j
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private JWTUserDetailsService userDetailsService;

    @Autowired
    private JedisUtils jedisUtils;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        log.info("enter signup");
        try {
            if (userDetailsService.hasUser(user)) {
                return ResponseEntity.badRequest().body("User already existing.");
            }
            return ResponseEntity.ok(userDetailsService.save(user));
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Failed to sign up. Try again later.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JWTRequest authenticationRequest)  {
        log.info("enter login");
        try {
            authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
            final String token = jwtUtils.generateValidationToken(userDetails);
            final String recoveryToken = jwtUtils.generateRecoveryToken(userDetails);
            jedisUtils.addRefreshToken(recoveryToken);
            log.error("generated token is : " + token);
            log.error("generated recovery token is : " + recoveryToken);
            return ResponseEntity.ok(new JWTResponse(token, recoveryToken));
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Failed to log in. Try again later");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody JWTRefreshRequest jwtRefreshRequest) {
        log.info("enter refresh");
        try {
            String recoveryToken = jwtRefreshRequest.getRecoveryToken();
            if (jedisUtils.containsRefreshToken(recoveryToken)) {
                if (jwtUtils.isTokenExpired(recoveryToken)) {
                    jedisUtils.removeRefreshToken(recoveryToken);
                    recoveryToken = jwtUtils.generateRecoveryToken(recoveryToken);
                    jedisUtils.addRefreshToken(recoveryToken);
                }
                String validationToken = jwtUtils.generateValidationToken(jwtRefreshRequest.getRecoveryToken());
                return ResponseEntity.ok(new JWTResponse(validationToken, recoveryToken));
            } else {
                return ResponseEntity.badRequest().body("Not a valid user. Please log in.");
            }
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Failed to refresh. Please log in.");
        }
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestBody JWTRefreshRequest jwtRefreshRequest) {
        log.info("enter logout");
        try {
            jedisUtils.removeRefreshToken(jwtRefreshRequest.getRecoveryToken());
            return ResponseEntity.ok("Successfully log out.");
        } catch (Exception e) {
            log.error(e.toString());
            return ResponseEntity.internalServerError().body("Failed to log out.");
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
