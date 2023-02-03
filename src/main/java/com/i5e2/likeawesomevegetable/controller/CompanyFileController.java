package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.user.company.dto.CompanyFileResponse;
import com.i5e2.likeawesomevegetable.domain.user.file.CompanyFileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user/mypage")
public class CompanyFileController {
    private final CompanyFileUploadService companyFileUploadService;

    /*     기업 이미지 업로드     */
    @PostMapping("/company-user/{companyUserId}/images/upload")
    public ResponseEntity<Result<CompanyFileResponse>> uploadCompanyImage(@PathVariable("companyUserId") Long companyUserId,
                                                                          @RequestPart MultipartFile multipartFile,
                                                                          Authentication authentication) throws IOException {
        CompanyFileResponse companyFileUploadResponse = companyFileUploadService.UploadCompanyImage(companyUserId, multipartFile, authentication.getName());

        return ResponseEntity.ok().body(Result.success(companyFileUploadResponse));
    }

    /*     기업 파일 업로드     */
    @PostMapping("/company-user/{companyUserId}/files/upload")
    public ResponseEntity<Result<CompanyFileResponse>> uploadCompanyFile(@PathVariable("companyUserId") Long companyUserId,
                                                                         @RequestPart MultipartFile multipartFile,
                                                                         Authentication authentication) throws IOException {
        CompanyFileResponse companyImageUploadResponse = companyFileUploadService.UploadCompanyFile(companyUserId, multipartFile, authentication.getName());
        return ResponseEntity.ok().body(Result.success(companyImageUploadResponse));
    }

    /*    기업 이미지 삭제     */
    @DeleteMapping("/company-user/{companyUserId}/company-images/{companyImageId}/delete")
    public ResponseEntity<Result<CompanyFileResponse>> deleteCompanyImage(@PathVariable("companyUserId") Long companyUserId,
                                                                          @PathVariable("companyImageId") Long companyImageId,
                                                                          @RequestParam String filePath,
                                                                          Authentication authentication) {
        CompanyFileResponse companyImageDeleteResponse = companyFileUploadService.deleteCompanyImage(companyUserId, companyImageId, filePath, authentication.getName());
        return ResponseEntity.ok().body(Result.success(companyImageDeleteResponse));
    }

    /*     기업 파일 삭제     */
    @DeleteMapping("/{userId}/company-user/{companyUserId}/company-files/{companyFileId}/delete")
    public ResponseEntity<Result<CompanyFileResponse>> deleteCompanyFile(@PathVariable("companyUserId") Long companyUserId,
                                                                         @PathVariable("companyFileId") Long companyFileId,
                                                                         @RequestParam String filePath,
                                                                         Authentication authentication) {
        CompanyFileResponse companyFileDeleteResponse = companyFileUploadService.deleteCompanyFile(companyUserId, companyFileId, filePath, authentication.getName());
        return ResponseEntity.ok().body(Result.success(companyFileDeleteResponse));
    }

}
