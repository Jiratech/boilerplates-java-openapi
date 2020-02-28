package com.project.server.rest;

import com.project.server.business.StorageService;
import com.project.server.business.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api")
public class TestContoller {


    private static final Logger logger = LoggerFactory.getLogger(TestContoller.class);

    @Autowired
    private StorageService storageService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ResponseEntity<String> welcome() {
        logger.info("Welcome endpoint");
        return new ResponseEntity<>("test", HttpStatus.OK);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@RequestPart(value = "file") MultipartFile file) {
        String fileUrl = storageService.uploadFile(file);
        logger.info("Upload endpoint");
        return new ResponseEntity<>(fileUrl, HttpStatus.OK);
    }

}
