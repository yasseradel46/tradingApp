package com.trading.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by daz on 29/06/2017.
 */
@RestController
@RequestMapping(path = "/account")
public class PrincipalResource {

    
    @RequestMapping(method = RequestMethod.GET)
    public String test() {
        return "yasser";
    }
}
