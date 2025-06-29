package com.leo.trading.controller;

import com.leo.trading.config.JwtProvider;
import com.leo.trading.modal.TwoFactorOTP;
import com.leo.trading.modal.User;
import com.leo.trading.repository.UserRepository;
import com.leo.trading.response.AuthResponse;
import com.leo.trading.service.CustomUserDetailsService;
import com.leo.trading.service.EmailService;
import com.leo.trading.service.TwoFactorOtpService;
import com.leo.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailExist = userRepository.findByEmail(user.getEmail()); // EMail???? or user name ?????

        if (isEmailExist != null) {
            throw new Exception("email is already used with another account");
        }

        User newUser = new User();
        newUser.setFullName(user.getFullName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        User savedUser = userRepository.save(newUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwt = JwtProvider.generateToken(auth);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("register success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

        String userName = user.getEmail();
        String password = user.getPassword();
        User authUser = userRepository.findByEmail(user.getEmail());

        // get authentication info by comparing username and password in authenticate method
        Authentication auth = authenticate(userName, password);
        // store authentication object containing token into security context holder
        SecurityContextHolder.getContext().setAuthentication(auth);
        // based on above info, create jwt token
        String jwt = JwtProvider.generateToken(auth);

        /**
         * Create new authResponse if two-factor auth is enabled.
         * Then, generate random 6 digit otp
         * Check the old otp info and if it exists, delete the old otp
         * with new 6 digit otp, create a two factor otp object with current user, new otp, and jwt.
         * Then, set authResponse session with new two factor otp id.
         * and return this auth response
         */
        if (user.getTwoFactorAuth().isEnabled()) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Two factor auth is enabled");
            authResponse.setIsTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOtp();

            TwoFactorOTP oldTwoFactorOtp = twoFactorOtpService.findByUser(authUser.getId());
            if (oldTwoFactorOtp != null) {
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);
            }
            TwoFactorOTP newTwoFactorOtp = twoFactorOtpService.createTwoFactorOtp(authUser, otp, jwt);

            // send email to user
            emailService.sendVerificationOtpEmail(userName,otp);

            authResponse.setSession(newTwoFactorOtp.getId());

            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("login success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!userDetails.getPassword().equals(password)) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    // @RequestParam String id == TwoFactorOTP object's id (primary key)
    @PostMapping("two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySigningOtp (@PathVariable String otp, @RequestParam String id) throws Exception {
        TwoFactorOTP twoFactorOtp = twoFactorOtpService.findById(id);

        if (twoFactorOtpService.verifyTwoFactorOtp(twoFactorOtp, otp)) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Two factor authentication is verified");
            authResponse.setIsTwoFactorAuthEnabled(true);
            authResponse.setJwt(twoFactorOtp.getJwt());
            return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
        }
        throw new Exception("invalid otp");
    }
}
