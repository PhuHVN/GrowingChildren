package com.example.GrowChild.api;

import com.example.GrowChild.dto.FeedBackDTO;
import com.example.GrowChild.dto.ShareRecordDTO;
import com.example.GrowChild.entity.respone.ShareRecord;
import com.example.GrowChild.service.ShareRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("shareRecordAPI")
public class ShareRecordAPI {
    @Autowired
    ShareRecordService shareRecordService;

    // API chia sẻ hồ sơ
    @PostMapping("/share")
    public ResponseEntity<?> share(
            @RequestParam Long consultingId,
            @RequestParam Long healthRecordId
    ) {
        try {
            // Kiểm tra và xử lý các tham số consultingId và healthRecordId
            if (consultingId == null || healthRecordId == null) {
                return ResponseEntity.badRequest().body("Missing required fields: consultingId or healthRecordId.");
            }

            ShareRecordDTO savedRecord = shareRecordService.shareHealthRecord(consultingId, healthRecordId);
            return ResponseEntity.ok(savedRecord);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("getALl")
    public List<ShareRecordDTO> getAllShareRecord(){
        return shareRecordService.getAll();}

    @GetMapping("getShareRecordId/{shareRecord_id}")
    public ShareRecordDTO getShareRecordById(@PathVariable long shareRecord_id){
        return shareRecordService.getShareRecordById(shareRecord_id);
    }

    @GetMapping("/consulting/{consultingId}")
    public ResponseEntity<List<ShareRecord>> getSharedRecords(@PathVariable Long consultingId) {
        return ResponseEntity.ok(shareRecordService.getSharedRecordsByConsulting(consultingId));
    }
    @DeleteMapping("deleteShareRecord/{shareRecord_id}")
    public String deleteShareRecord(@RequestParam long shareRecord_id){
        return shareRecordService.deleteShareRecord(shareRecord_id);}
}
