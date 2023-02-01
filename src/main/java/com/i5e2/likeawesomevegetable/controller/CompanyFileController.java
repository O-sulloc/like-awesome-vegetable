package com.i5e2.likeawesomevegetable.controller;

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
    public ResponseEntity<CompanyFileResponse> uploadCompanyImage(@PathVariable("companyUserId") Long companyUserId,
                                                                  @RequestPart MultipartFile multipartFile,
                                                                  Authentication authentication) throws IOException {
        CompanyFileResponse companyFileUploadResponse = companyFileUploadService.UploadCompanyImage(companyUserId, multipartFile, authentication.getName());

        return ResponseEntity.ok().body(companyFileUploadResponse);
    }

    /*     기업 파일 업로드     */
    @PostMapping("/company-user/{companyUserId}/files/upload")
    public ResponseEntity<CompanyFileResponse> uploadCompanyFile(@PathVariable("companyUserId") Long companyUserId,
                                                                 @RequestPart MultipartFile multipartFile,
                                                                 Authentication authentication) throws IOException {
        CompanyFileResponse companyImageUploadResponse = companyFileUploadService.UploadCompanyFile(companyUserId, multipartFile, authentication.getName());
        return ResponseEntity.ok().body(companyImageUploadResponse);
    }

    /*    기업 이미지 삭제     */
    @DeleteMapping("/company-user/{companyUserId}/company-images/{companyImageId}/delete")
    public ResponseEntity<CompanyFileResponse> deleteCompanyImage(@PathVariable("companyUserId") Long companyUserId,
                                                                  @PathVariable("companyImageId") Long companyImageId,
                                                                  @RequestParam String filePath,
                                                                  Authentication authentication) {
        CompanyFileResponse companyImageDeleteResponse = companyFileUploadService.deleteCompanyImage(companyUserId, companyImageId, filePath, authentication.getName());
        return ResponseEntity.ok().body(companyImageDeleteResponse);
    }

    /*     기업 파일 삭제     */
    @DeleteMapping("/{userId}/company-user/{companyUserId}/company-files/{companyFileId}/delete")
    public ResponseEntity<CompanyFileResponse> deleteCompanyFile(@PathVariable("companyUserId") Long companyUserId,
                                                                 @PathVariable("companyFileId") Long companyFileId,
                                                                 @RequestParam String filePath,
                                                                 Authentication authentication) {
        CompanyFileResponse companyFileDeleteResponse = companyFileUploadService.deleteCompanyFile(companyUserId, companyFileId, filePath, authentication.getName());
        return ResponseEntity.ok().body(companyFileDeleteResponse);
    }

}
