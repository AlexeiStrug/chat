package com.example.registration.service;

import com.example.registration.model.PasswordResetToken;
import com.example.registration.model.User;
import com.example.registration.model.VerificationToken;
import com.example.registration.model.dto.UserDto;
import com.example.registration.validation.exception.EmailExistsException;

/**
 * Created by Alex on 20.08.2017.
 */
public interface IUserService {

    User getCurrentUser();

    User registerNewUserAccount(UserDto accountDto) throws EmailExistsException;

    User registerNewUserAccountForAdmin(UserDto accountDto) throws EmailExistsException;

    User getUser(String verificationToken);

    void saveRegisteredUser(User user);

    void createVerificationToken(User user, String token);

    VerificationToken getVerificationToken(String VerificationToken);

    VerificationToken generateNewVerificationToken(String existingVerificationToken);

    void createPasswordResetTokenForUser(User user, String token);

    User findUserByEmail(String email);

    PasswordResetToken getPasswordResetToken(String token);

    User getUserByPasswordResetToken(String token);

    User getUserByID(Integer id);

    void changeUserPassword(User user, String password);

    boolean checkIfValidOldPassword(User user, String password);

    String validateVerificationToken(String token);
}
