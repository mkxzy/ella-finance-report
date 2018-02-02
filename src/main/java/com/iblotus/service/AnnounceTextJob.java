package com.iblotus.service;

import com.iblotus.core.MyPdfExtractor;
import com.iblotus.domain.Announce;
import com.iblotus.domain.AnnounceText;
import com.iblotus.repository.AnnounceTextRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * Created by xiezhiyan on 18-1-30.
 */
@Component
public class AnnounceTextJob {

    private static final Logger log = LoggerFactory.getLogger(AnnounceTextJob.class);

    @Autowired
    private AnnounceService announceService;

    @Autowired
    private AnnounceTextRepository announceTextRepository;

    public void execute(int year){

//        Integer year = 2016;
        int size = 50;
        int page = 0;
        int totalPage;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletionService<Void> completionService = new ExecutorCompletionService(executorService);
        do {
            PageRequest pageRequest = new PageRequest(page, size);
            Page<Announce> pageData = announceService.getAll(year, pageRequest);
            for (Announce announce: pageData.getContent()){
                Callable<Void> task = () -> {
                    log.info("保存announce text");
                    AnnounceText announceText = new AnnounceText();
                    BeanUtils.copyProperties(announce, announceText);
                    MyPdfExtractor extractor = new MyPdfExtractor(this.getPdfUrl(announce));
                    announceText.setText(extractor.getText());
                    announceTextRepository.save(announceText);
                    return null;
                };
                completionService.submit(task);
            }
            try {
                completionService.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            totalPage = pageData.getTotalPages();
            page++;
        }while (page < totalPage);
    }

    private String getPdfUrl(Announce announce){
        String url = "http://www.neeq.com.cn" + announce.getDestFilePath();
        return url;
    }
}
