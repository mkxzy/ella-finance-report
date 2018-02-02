package com.iblotus.service;

import com.iblotus.core.AnnounceReportBuilder;
import com.iblotus.core.MyPdfExtractor;
import com.iblotus.domain.Announce;
import com.iblotus.domain.AnnounceReport;
import com.iblotus.domain.AnnounceText;
import com.iblotus.repository.AnnounceReportRepository;
import com.iblotus.repository.AnnounceTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by xiezhiyan on 18-1-28.
 */
@Service
public class AnnounceReportService {

    @Autowired
    private AnnounceReportRepository reportRepository;

    @Autowired
    private AnnounceTextRepository textRepository;

    public AnnounceReport getReport(Integer year, String cd){
        Optional<AnnounceText> announceTextOptional =
                textRepository.findTopByDisclosureTitleRegexAndCompanyCdEquals(toRegex(year), cd);
        if(announceTextOptional.isPresent()){
            AnnounceText announce = announceTextOptional.get();
            return buildReport(year, announce);
        }else{
            return null;
        }
    }

    public List<AnnounceReport> getReports(Integer year, List<String> cds){
        List<AnnounceText> list = textRepository.findByDisclosureTitleRegexAndCompanyCdIn(toRegex(year), cds);
        return list.stream().parallel()
                .map(p -> this.buildReport(year, p))
                .collect(Collectors.toList());
    }

    private AnnounceReport buildReport(Integer year, AnnounceText announce){
        AnnounceReportBuilder builder = new AnnounceReportBuilder();
        builder.buildCompany(announce.getCompanyCd(), announce.getCompanyName());
        builder.buildYear(year);
        builder.buildYssr(announce.getText());
        builder.buildZcfzl(announce.getText());
        return builder.getResult();
    }

    private String toRegex(Integer year){
        String regex = String.format("%d年(年度报告|年报)\\.?$", year);
        return regex;
    }

    public Page<AnnounceReport> getReport(Pageable pageable){
        return reportRepository.findAll(pageable);
    }

    public void create(AnnounceReport report){
        this.reportRepository.save(report);
    }

    public void createFromAnnounce(Integer year, Announce announce) throws IOException {
        String url = "http://www.neeq.com.cn" + announce.getDestFilePath();
        MyPdfExtractor extractor = new MyPdfExtractor(url);
        String text = extractor.getText();
        AnnounceReportBuilder builder = new AnnounceReportBuilder();
        builder.buildCompany(announce.getCompanyCd(), announce.getCompanyName());
        builder.buildYear(year);
        builder.buildYssr(text);
        builder.buildZcfzl(text);
        AnnounceReport report = builder.getResult();
        this.reportRepository.save(report);
    }

    private Number parseNumber(String s){
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        dfs.setGroupingSeparator(',');
        dfs.setMonetaryDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###,###.##", dfs);
        Number num = null;
        try {
            num = df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return num;
    }
}
