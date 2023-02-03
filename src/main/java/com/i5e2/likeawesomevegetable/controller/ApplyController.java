package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.apply.ApplyService;
import com.i5e2.likeawesomevegetable.domain.apply.dto.ApplyRequest;
import com.i5e2.likeawesomevegetable.domain.apply.dto.ApplyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/market/buying/{companyBuyingId}")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    // 모집 조회
    @GetMapping("/apply")
    public ResponseEntity<Page<ApplyResponse>> getApply(
            @PathVariable Long companyBuyingId,
            @PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ApplyResponse> applyResponsePage = applyService.list(companyBuyingId, pageable);
        return ResponseEntity.ok().body(applyResponsePage);
    }

    // 모집 진행률
    @GetMapping("/progress")
    public ResponseEntity<Double> progress(@PathVariable Long companyBuyingId) {

        double currentProgress = applyService.progress(companyBuyingId);
        return ResponseEntity.ok().body(currentProgress);
    }

    // 참여 신청
    @PostMapping("/apply")
    public ResponseEntity<String> quantityInput(@RequestBody ApplyRequest request, @PathVariable Long companyBuyingId,
                                                Authentication authentication) {

        applyService.apply(request, companyBuyingId, authentication.getName());
        return ResponseEntity.ok().body("신청 완료");
    }

//    // 모집 완료
//    @PostMapping("/complete")
//    public ResponseEntity<String> complete(@PathVariable Long companyBuyingId, Authentication authentication) {
//
//        applyService.complete(companyBuyingId, authentication.getName());
//        return ResponseEntity.ok().body("모집 종료");
//    }
}
