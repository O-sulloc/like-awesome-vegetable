package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.domain.user.FarmUser;
import com.i5e2.likeawesomevegetable.domain.user.User;
import com.i5e2.likeawesomevegetable.exception.AppErrorCode;
import com.i5e2.likeawesomevegetable.exception.AwesomeVegeAppException;
import com.i5e2.likeawesomevegetable.repository.FarmAuctionJpaRepository;
import com.i5e2.likeawesomevegetable.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuctionService {
    private final FarmAuctionJpaRepository auctionJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final ImgUploadService imgUploadService;


    public FarmAuction creatAuctionNoneAuth(AuctionRequest auctionRequest) {

        FarmAuction farmAuction = auctionRequest.toEntityNoneAuth(auctionRequest);
        auctionJpaRepository.save(farmAuction);

        return farmAuction;
    }

    public AuctionResponse createAuction(AuctionRequest auctionRequest, List<MultipartFile> imgs, String email) throws IOException {

        User user = userJpaRepository.findByEmail(email).get();
        FarmUser farmUser = user.getFarmUser();

        notValidFarmUser(farmUser);


        FarmAuction farmAuction = auctionRequest.toEntity(auctionRequest, farmUser);
        auctionJpaRepository.save(farmAuction);

        for (MultipartFile img : imgs) {
            imgUploadService.farmUploadImg(img, farmAuction);
        }

        AuctionResponse auctionResponse = AuctionResponse.builder()
                .auctionId(farmAuction.getId())
                .message("경매 게시글 작성 완료")
                .build();
        return auctionResponse;

    }

    private void notValidFarmUser(FarmUser farmUser) {
        if (farmUser == null) {
            throw new AwesomeVegeAppException(
                    AppErrorCode.FARM_USER_NOT_FOUND,
                    AppErrorCode.FARM_USER_NOT_FOUND.getMessage()
            );
        }
    }
}
