package org.spring.springboot.controller;


import org.spring.springboot.service.SearchContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by full on 2017/7/9.
 */
@RestController
public class SearchController {
    @Autowired
    private SearchContextService earchContextService;

    @RequestMapping("/search")
    public void searchPdf(){
        earchContextService.getSearchCont("读取");
    }

}
