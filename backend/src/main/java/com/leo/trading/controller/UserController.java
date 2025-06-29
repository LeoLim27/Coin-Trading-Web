package com.leo.trading.controller;

import com.leo.trading.request.ForgetPasswordTokenRequest;
import com.leo.trading.domain.VerificationType;
import com.leo.trading.modal.ForgetPasswordToken;
import com.leo.trading.modal.User;
import com.leo.trading.modal.VerificationCode;
import com.leo.trading.request.ResetPasswordRequest;
import com.leo.trading.response.ApiResponse;
import com.leo.trading.response.AuthResponse;
import com.leo.trading.service.EmailService;
import com.leo.trading.service.ForgetPasswordService;
import com.leo.trading.service.UserService;
import com.leo.trading.service.VerificationCodeService;
import com.leo.trading.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ForgetPasswordService forgetPasswordService;
    private String jwt;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserProfileByJwt(jwt);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * About verifying user (two-authentication)
     * check user's verification code and send appropriate otp to user
     * Then, get user info by jwt and find the verification code in repository by userId
     * and verify otp by comparing it with otp in path variable.
     * once verified, update user info with enable two-auth,
     * and delete verification code in verificationCodeRepository
     * if not verified, throw opt not match error.
     */

    // check verification code of user found by jwt, and if not exist,
    // create new code and send it to email by emailService
    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOtp(
            @RequestHeader("Authorization") String jwt,
            @PathVariable VerificationType verificationType) throws Exception{

        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService
                .getVerificationCodeByUserId(user.getId());

        if (verificationCode==null) {
            verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
        }
        if (verificationType.equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }

        return new ResponseEntity<>("verification otp sent successfully", HttpStatus.OK);
    }

    // an end point for verifying two-factor auth otp
    @PatchMapping ("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication (
            @RequestHeader("Authorization") String jwt,
            @PathVariable String otp) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByUserId(user.getId());

        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL) ?
                verificationCode.getEmail() : verificationCode.getMobile();

        boolean isVerified = verificationCode.getOtp().equals(otp);

        if (isVerified) {
            User updatedUser = userService.enableTwoFactorAuthentication(
                    verificationCode.getVerificationType(), sendTo, user);
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        throw new Exception("wrong otp");
    }

    /**
     * About resetting password for user who forgot password.
     */

    // sending otp (token) for resetting password; since there is no login, no jwt info form user
    // find user by email info in request body
    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgetPasswordOtp (@RequestBody ForgetPasswordTokenRequest request) throws Exception {

        User user = userService.findUserProfileByEmail(request.getSendTo());
        String otp = OtpUtils.generateOtp();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgetPasswordToken forgetPasswordToken = forgetPasswordService.findByUserId(user.getId());

        // create new token for user if user doesn't have token
        if (forgetPasswordToken==null) {
            forgetPasswordToken = forgetPasswordService.createToken(user, id, otp, request.getVerificationType(),
                    request.getSendTo());
        }

        // send otp via email if verification type is email
        if (request.getVerificationType().equals(VerificationType.EMAIL)) {
            emailService.sendVerificationOtpEmail(user.getEmail(), forgetPasswordToken.getOtp());
        }
        AuthResponse response = new AuthResponse();
        response.setSession(forgetPasswordToken.getId());
        response.setMessage("Password reset otp sent successfully");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id, @RequestBody ResetPasswordRequest req) throws Exception {
        ForgetPasswordToken forgetPasswordToken = forgetPasswordService.findById(id);
        boolean isVerified = forgetPasswordToken.getOtp().equals(req.getOtp());
        if (isVerified) {
            userService.updatePassword(forgetPasswordToken.getUser(), req.getPassword());
            ApiResponse res = new ApiResponse();
            res.setMessage("password reset successfully");
            return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
        }
        throw new Exception("wrong otp");
    }
}
