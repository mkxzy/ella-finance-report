package com.iblotus.web.rest;

import com.iblotus.domain.AnnounceReportTask;
import com.iblotus.service.AnnounceReportTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiezhiyan on 18-2-3.
 */
@RestController
@RequestMapping("api")
public class AnnounceReportTaskController {

    @Autowired
    private AnnounceReportTaskService taskService;

    @PostMapping("report_task")
    public void create(@RequestBody AnnounceReportTask task){
        taskService.create(task);
    }
}
