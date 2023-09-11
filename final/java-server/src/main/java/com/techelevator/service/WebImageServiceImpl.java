package com.techelevator.service;

import com.techelevator.dao.WebImageDao;
import com.techelevator.model.WebImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebImageServiceImpl implements WebImageService {
    private final WebImageDao webImageDao;
    @Autowired
    public WebImageServiceImpl(WebImageDao webImageDao){
        this.webImageDao = webImageDao;
    }
    @Override
    public WebImage getWebImage(Long id){
        return webImageDao.getWebImage(id);
    }
    @Override
    public WebImage saveWebImage(WebImage webImage){
        return webImageDao.saveWebImage(webImage);
    }
}
