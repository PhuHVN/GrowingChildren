package com.example.GrowChild.repository;

import com.example.GrowChild.entity.response.Consulting;
import com.example.GrowChild.entity.response.ShareRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface ShareRecordRepository extends JpaRepository<ShareRecord, Long> {
    List<ShareRecord> findByIsDelete(boolean isDelete);

    List<ShareRecord> findShareRecordByIsDeleteFalse();

    List<ShareRecord> findByIsDeleteAndShareRecordId(boolean isDelete, long shareRecordId);

    List<ShareRecord> findByConsulting_ConsultingId(long consultingId);
}
