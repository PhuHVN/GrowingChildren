package com.example.GrowChild.api;


import com.example.GrowChild.dto.FeedBackDTO;
import com.example.GrowChild.entity.request.FeedBackRequest;
import com.example.GrowChild.entity.response.FeedBack;
import com.example.GrowChild.service.FeedBackService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("feedbackAPI")
public class FeedBackAPI {

    @Autowired
    FeedBackService feedBackService;

    @PostMapping("createFeedBack")
    public ResponseEntity createFeedBack(@Valid @RequestBody FeedBackRequest feedBackRequest,
                                         @RequestParam String doctorId,
                                         @RequestParam String parentId,
                                         @RequestParam long consulting_id) {

        FeedBack feedBack = feedBackService.createFeedBack(feedBackRequest, doctorId, parentId, consulting_id);
        return new ResponseEntity<>(feedBack, HttpStatus.CREATED);
    }

    @GetMapping("getALl")
    public List<FeedBackDTO> getAllFeedBack() {
        return feedBackService.getAll();
    }

    @GetMapping("getFeedBackId/{feedback_id}")
    public FeedBackDTO getFeedBackById(@PathVariable long feedback_id) {
        return feedBackService.getFeedBackById(feedback_id);

    }

    @GetMapping("/getFeedbackByConsultingId/{consulting_id}")
    public ResponseEntity<List<FeedBackDTO>> getFeedBackByConsultingId(@PathVariable("consulting_id") long consultingId) {
        List<FeedBackDTO> feedBackDTOs = feedBackService.getFeedBackByConsultingId(consultingId);
        return ResponseEntity.ok(feedBackDTOs);
    }

    @GetMapping("/getFeedbackByDoctorId/{doctor_id}")
    public ResponseEntity<List<FeedBackDTO>> getFeedBackByDoctorId(@PathVariable("doctor_id") String doctorId) {
        List<FeedBackDTO> feedBackDTOs = feedBackService.getFeedBackByDoctorId(doctorId);
        return ResponseEntity.ok(feedBackDTOs);
    }


    @PutMapping("updateFeedBack/{feedback_id}")
    public FeedBackDTO updateFeedBack(@PathVariable long feedback_id,
                                      @RequestBody FeedBack feedBack) {
        return feedBackService.updateFeedBack(feedback_id, feedBack);
    }

    @DeleteMapping("deleteFeedBack/{feedback_id}")
    public String deleteFeedBack(@RequestParam long feedback_id) {
        return feedBackService.deleteFeedBack(feedback_id);
    }
}
