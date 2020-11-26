package com.jigi.web;

import com.jigi.config.oauth.LoginUser;
import com.jigi.service.MailService;
import com.jigi.service.PostService;
import com.jigi.service.UserService;
import com.jigi.web.dto.My.MyResponseDto;
import com.jigi.web.dto.PostListResponseDto;
import com.jigi.web.dto.PostResponseDto;
import com.jigi.web.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log
@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostService postService;
    private final UserService userService;
    private final MailService mailService;
    private final HttpSession httpSession;

    @GetMapping({"","/"})
    public String home(Model model){//@LoginUser SessionUser sessionUser) { /** user는 github?과 충돌나는지 이미 값이 있음;그래서 수정**/
        List<PostListResponseDto> list = postService.findPostsByCategory("arduino");
        model.addAttribute("posts", list);

        SessionUser user = (SessionUser)httpSession.getAttribute("sessionUser");
        log.info("----home user="+user);
        if(user!= null)
            model.addAttribute("sessionUser", user);

        return "home";
    }

    @GetMapping("/post/find/{id}")
    public String post_find(@PathVariable Long id, Model model){//@AuthenticationPrincipal OAuth2User oAuth2User) {
        PostResponseDto postResponseDto = postService.find(id);
        model.addAttribute("post", postResponseDto);

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        log.info("----post_find user="+sessionUser);
        if( sessionUser!=null) {
            model.addAttribute("sessionUser", sessionUser);//참여하기
            if(postResponseDto.getAuthor_oauthId().equals(sessionUser.getOauthId())) //host면 수정, 삭제 버튼 보임
                model.addAttribute("isHost", 1);
            else
                model.addAttribute("isGuest", 1);
        }
        return "post-find";
    }

    @GetMapping("/post/save")
    public String post_save(Model model){//},@AuthenticationPrincipal OAuth2User oAuth2User) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        log.info(sessionUser.toString());
        model.addAttribute("sessionUser", sessionUser );
        return "post-save";
    }

    @GetMapping("/post/update/{id}")
    public String post_update(@PathVariable Long id,  Model model){
        PostResponseDto postResponseDto = postService.find(id);
        model.addAttribute("post", postResponseDto);

        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        model.addAttribute("sessionUser", sessionUser);
        return "post-update";
    }
    @GetMapping("/my")
    public String mypage(Model model){//},@AuthenticationPrincipal OAuth2User oAuth2User) {
        SessionUser sessionUser = (SessionUser) httpSession.getAttribute("sessionUser");
        try {
            //내 계정 정보필요
            MyResponseDto myResponseDto = userService.findMyInfoByOauthId(sessionUser.getOauthId());
            model.addAttribute("my", myResponseDto);
        } catch(NullPointerException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "errorPage";
        }
        return "my";
    }

    @GetMapping("/userdenied")
    public String userDenied(){
        return "userDenied";
    }
}
