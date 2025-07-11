package com.myencurter.controller.url;

import com.myencurter.dto.url.UrlRequestDTO;
import com.myencurter.dto.url.UrlResponseDTO;
import com.myencurter.model.Url;
import com.myencurter.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/createShortUrl")
    public ResponseEntity<UrlResponseDTO> createShortUrl(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid UrlRequestDTO urlRequestDTO,
            HttpServletRequest request) {

        String username = userDetails.getUsername();

        Url url = urlService.saveUrl(urlRequestDTO.url(), username);

        String domain = request.getServerName();
        int port = request.getServerPort();

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new UrlResponseDTO(url.getId(), url.getUrl(), domain + ":" + port + "/" + url.getId())
        );
    }

    @GetMapping("/list")
    public ResponseEntity<List<UrlResponseDTO>> getAllUrlsByUserId(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {

        String username = userDetails.getUsername();

        List<Url> allLinks = urlService.getAllUrlsByUser(username);

        String domain = request.getServerName();
        int port = request.getServerPort();

        return ResponseEntity.ok(allLinks.stream().map((link) -> new UrlResponseDTO(link.getId(), link.getUrl(), domain + ":" + port + "/" + link.getId())).toList());
    }
}
