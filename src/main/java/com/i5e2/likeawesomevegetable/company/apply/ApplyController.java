package com.i5e2.likeawesomevegetable.company.apply;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.company.apply.dto.ApplyRequest;
import com.i5e2.likeawesomevegetable.company.apply.dto.ApplyResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@Api("ApplyController")
@RequestMapping("/api/v1/buying/{companyBuyingId}")
@RequiredArgsConstructor
public class ApplyController {

    private final ApplyService applyService;

    // 모집 참여 조회
    @ApiOperation(value = "모집 참여 조회", notes = "모집 게시글 id를 통해 참여 목록을 조회한다")
    @GetMapping("/list")
    public ResponseEntity<Result<Page<ApplyResponse>>> getApply(
            @PathVariable Long companyBuyingId,
            @PageableDefault(size = 10, direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ApplyResponse> applyResponsePage = applyService.list(companyBuyingId, pageable);
        return ResponseEntity.ok().body(Result.success(applyResponsePage));
    }

    // 모집 참여 신청
    @ApiOperation(value = "모집 참여 신청", notes = "모집 게시글 id를 통해 참여 신청을 한다")
    @PostMapping("/apply")
    public ResponseEntity<Result<ApplyResponse>> quantityInput(
            @RequestBody ApplyRequest request, @PathVariable Long companyBuyingId, Authentication authentication, HttpSession session) {

        ApplyResponse applyResponse = applyService.apply(request, companyBuyingId, authentication.getName(), session);
        return ResponseEntity.ok().body(Result.success(applyResponse));
    }
}
