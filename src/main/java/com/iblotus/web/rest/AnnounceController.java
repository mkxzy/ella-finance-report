package com.iblotus.web.rest;

import com.iblotus.domain.Announce;
import com.iblotus.domain.AnnounceReport;
import com.iblotus.service.AnnounceReportService;
import com.iblotus.service.AnnounceService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiezhiyan on 18-1-29.
 */
@RestController
@RequestMapping("api")
public class AnnounceController {

    @Autowired
    private AnnounceService announceService;

    @Autowired
    private AnnounceReportService announceReportService;

    @GetMapping("announce/{year}")
    public Page<Announce> getPage(@PathVariable Integer year, @PageableDefault Pageable pageable){
        return announceService.getAll(year, pageable);
    }

    @GetMapping("announce/report")
    public Page<AnnounceReport> getReport(@PageableDefault Pageable pageable){
        return announceReportService.getReport(pageable);
    }

    @GetMapping("announce/report/{year}/{cd}")
    public AnnounceReport getOne(@PathVariable Integer year,
                                 @PathVariable String cd){
        return announceReportService.getReport(year,cd);
    }

    @PostMapping("announce/report/{year}")
    public List<AnnounceReport> getList(@PathVariable Integer year, @RequestBody List<String> cds){
        return announceReportService.getReports(year, cds);
    }

    @GetMapping("announce/report/download")
    public void downLoadExcel(HttpServletResponse response) throws IOException {
        String fileName = "财务数据.xlsx";
        Resource resource = new ClassPathResource(fileName);
//        response.setHeader("content-type", "application/octet-stream");
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String(fileName.getBytes("utf-8"),"iso-8859-1"));
        List<String> list = new ArrayList<>();
        list.add("834794");
        list.add("833197");
        List<AnnounceReport> reports = announceReportService.getReports(2016, list);

        Workbook workbook = new XSSFWorkbook(resource.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for(int i=0; i<reports.size(); i++){
            AnnounceReport report = reports.get(i);
            Row row = sheet.createRow(i+2);
            row.createCell(0).setCellValue(report.getCompanyName());
            row.createCell(1).setCellValue(report.getYysr().get("current").toString());
            row.createCell(2).setCellValue(report.getYysr().get("before").toString());
        }
        workbook.write(response.getOutputStream());
    }
}
