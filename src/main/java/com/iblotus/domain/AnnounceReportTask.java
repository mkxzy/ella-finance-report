package com.iblotus.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by xiezhiyan on 18-2-3.
 */
@Document(collection = "announce_report_task")
public class AnnounceReportTask {

    @Id
    private String _id;

    private Integer year;

    private String title;

    @DBRef
    private AnnounceReport announceReport;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
