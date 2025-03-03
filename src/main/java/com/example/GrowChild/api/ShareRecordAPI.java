package com.example.GrowChild.api;

import com.example.GrowChild.dto.FeedBackDTO;
import com.example.GrowChild.dto.ShareRecordDTO;
import com.example.GrowChild.entity.response.ShareRecord;
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


    @GetMapping("/consulting/{consultingId}")
    public ResponseEntity<List<ShareRecord>> getSharedRecords(@PathVariable Long consultingId) {
        return ResponseEntity.ok(shareRecordService.getSharedRecordsByConsulting(consultingId));
    }


    @GetMapping("/all")
    public ResponseEntity<List<ShareRecordDTO>> getAllSharedRecords() {
        // Gọi service để lấy tất cả các bản ghi
        List<ShareRecordDTO> allSharedRecords = shareRecordService.getAll();
        return ResponseEntity.ok(allSharedRecords);  // Trả về danh sách ShareRecordDTO
    }


    @DeleteMapping("/deleteShareRecord/{shareRecordId}")
    public ResponseEntity<String> deleteShareRecord(@PathVariable long shareRecordId) {
        try {
            // Gọi service để xóa bản ghi
            String response = shareRecordService.deleteShareRecord(shareRecordId);
            return ResponseEntity.ok(response);  // Trả về thông báo xóa thành công
        } catch (RuntimeException e) {
            // Trả về thông báo lỗi nếu không tìm thấy bản ghi
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
