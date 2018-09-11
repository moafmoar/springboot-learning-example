package com.full.cn.service.impl;

import com.full.cn.service.SearchContextService;
import com.full.cn.utils.OpenOffice2Pdf;
import com.full.cn.utils.SolrUtil;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

/**
 * Created by full on 2017/7/9.
 */
@Service
public class SearchContextServiceImpl implements SearchContextService {
    private String defaulterpath = "E:\\text\\";
    @Override
    public String getSearchCont(String searchKeyword) {
        Boolean marrk = null;
        try {
            marrk = OpenOffice2Pdf.convert2PDF(defaulterpath+"daochu.docx",defaulterpath+"text.pdf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(marrk){
           SolrUtil.solrSearch(defaulterpath+"text.pdf",searchKeyword);
        }
        return null;
    }
}
