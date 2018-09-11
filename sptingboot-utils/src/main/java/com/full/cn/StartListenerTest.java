package com.full.cn;

import com.full.cn.utils.PdfReaderUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by full on 2017/7/4.
 */
@Component
public class StartListenerTest implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        String fileName = "E:\\text\\SanDisk_SecureAccess_QSG.pdf";
        System.out.println("===================start===============");
        PdfReaderUtils.Pdfread(fileName);
        System.out.println("====================end================");
    }
}
