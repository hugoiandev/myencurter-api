package com.myencurter.controller;

import com.myencurter.dto.UrlRequestDTO;
import com.myencurter.dto.UrlResponseDTO;
import com.myencurter.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/createShortUrl")
    public ResponseEntity<UrlResponseDTO> createShortUrl(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UrlRequestDTO urlRequestDTO,
            HttpServletRequest request) {

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = userDetails.getUsername();

        String hash = urlService.save(urlRequestDTO.url(), username);

        String domain = request.getServerName();
        int port = request.getServerPort();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UrlResponseDTO(domain + ":" + port + "/" + hash)
        );
    }
}
