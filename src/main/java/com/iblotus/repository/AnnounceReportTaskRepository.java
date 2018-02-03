package com.iblotus.repository;

import com.iblotus.domain.AnnounceReportTask;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xiezhiyan on 18-2-3.
 */
@Repository
public interface AnnounceReportTaskRepository extends MongoRepository<AnnounceReportTask, String> {
}
