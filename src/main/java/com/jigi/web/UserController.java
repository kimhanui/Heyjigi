package com.jigi.web;

import com.jigi.service.UserService;
import com.jigi.web.dto.SessionUser;
import com.jigi.web.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Log
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final HttpSession httpSession;

    @GetMapping("/api/v1/user/category")//@RequestParam은 ajax에서 data:{ rawCategory: "STUDY"}
    public ResponseEntity<JSONObject> setCategory(@RequestParam String rawCategory, String oauthId) {
        ResponseEntity<JSONObject> responseEntity = null;
        JSONObject jsonObject = new JSONObject();
        try {
            userService.updateCategory(oauthId, rawCategory);
            jsonObject.put("data", "ok");
            responseEntity = new ResponseEntity<>(jsonObject, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            jsonObject.put("data", e.getMessage());
            responseEntity = new ResponseEntity<>(jsonObject, HttpStatus.OK);
        }
        return responseEntity;
    }

    @PutMapping("/api/v1/user")
    public Long update(@RequestBody UserRequestDto userRequestDto) {
        try {
            userService.updateBasic(userRequestDto);
        } catch (IllegalArgumentException e) {
            return -1L;
        }
        return 1L;
    }

    @GetMapping("/api/v1/user/cancel/{id}")
    public Long cancel_participated(@PathVariable Long id) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        try {
            userService.cancel_participated(id, sessionUser.getOauthId());
        } catch (IllegalArgumentException e) {
            return -1L;
        }
        return id;
    }


}
