package com.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ShortUrlController {
    private Map<String, url> map = new HashMap<>();

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/new-url")
    public String shortenUrl(@RequestParam String longUrl, Model model) {
        String shortUrl = generateShortUrl();
        map.put(shortUrl, new url(longUrl));
        model.addAttribute("shortUrl", shortUrl);
        return "index";
    }

    @GetMapping("/{shortUrl}")
    public String redirectToLongUrl(@PathVariable String shortUrl, Model model) {
        if (map.containsKey(shortUrl)) {
            url url = map.get(shortUrl);
            if (url.isValid()) {
                return "redirect:" + url.getLongUrl();
            } else {
                model.addAttribute("message", "URL has expired.");
                return "error";
            }
        } else {
            model.addAttribute("message", "URL doesn't exist.");
            return "error";
        }
    }

    private String generateShortUrl() {
        // Generate a short URL logic here
        // This could involve generating a random or unique ID
        // and appending it to your base URL, like http://localhost:7070/
        return "3xerdsfs"; // Replace with actual short URL
    }

    private class url {
        private String longUrl;
        private long creationTime;

        public url(String longUrl) {
            this.longUrl = longUrl;
            this.creationTime = System.currentTimeMillis();
        }

        public String getLongUrl() {
            return longUrl;
        }

        public boolean isValid() {
            long currentTime = System.currentTimeMillis();
            return currentTime - creationTime <= 5 * 60 * 1000; // 5 minutes validity
        }
    }
}
