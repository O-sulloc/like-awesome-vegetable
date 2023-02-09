package com.i5e2.likeawesomevegetable.domain.user.file;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.i5e2.likeawesomevegetable.domain.user.CompanyFile;
import com.i5e2.likeawesomevegetable.domain.user.CompanyImage;
import com.i5e2.likeawesomevegetable.domain.user.CompanyUser;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.domain.user.company.dto.CompanyFileResponse;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.repository.CompanyFileJpaRepository;
import com.i5e2.likeawesomevegetable.repository.CompanyImageJpaRepository;
import com.i5e2.likeawesomevegetable.repository.CompanyUserJpaRepository;
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
public class CompanyFileUploadService {
    private final AmazonS3Client amazonS3Client;
    private final UserJpaRepository userJpaRepository;
    private final CompanyUserJpaRepository companyUserJpaRepository;
    private final CompanyImageJpaRepository companyImageJpaRepository;
    private final CompanyFileJpaRepository companyFileJpaRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /*     기업 파일 업로드     */
    public CompanyFileResponse UploadCompanyFile(Long companyId, MultipartFile multipartFile, String loginEmail) throws IOException {
        // 파일이 들어있는지 확인
        validateFilExists(multipartFile);

        User loginUser = validateLoginUser(loginEmail);
        CompanyUser companyUser = validateCompanyUser(companyId);

        // 업로드 시도하는 회원이 해당 파일을 저장하려는 기업 정회원인지
        if (loginUser.getCompanyUser().getId() != companyUser.getId()) {
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
        String key = "companyuser/file/" + storeFileName;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadException();
        }

        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
        CompanyFile companyFile = CompanyFile.makeCompanyFile(originalFilename, storeFileUrl, companyUser);
        CompanyFile savedFile = companyFileJpaRepository.save(companyFile);

        return CompanyFileResponse.of(savedFile.getCompanyFileName(), "파일 등록 성공");
    }

    /*     기업 이미지 업로드     */
    public CompanyFileResponse UploadCompanyImage(Long companyId, MultipartFile multipartFile, String loginEmail) throws IOException {
        // 파일이 들어있는지 확인
        validateFilExists(multipartFile);

        User loginUser = validateLoginUser(loginEmail);
        CompanyUser companyUser = validateCompanyUser(companyId);

        // 로그인 회원이 접근하려는 url의 기업 정회원이 맞는지
        if (loginUser.getCompanyUser().getId() != companyUser.getId()) {
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
        String key = "companyuser/image/" + storeFileName;

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadException();
        }

        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
        CompanyImage companyImage = CompanyImage.makeCompanyImage(originalFilename, storeFileUrl, companyUser);
        CompanyImage savedImage = companyImageJpaRepository.save(companyImage);

        return CompanyFileResponse.of(savedImage.getCompanyImageName(), "이미지 등록 성공");
    }

    /*     기업 이미지 다운로드     */
    public String getCompanyImage(String companyId) {
        String storeFileName = UUID.randomUUID() + "." + companyId;
        String key = "companyuser/image/" + storeFileName;

        return amazonS3Client.getUrl(bucket, key).toString();
    }

    /*     기업 파일 삭제     */
    public CompanyFileResponse deleteCompanyFile(Long companyId, Long companyFileId, String filePath, String loginEmail) {

        User loginUser = validateLoginUser(loginEmail);
        CompanyUser companyUser = validateCompanyUser(companyId);

        // 로그인 회원이 접근하려는 url의 기업 정회원이 맞는지
        if (loginUser.getCompanyUser().getId() != companyUser.getId()) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_PERMISSION, AppErrorCode.INVALID_PERMISSION.getMessage());
        }

        // 파일 존재 유무 확인
        CompanyFile companyFile = validateCompanyFile(companyFileId);

        String deleteFileName = companyFile.getCompanyFileName();

        try {
            // S3 업로드 파일 삭제
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, filePath));
            // 해당 업로드 파일 테이블에서도 같이 삭제
            companyFileJpaRepository.delete(companyFile);
            log.info("파일 삭제 성공");
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
        return CompanyFileResponse.of(deleteFileName, "파일 삭제 성공");
    }

    /*    기업 사진 삭제     */
    public CompanyFileResponse deleteCompanyImage(Long companyId, Long companyImageId, String filePath, String loginEmail) {

        User loginUser = validateLoginUser(loginEmail);
        CompanyUser companyUser = validateCompanyUser(companyId);

        // 로그인 회원이 접근하려는 url의 기업 정회원이 맞는지
        if (loginUser.getCompanyUser().getId() != companyUser.getId()) {
            throw new AwesomeVegeAppException(AppErrorCode.INVALID_PERMISSION, AppErrorCode.INVALID_PERMISSION.getMessage());
        }

        // 파일 존재 유무 확인
        CompanyImage companyImage = validateCompanyImage(companyImageId);

        String deleteImageName = companyImage.getCompanyImageName();

        try {
            // S3 업로드 파일 삭제
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, filePath));
            // 해당 업로드 파일 테이블에서도 같이 삭제
            companyImageJpaRepository.delete(companyImage);
            log.info("파일 삭제 성공");
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
        return CompanyFileResponse.of(deleteImageName, "이미지 삭제 성공");
    }

    // 기업 정회원 존재 유무 확인
    private CompanyUser validateCompanyUser(Long companyId) {
        CompanyUser validatedCompanyUser = companyUserJpaRepository.findById(companyId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.COMPANY_USER_NOT_FOUND,
                        AppErrorCode.COMPANY_USER_NOT_FOUND.getMessage())
                );
        return validatedCompanyUser;
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

    // 기업 파일 존재 확인
    private CompanyFile validateCompanyFile(Long companyFileId) {
        CompanyFile validatedCompanyFile = companyFileJpaRepository.findById(companyFileId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.COMPANY_FILE_NOT_FOUND,
                        AppErrorCode.COMPANY_FILE_NOT_FOUND.getMessage())
                );
        return validatedCompanyFile;
    }

    // 기업 이미지 존재 확인
    private CompanyImage validateCompanyImage(Long companyImageId) {
        CompanyImage validatedCompanyImage = companyImageJpaRepository.findById(companyImageId)
                .orElseThrow(() -> new AwesomeVegeAppException(
                        AppErrorCode.COMPANY_IMAGE_NOT_FOUND,
                        AppErrorCode.COMPANY_IMAGE_NOT_FOUND.getMessage())
                );
        return validatedCompanyImage;
    }

}
