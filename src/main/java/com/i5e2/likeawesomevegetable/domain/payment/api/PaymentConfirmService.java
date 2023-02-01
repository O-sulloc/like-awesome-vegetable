package com.i5e2.likeawesomevegetable.domain.payment.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class PaymentConfirmService {

    public String verifySuccessRequest() {
        return "";
    }
}
