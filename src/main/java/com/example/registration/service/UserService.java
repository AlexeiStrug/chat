package com.example.registration.service;

import com.example.registration.model.PasswordResetToken;
import com.example.registration.model.User;
import com.example.registration.model.VerificationToken;
import com.example.registration.model.dto.UserDto;
import com.example.registration.repository.PasswordResetTokenRepository;
import com.example.registration.repository.UserRepository;
import com.example.registration.repository.VerificationTokenRepository;
import com.example.registration.utils.SecurityUtils;
import com.example.registration.validation.exception.EmailExistsException;
import com.example.registration.validation.exception.NoUserWithSuchUsernameCustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Alex on 20.08.2017.
 */
@Service
public class UserService implements IUserService {

    public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";

    @Autowired
    private UserRepository repository;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User getCurrentUser() {
        User user = repository.findByLogin(securityUtils.getCurrentUserLogin())
                .orElseThrow(NoUserWithSuchUsernameCustomException::new);
        return user;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(UserDto accountDto) throws EmailExistsException {

        if (emailExist(accountDto.getEmail()) || loginExist(accountDto.getLogin())) {
            throw new EmailExistsException("There is an account with that email address:  + accountDto.getEmail()");
        }
        User user = new User();
        user.setLogin(accountDto.getLogin());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setMatchingPassword(passwordEncoder.encode(accountDto.getMatchingPassword()));
        user.setEmail(accountDto.getEmail());
        user.setEnabled(false);
        user.setRole("Freelance");
        return repository.save(user);
    }

    @Transactional
    @Override
    public User registerNewUserAccountForAdmin(UserDto accountDto) throws EmailExistsException {

        if (emailExist(accountDto.getEmail()) || loginExist(accountDto.getLogin())) {
            throw new EmailExistsException("There is an account with that email address:  + accountDto.getEmail()");
        }
        User user = new User();
        user.setLogin(accountDto.getLogin());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setMatchingPassword(passwordEncoder.encode(accountDto.getMatchingPassword()));
        user.setEmail(accountDto.getEmail());
        user.setEnabled(false);
        user.setRole(accountDto.getRole());
        return repository.save(user);
    }

    private boolean emailExist(String email) {
        User user = repository.findByEmail(email);
        return (user != null ? true : false);
    }

    private boolean loginExist(String login) {
        User user = repository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("User " + login + " not found"));;
        return (user != null ? true : false);
    }

    @Override
    public User getUser(String verificationToken) {
        User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(User user) {
        repository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public User findUserByEmail(final String email) {
        return repository.findByEmail(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public User getUserByPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token).getUser();
    }

    @Override
    public User getUserByID(Integer id) {
        return repository.findOne(id);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }


    @Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }
        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        tokenRepository.delete(verificationToken);
        repository.save(user);
        return TOKEN_VALID;
    }
}
