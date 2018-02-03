package com.iblotus.service;

import com.iblotus.domain.AnnounceReportTask;
import com.iblotus.repository.AnnounceReportTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiezhiyan on 18-2-3.
 */
@Service
public class AnnounceReportTaskService {

    @Autowired
    private AnnounceReportTaskRepository taskRepository;

    public void create(AnnounceReportTask task){
        taskRepository.save(task);
    }
}
