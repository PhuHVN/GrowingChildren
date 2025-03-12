package com.example.GrowChild.repository;

import com.example.GrowChild.entity.response.Consulting;
import com.example.GrowChild.entity.response.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
    List<FeedBack> findFeedbackByIsDeleteFalse();

    FeedBack findFeedbackByIsDeleteFalseAndFeedbackId(long feedbackId);

    List<FeedBack> findFeedbackByIsDeleteFalseAndConsultingId(Consulting consulting);
}
