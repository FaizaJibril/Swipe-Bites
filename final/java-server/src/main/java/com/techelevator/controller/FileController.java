package com.techelevator.controller;

import com.techelevator.service.WebImageService;
import com.techelevator.model.WebImage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@CrossOrigin
@RestController
public class FileController extends BaseController {

    private final WebImageService webImageService;

    public FileController(WebImageService webImageService){
        this.webImageService = webImageService;
    }
    //ResponseEntity is being used because of the multipart

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<WebImage> uploadFile(@RequestParam MultipartFile file) {

        WebImage webImage = new WebImage();
        webImage.setFileName(file.getOriginalFilename());
        webImage.setMimeType(file.getContentType());
        webImage.setFileSize(file.getSize());
        try {
            webImage.setImage(file.getBytes());
            return ResponseEntity.ok(webImageService.saveWebImage(webImage));
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }


    }

    //ResponseEntity is being used because of the octet stream
    @RequestMapping("/file/{id}")
    public ResponseEntity<?> getFile(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id) {
        WebImage webImage = this.webImageService.getWebImage(id);
        if (webImage==null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + webImage.getFileName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(webImage.getImage());
    }
}
