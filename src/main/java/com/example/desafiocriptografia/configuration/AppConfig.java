package com.example.desafiocriptografia.configuration;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  AES256TextEncryptor textEncryptor() {
    AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
    textEncryptor.setPassword("my-secret");
    return textEncryptor;
  }
}
