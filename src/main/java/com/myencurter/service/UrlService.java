package com.myencurter.service;

import com.myencurter.model.Url;
import com.myencurter.model.User;
import com.myencurter.repository.UrlRepository;
import com.myencurter.repository.UserRepository;
import com.myencurter.util.HashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashGenerator hashGenerator;

    public Url saveUrl(String url, String username) {

        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        String shortId = hashGenerator.generateHash();

        Url urlEntity = new Url();
        urlEntity.setId(shortId);
        urlEntity.setUrl(url);
        urlEntity.setUser(user);

        return urlRepository.save(urlEntity);
    }

    public Url getUrl(String shortId) {

        return urlRepository.findById(shortId).orElseThrow(() -> new RuntimeException("Url não encontrada ou expirada."));
    }

    public List<Url> getAllUrlsByUser(String username) {

        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        return urlRepository.findAllByUserId(user.getId());
    }

}
