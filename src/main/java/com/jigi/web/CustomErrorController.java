//package com.jigi.web;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//public class CustomErrorController implements ErrorController {
//
//    @GetMapping("/error")
//    public String handleError(HttpServletRequest request) {
////        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
////        if (status != null) {
////            int statusCode = Integer.valueOf(status.toString());
////            if (statusCode == HttpStatus.NOT_FOUND.value()) {
////                return "errors/404-custom";
////            }
////        }
//        return "error";
//    }
//
//    @Override
//    public String getErrorPath(){
//        return null;
//    }
//}
