package com.myencurter.service;

import com.myencurter.model.Url;
import com.myencurter.model.User;
import com.myencurter.repository.UrlRepository;
import com.myencurter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private UserRepository userRepository;

    private static final String BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String save(String url, String username) {

        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User Not Found."));

        String shortId = generateHash();

        Url urlEntity = new Url();
        urlEntity.setId(shortId);
        urlEntity.setUrl(url);
        urlEntity.setUser(user);

        Url createdUrl = urlRepository.save(urlEntity);

        return createdUrl.getId();
    }

    public String redirectUrl(String shortId) {

        Url url = urlRepository.findById(shortId).orElseThrow(() -> new RuntimeException("Url Id Not Found."));

        return url.getUrl();
    }

    private String generateHash() {

        int length = 7;

        SecureRandom random = new SecureRandom();
        BigInteger number = new BigInteger(130, random);
        StringBuilder sb = new StringBuilder();

        while (number.compareTo(BigInteger.ZERO) > 0 && sb.length() < length) {
            int remainder = number.mod(BigInteger.valueOf(62)).intValue();
            sb.append(BASE62.charAt(remainder));
            number = number.divide(BigInteger.valueOf(62));
        }

        while (sb.length() < length) {
            sb.append(BASE62.charAt(random.nextInt(62)));
        }

        return sb.toString();
    }

}
