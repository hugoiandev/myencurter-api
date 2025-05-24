package com.myencurter.controller;

import com.myencurter.dto.UrlRequestDTO;
import com.myencurter.dto.UrlResponseDTO;
import com.myencurter.model.Url;
import com.myencurter.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        String username = userDetails.getUsername();

        String hash = urlService.saveUrl(urlRequestDTO.url(), username);

        String domain = request.getServerName();
        int port = request.getServerPort();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UrlResponseDTO(domain + ":" + port + "/" + hash)
        );
    }

    @GetMapping("/list")
    public ResponseEntity<List<UrlResponseDTO>> getAllUrlsByUserId(@AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        List<Url> allLinks = urlService.getUrlsByUser(username);

        return ResponseEntity.ok(allLinks.stream().map((link) -> new UrlResponseDTO(link.getUrl())).toList());
    }
}
