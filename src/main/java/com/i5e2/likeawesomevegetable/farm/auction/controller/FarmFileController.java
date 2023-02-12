package com.i5e2.likeawesomevegetable.farm.auction.controller;

import com.i5e2.likeawesomevegetable.common.Result;
import com.i5e2.likeawesomevegetable.farm.auction.dto.FarmFileResponse;
import com.i5e2.likeawesomevegetable.farm.auction.service.FarmFileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Api("Farm User File Controller")
@RequestMapping("/api/v1/user/mypage")
public class FarmFileController {

    private final FarmFileUploadService farmFileUploadService;

    @ApiOperation(
            value = "농가 이미지 업로드",
            notes = "농가 사용자 ID로 해당 농가 사용자의 이미지를 S3에 업로드한다.")
    @PostMapping("/farm-user/{farmUserId}/images/upload")
    public ResponseEntity<Result<FarmFileResponse>> uploadFarmImage(@PathVariable("farmUserId") Long farmUserId,
                                                                    @RequestPart MultipartFile multipartFile,
                                                                    Authentication authentication) throws IOException {
        FarmFileResponse farmFileUploadResponse = farmFileUploadService.UploadFarmImage(farmUserId, multipartFile, authentication.getName());
        return ResponseEntity.ok().body(Result.success(farmFileUploadResponse));
    }

    @ApiOperation(
            value = "농가 파일 업로드",
            notes = "농가 사용자 ID로 해당 농가 사용자의 파일을 S3에 업로드한다.")
    @PostMapping("/farm-user/{farmUserId}/files/upload")
    public ResponseEntity<Result<FarmFileResponse>> uploadFarmFile(@PathVariable("farmUserId") Long farmUserId,
                                                                   @RequestPart MultipartFile multipartFile,
                                                                   Authentication authentication) throws IOException {
        String loginEmail = authentication.getName();
        FarmFileResponse farmImageUploadResponse = farmFileUploadService.UploadFarmFile(farmUserId, multipartFile, loginEmail);
        return ResponseEntity.ok().body(Result.success(farmImageUploadResponse));
    }

    @ApiOperation(
            value = "농가 이미지 삭제",
            notes = "농가 사용자 ID와 이미지 ID로 S3에 있는 해당 농가 사용자의 해당 이미지를 삭제한다")
    @DeleteMapping("/farm-user/{farmUserId}/farm-images/{farmImageId}/delete")
    public ResponseEntity<Result<FarmFileResponse>> deleteFarmImage(@PathVariable("farmUserId") Long farmUserId,
                                                                    @PathVariable("farmImageId") Long farmImageId,
                                                                    @RequestParam String filePath,
                                                                    Authentication authentication) {
        FarmFileResponse farmImageDeleteResponse = farmFileUploadService.deleteFarmImage(farmUserId, farmImageId, filePath, authentication.getName());
        return ResponseEntity.ok().body(Result.success(farmImageDeleteResponse));
    }

    @ApiOperation(
            value = "농가 이미지 삭제",
            notes = "농가 사용자 ID와 이미지 ID로 S3에 있는 해당 농가 사용자의 해당 이미지를 삭제한다")
    @DeleteMapping("/farm-user/{farmUserId}/farm-files/{farmImageId}/delete")
    public ResponseEntity<Result<FarmFileResponse>> deleteCompanyFile(@PathVariable("farmUserId") Long farmUserId,
                                                                      @PathVariable("farmImageId") Long farmImageId,
                                                                      @RequestParam String filePath,
                                                                      Authentication authentication) {
        FarmFileResponse farmFileDeleteResponse = farmFileUploadService.deleteFarmFile(farmUserId, farmImageId, filePath, authentication.getName());
        return ResponseEntity.ok().body(Result.success(farmFileDeleteResponse));
    }

}
