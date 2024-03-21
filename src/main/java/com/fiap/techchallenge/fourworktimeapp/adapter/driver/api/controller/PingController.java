package com.fiap.techchallenge.fourworktimeapp.adapter.driver.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ping")
public class PingController {
    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<String> ping() {

        return ResponseEntity.ok("pong");

    }

    @ResponseBody
    @RequestMapping(value = "/protected", method = RequestMethod.GET)
    public ResponseEntity<String> pingProtected() {

        return ResponseEntity.ok("pong, but with protection");

    }
}
