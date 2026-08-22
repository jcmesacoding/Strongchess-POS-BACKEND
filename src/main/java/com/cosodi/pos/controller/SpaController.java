package com.cosodi.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SpaController {

    @RequestMapping(value = { "{path:[^\\.]*}", "/**/{path:[^\\.]*}" })
    public String redirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();

        if (uri.startsWith("/api") || uri.startsWith("/swagger") || uri.startsWith("/v3")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }

        return "forward:/index.html";
    }
}