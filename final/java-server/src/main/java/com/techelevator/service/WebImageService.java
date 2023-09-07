package com.techelevator.service;

import com.techelevator.model.WebImage;

public interface WebImageService {
    WebImage getWebImage(Long id);

    WebImage saveWebImage(WebImage webImage);
}
