package com.i5e2.likeawesomevegetable.domain.market;

import com.i5e2.likeawesomevegetable.repository.FarmAuctionImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ImgFindService {

    private final FarmAuctionImageRepository auctionImageRepository;

    public List<ImgFindListResponse> findAuctionImg(Long auctionId) {
        return auctionImageRepository.findByFarmAuctionId(auctionId).stream()
                .map(farmAuctionImage -> FarmAuctionImageFactory.from(farmAuctionImage)
                ).collect(Collectors.toList());

    }
}
