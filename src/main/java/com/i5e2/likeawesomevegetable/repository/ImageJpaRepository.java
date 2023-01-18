package com.i5e2.likeawesomevegetable.repository;

import com.i5e2.likeawesomevegetable.domain.common.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageJpaRepository extends JpaRepository<Image, Long> {
}
