package com.i5e2.likeawesomevegetable.domain.user.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.i5e2.likeawesomevegetable.domain.user.FarmFile;
import com.i5e2.likeawesomevegetable.domain.user.FarmImage;
import com.i5e2.likeawesomevegetable.domain.user.FarmUser;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.farm.dto.FarmFileResponse;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.repository.FarmFileJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmImageJpaRepository;
import com.i5e2.likeawesomevegetable.repository.FarmUserRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FarmFileUploadService {
    private final AmazonS3Client amazonS3Client;
    private final UserJpaRepository userJpaRepository;
    private final FarmUserRepository farmUserRepository;
    private final FarmImageJpaRepository farmImageJpaRepository;
    private final FarmFileJpaRepository farmFileJpaRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /*     농가 파일 업로드     */
    public FarmFileResponse UploadFarmFile(Long farmId, MultipartFile multipartFile, String loginEmail) throws IOException {
        // 파일이 들어있는지 확인
        validateFilExists(multipartFile);

        User loginUser = validateLoginUser(loginEmail);
        FarmUser farmUser = validateFarmUser(farmId);

        // 업로드 시도하는 회원이 해당 파일을 저장하려는 농가 정회원인지
        if (loginUser.getFarmUser().getId() != farmUser.getId()) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_PERMISSION, AppErrorCode.INVALID_PERMISSION.getMessage());
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index + 1);

        // 저장될 파일 이름
        String storeFileName = UUID.randomUUID() + "." + ext;

        // 저장할 디렉토리 경로 + 파일 이름
        String key = "farmuser/file/" + storeFileName;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadException();
        }

        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
        FarmFile farmFile = FarmFile.makeFarmFile(originalFilename, storeFileUrl, farmUser);
        FarmFile savedFile = farmFileJpaRepository.save(farmFile);

        return FarmFileResponse.of(savedFile.getFarmFileName(), "파일 등록 성공");
    }

    /*     농가 이미지 업로드     */
    public FarmFileResponse UploadFarmImage(Long farmId, MultipartFile multipartFile, String loginEmail) throws IOException {
        // 파일이 들어있는지 확인
        validateFilExists(multipartFile);

        User loginUser = validateLoginUser(loginEmail);
        FarmUser farmUser = validateFarmUser(farmId);

        // 로그인 회원이 접근하려는 url의 농가 정회원이 맞는지
        if (loginUser.getFarmUser().getId() != farmUser.getId()) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_PERMISSION, AppErrorCode.INVALID_PERMISSION.getMessage());
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        String originalFilename = multipartFile.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(index + 1);

        // 저장될 파일 이름
        String storeFileName = UUID.randomUUID() + "." + ext;

        // 저장할 디렉토리 경로 + 파일 이름
        String key = "farmuser/image/" + storeFileName;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadException();
        }

        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
        FarmImage farmImage = FarmImage.makeFarmImage(originalFilename, storeFileUrl, farmUser);
        FarmImage savedImage = farmImageJpaRepository.save(farmImage);

        return FarmFileResponse.of(savedImage.getFarmImageName(), "이미지 등록 성공");
    }

    /*     농가 파일 삭제     */
    public FarmFileResponse deleteFarmFile(Long farmId, Long farmFileId, String filePath, String loginEmail) {

        User loginUser = validateLoginUser(loginEmail);
        FarmUser farmUser = validateFarmUser(farmId);

        // 로그인 회원이 접근하려는 url의 농가 정회원이 맞는지
        if (loginUser.getFarmUser().getId() != farmUser.getId()) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_PERMISSION, AppErrorCode.INVALID_PERMISSION.getMessage());
        }

        // 파일 존재 유무 확인
        FarmFile farmFile = validateFarmFile(farmFileId);

        String deleteFileName = farmFile.getFarmFileName();

        try {
            // S3 업로드 파일 삭제
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, filePath));
            // 해당 업로드 파일 테이블에서도 같이 삭제
            farmFileJpaRepository.delete(farmFile);
            log.info("파일 삭제 성공");
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
        return FarmFileResponse.of(deleteFileName, "파일 삭제 성공");
    }

    /*    기업 사진 삭제     */
    public FarmFileResponse deleteFarmImage(Long farmId, Long farmImageId, String filePath, String loginEmail) {

        User loginUser = validateLoginUser(loginEmail);
        FarmUser farmUser = validateFarmUser(farmId);

        // 로그인 회원이 접근하려는 url의 농가 정회원이 맞는지
        if (loginUser.getFarmUser().getId() != farmUser.getId()) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_PERMISSION, AppErrorCode.INVALID_PERMISSION.getMessage());
        }

        // 파일 존재 유무 확인
        FarmImage farmImage = validateFarmImage(farmImageId);

        String deleteImageName = farmImage.getFarmImageName();

        try {
            // S3 업로드 파일 삭제
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, filePath));
            // 해당 업로드 파일 테이블에서도 같이 삭제
            farmImageJpaRepository.delete(farmImage);
            log.info("파일 삭제 성공");
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
        return FarmFileResponse.of(deleteImageName, "이미지 삭제 성공");
    }

    // 농가 정회원 존재 유무 확인
    private FarmUser validateFarmUser(Long farmId) {
        FarmUser validateFarmUser = farmUserRepository.findById(farmId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.FARM_USER_NOT_FOUND,
                        AppErrorCode.FARM_USER_NOT_FOUND.getMessage())
                );
        return validateFarmUser;
    }

    // 로그인 이메일 확인
    private User validateLoginUser(String loginEmail) {
        User loginUser = userJpaRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.LOGIN_USER_NOT_FOUND,
                        AppErrorCode.LOGIN_USER_NOT_FOUND.getMessage()
                ));
        return loginUser;
    }

    // 빈 파일이 아닌지 확인, 파일 자체를 첨부안하거나 첨부해도 내용이 비어있으면 에러 처리
    private void validateFilExists(MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new AwesomeVegeAppException(
                    AppErrorCode.FILE_NOT_EXISTS,
                    AppErrorCode.FILE_NOT_EXISTS.getMessage()
            );
        }
    }

    // 농가 파일 존재 확인
    private FarmFile validateFarmFile(Long farmFileId) {
        FarmFile validatedFarmFile = farmFileJpaRepository.findById(farmFileId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.FARM_FILE_NOT_FOUND,
                        AppErrorCode.FARM_FILE_NOT_FOUND.getMessage())
                );
        return validatedFarmFile;
    }

    // 농가 이미지 존재 확인
    private FarmImage validateFarmImage(Long farmImageId) {
        FarmImage validateFarmImage = farmImageJpaRepository.findById(farmImageId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.FARM_IMAGE_NOT_FOUND,
                        AppErrorCode.FARM_IMAGE_NOT_FOUND.getMessage())
                );
        return validateFarmImage;
    }

}
