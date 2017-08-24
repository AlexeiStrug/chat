package com.example.registration.controller;

import com.example.registration.config.OnRegistrationCompleteEvent;
import com.example.registration.model.User;
import com.example.registration.model.VerificationToken;
import com.example.registration.model.dto.PasswordDto;
import com.example.registration.model.dto.UserDto;
import com.example.registration.service.ISecurityUserService;
import com.example.registration.service.IUserService;
import com.example.registration.validation.exception.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

import static com.example.registration.service.UserService.TOKEN_EXPIRED;
import static com.example.registration.service.UserService.TOKEN_VALID;

/**
 * Created by Alex on 20.08.2017.
 */
@RestController
public class Registration {

    @Autowired
    private IUserService service;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ISecurityUserService securityUserService;

    //working for freelance
    @PostMapping(value = "/user/registration")
    @ResponseBody
    public ResponseEntity registerUserAccount(@RequestBody UserDto accountDto, HttpServletRequest request) {

        User registered;
        try {
            registered = service.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        if (registered == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, getAppUrl(request)));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //working for admin
    @PostMapping(value = "/admin/registration")
    @ResponseBody
    public ResponseEntity registerUserAccountForAdmin(@RequestBody UserDto accountDto, HttpServletRequest request) {

        User registered;
        try {
            registered = service.registerNewUserAccountForAdmin(accountDto);
        } catch (EmailExistsException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        if (registered == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, getAppUrl(request)));
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //working
    @GetMapping(value = "/regitrationConfirm")
    public ResponseEntity confirmRegistration(@RequestParam("token") String token) {

        String result = service.validateVerificationToken(token);
        if (result.equals(TOKEN_VALID)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        if (result.equals(TOKEN_EXPIRED)) {
            return new ResponseEntity(HttpStatus.GONE);
        } else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    //working(not using)
    @GetMapping(value = "/user/resendRegistrationToken")
    @ResponseBody
    public ResponseEntity resendRegistrationToken(HttpServletRequest request, @RequestParam("token") String existingToken) {

        VerificationToken newToken = service.generateNewVerificationToken(existingToken);

        User user = service.getUser(newToken.getToken());
//        MimeMessage email = null;
//        try {
//            email = constructResendVerificationTokenEmail(getAppUrl(request), newToken, user);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

//        mailSender.send(email);

        return new ResponseEntity(HttpStatus.OK);
    }

//    //working
//    private MimeMessage constructResendVerificationTokenEmail(String contextPath, VerificationToken newToken, User user) throws MessagingException {
//        String confirmationUrl = contextPath + "/regitrationConfirm?token=" + newToken.getToken();
//        String html = "Resend register token: \n<a href='" + confirmationUrl + "'>confirmation</a>";
//
//        MimeMessage message = mailSender.createMimeMessage();
//
//        message.setSubject("Resend Registration Token");
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        helper.setTo(user.getEmail());
//        helper.setText(html, true);
//        return message;
//    }

    @PostMapping(value = "/user/resetPassword")
    @ResponseBody
    public ResponseEntity resetPassword(HttpServletRequest request, @RequestBody UserDto userDto) throws MessagingException {

        User user = service.findUserByEmail(userDto.getEmail());
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        String token = UUID.randomUUID().toString();
        service.createPasswordResetTokenForUser(user, token);

        mailSender.send(constructResetTokenEmail(getAppUrl(request), token, user));

        return new ResponseEntity(HttpStatus.OK);
    }

    //      working
    private MimeMessage constructResetTokenEmail(String contextPath, String token, User user) throws MessagingException {
        String url = contextPath + "/user/changePassword?id=" + user.getId() + "&token=" + token;
        String message = "Reset Password";

        return constructEmail("Reset Password", message + " \n<a href='" + url + "'>reset password</a>", user);
    }

    private MimeMessage constructEmail(String subject, String body, User user) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setSubject(subject);
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(user.getEmail());
        helper.setText(body, true);
        return message;

    }

    //если все ок вызываем(редиректим) метод savePassword
    @GetMapping(value = "/user/changePassword")
    @ResponseBody
    public ResponseEntity showChangePasswordPage(@RequestParam("id") long id, @RequestParam("token") String token) {
        Map<String, OAuth2AccessToken> result = securityUserService.validatePasswordResetToken(id, token);
        if (result.containsKey(TOKEN_VALID)) {
            return new ResponseEntity<>(result.get(TOKEN_VALID).getValue(), HttpStatus.OK);
        }
        if (result.containsKey(TOKEN_EXPIRED)) {
            return new ResponseEntity(HttpStatus.GONE);
        } else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    //если пароль совпадают вернуть только 1 пароль? только при авторизированном юзере
    @PutMapping(value = "/user/savePassword")
    @PreAuthorize("hasAuthority('CHANGE_PASSWORD_PRIVILEGE')")
    public ResponseEntity savePassword(@RequestBody PasswordDto passwordDto, HttpServletRequest request) {
        User user = service.getCurrentUser();
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        service.changeUserPassword(user, passwordDto.getNewPassword());
        deleteTokens(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Autowired
    private TokenStore tokenStore;

    public void deleteTokens(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
            tokenStore.removeAccessToken(accessToken);
            tokenStore.removeRefreshToken(refreshToken);
        }
    }

    @RequestMapping(value = "/user/updatePassword", method = RequestMethod.POST)
//    метод который будет работать после авторизации
//    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    @ResponseBody
    public ResponseEntity changeUserPassword(@RequestBody PasswordDto passwordDto) {
        User user = service.getCurrentUser();
        if (user == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
//        User user = service.findUserByEmail(user.getEmail());

        if (!service.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        service.changeUserPassword(user, passwordDto.getNewPassword());
        return new ResponseEntity(HttpStatus.OK);
    }

    //working

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
