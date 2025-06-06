package com.myencurter.controller.redirect;

import com.myencurter.model.Url;
import com.myencurter.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class RedirectController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/{id}")
    public void redirect(@PathVariable String id, HttpServletResponse response) throws IOException {

        Url url = urlService.getUrl(id);

        response.sendRedirect(url.getUrl());
    }
}
