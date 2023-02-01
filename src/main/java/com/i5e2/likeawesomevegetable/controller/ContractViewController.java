package com.i5e2.likeawesomevegetable.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.i5e2.likeawesomevegetable.domain.contract.ContractService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@Controller
@RequestMapping("/contractView")
@RequiredArgsConstructor
public class ContractViewController {

    private final ContractService contractService;

    @GetMapping("/sign")
    public ModelAndView getForm() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException, ParseException, InvalidKeyException {

        String accessToken = contractService.getAccessToken();
        String refreshToken = contractService.getRefreshToken();

        ModelAndView mv = new ModelAndView("/contract/eform");
        mv.addObject("accessToken", accessToken);
        mv.addObject("refreshToken", refreshToken);

        return mv;
    }
}
