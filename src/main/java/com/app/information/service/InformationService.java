package com.app.information.service;

import com.app.information.dao.InformationDao;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InformationService {
    @Autowired
    private InformationDao informationDao;

    public JsonNode persons (){
        return informationDao.persons();
    }

    public JsonNode foods (){
        return informationDao.foods();
    }

    public JsonNode books (){
        return informationDao.getRandomBook();
    }
}
