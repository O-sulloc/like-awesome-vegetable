package com.i5e2.likeawesomevegetable.controller;

import com.i5e2.likeawesomevegetable.domain.Result;
import com.i5e2.likeawesomevegetable.domain.user.farm.dto.FarmFileResponse;
import com.i5e2.likeawesomevegetable.domain.user.file.FarmFileUploadService;
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
public class FarmFileController {

    private final FarmFileUploadService farmFileUploadService;

    /*     농가 이미지 업로드     */
    @PostMapping("/farm-user/{farmUserId}/images/upload")
    public ResponseEntity<Result<FarmFileResponse>> uploadFarmImage(@PathVariable("farmUserId") Long farmUserId,
                                                                    @RequestPart MultipartFile multipartFile,
                                                                    Authentication authentication) throws IOException {
        FarmFileResponse farmFileUploadResponse = farmFileUploadService.UploadFarmImage(farmUserId, multipartFile, authentication.getName());
        return ResponseEntity.ok().body(Result.success(farmFileUploadResponse));
    }

    /*     농가 파일 업로드     */
    @PostMapping("/farm-user/{farmUserId}/files/upload")
    public ResponseEntity<Result<FarmFileResponse>> uploadFarmFile(@PathVariable("farmUserId") Long farmUserId,
                                                                   @RequestPart MultipartFile multipartFile,
                                                                   Authentication authentication) throws IOException {
        String loginEmail = authentication.getName();
        FarmFileResponse farmImageUploadResponse = farmFileUploadService.UploadFarmFile(farmUserId, multipartFile, loginEmail);
        return ResponseEntity.ok().body(Result.success(farmImageUploadResponse));
    }

    /*    농가 이미지 삭제     */
    @DeleteMapping("/farm-user/{farmUserId}/farm-images/{farmImageId}/delete")
    public ResponseEntity<Result<FarmFileResponse>> deleteFarmImage(@PathVariable("farmUserId") Long farmUserId,
                                                                    @PathVariable("farmImageId") Long farmImageId,
                                                                    @RequestParam String filePath,
                                                                    Authentication authentication) {
        FarmFileResponse farmImageDeleteResponse = farmFileUploadService.deleteFarmImage(farmUserId, farmImageId, filePath, authentication.getName());
        return ResponseEntity.ok().body(Result.success(farmImageDeleteResponse));
    }

    /*     농가 파일 삭제     */
    @DeleteMapping("/farm-user/{farmUserId}/farm-files/{farmImageId}/delete")
    public ResponseEntity<Result<FarmFileResponse>> deleteCompanyFile(@PathVariable("farmUserId") Long farmUserId,
                                                                      @PathVariable("farmImageId") Long farmImageId,
                                                                      @RequestParam String filePath,
                                                                      Authentication authentication) {
        FarmFileResponse farmFileDeleteResponse = farmFileUploadService.deleteFarmFile(farmUserId, farmImageId, filePath, authentication.getName());
        return ResponseEntity.ok().body(Result.success(farmFileDeleteResponse));
    }

}
