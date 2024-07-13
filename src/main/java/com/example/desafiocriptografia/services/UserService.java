package com.example.desafiocriptografia.services;

import java.util.Optional;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.desafiocriptografia.dtos.UserDto;
import com.example.desafiocriptografia.exceptions.UserNotFoundException;
import com.example.desafiocriptografia.models.UserWithDataSensitive;
import com.example.desafiocriptografia.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AES256TextEncryptor textEncryptor;

  @Transactional
  public UserDto getUserWithDataSensitive(Long id) {
    Optional<UserWithDataSensitive> userO = userRepository.findById(id);

    if (userO.isPresent()) {
      UserWithDataSensitive user = userO.get();

      String rawUserDocument = textEncryptor.decrypt(user.getUserDocument());
      String rawCreditCardToken = textEncryptor.decrypt(user.getCreditCardToken());

      return new UserDto(rawUserDocument, rawCreditCardToken, user.getAmount());
    }

    throw new UserNotFoundException("User not found!");
  }

  @Transactional
  public UserDto createUserWithDataSensitive(UserDto userDto) {
    UserWithDataSensitive newUser = new UserWithDataSensitive();

    String userDocumentEncrypted = textEncryptor.encrypt(userDto.userDocument());
    String creditCardTokenEncrypted = textEncryptor.encrypt(userDto.creditCardToken());

    newUser.setUserDocument(userDocumentEncrypted);
    newUser.setCreditCardToken(creditCardTokenEncrypted);
    newUser.setAmount(userDto.value());

    userRepository.save(newUser);
    return new UserDto(newUser.getUserDocument(), newUser.getCreditCardToken(), newUser.getAmount());
  }

  @Transactional
  public UserDto updateUserWithDataSensitive(Long idUser, UserDto userDto) {
    Optional<UserWithDataSensitive> userO = userRepository.findById(idUser);

    if (userO.isPresent()) {
      UserWithDataSensitive user = userO.get();

      String userDocumentEncrypted = textEncryptor.encrypt(userDto.userDocument());
      String creditCardTokenEncrypted = textEncryptor.encrypt(userDto.creditCardToken());

      user.setUserDocument(userDocumentEncrypted);
      user.setCreditCardToken(creditCardTokenEncrypted);
      user.setAmount(userDto.value());

      userRepository.save(user);
      return new UserDto(user.getUserDocument(), user.getCreditCardToken(), user.getAmount());
    }

    throw new UserNotFoundException("User not found!");
  }

  @Transactional
  public void deleteUserWithDataSensitive(Long idUser) {
    userRepository.deleteById(idUser);
  }
}
