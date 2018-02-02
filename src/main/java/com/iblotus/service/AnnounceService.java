package com.iblotus.service;

import com.iblotus.domain.Announce;
import com.iblotus.repository.AnnounceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by xiezhiyan on 18-1-29.
 */
@Service
public class AnnounceService {
    @Autowired
    private AnnounceRepository announceRepository;

    public Page<Announce> getAll(Integer year, Pageable pageable){
        String regex = String.format("%d年年度报告$", year);
        return announceRepository.findByDisclosureTitleRegex(regex, pageable);
    }
}
