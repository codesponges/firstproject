package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class MemberController {
    @Autowired // 의존성 주입 - 객체를 자동으로 생성
    private MemberRepository memberRepository;

    @GetMapping("/join")
    public String welcome() {

        return "/members/new";
    }

    
    @PostMapping("/join")
    public String signup(MemberForm form){
        //System.out.println(form.toString());
        log.info(form.toString());
        // dto를 엔티티로 변환하기
        Member memberEntity = form.toEntity();
        //System.out.println(memberEntity);
        log.info(memberEntity.toString());
        // 엔티티를 db에 저장하기
        Member saved = memberRepository.save(memberEntity);
        // System.out.println(saved.toString());
        log.info(saved.toString());

        return "redirect:/members/" + saved.getId(); // getter는 외부에서 객체의 데이터를 읽을 때 사용하는 메서드
    }


    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {
        // 1. id값으로 일치하는 데이터를 DB에서 가져오기
        Member memberEntity = memberRepository.findById(id).orElse(null);
        // 2. 가져온 데이터를 모델 객체에 담아서 뷰 페이지로 넘기기
        model.addAttribute("member", memberEntity);
        // 3. 뷰 페이지 반환하기
        return "members/show";
    }


    @GetMapping("/members")
    public String index(Model model) {
        // 1. DB에서 모든 회원정보 가져와서 리스트에 담기
        List<Member> memberEntityList = memberRepository.findAll();
        // 2. 가져온 리스트 엔티티를 모델 객체에 담아서 뷰 페이지로 넘기기
        model.addAttribute("memberList", memberEntityList);
        // 3. 뷰 페이지 반환하기
        return "members/index";
    }



    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 1. 넘어온 id로 db에서 엔티티를 찾는다
        Member member = memberRepository.findById(id).orElse(null);
        // 2. 찾은 엔티티를 모델 객체에 담아서 수정페이지로 넘겨준다
        model.addAttribute("member", member);
        // 3, 수정 페이지 반환하기
        return "members/edit";
    }


    @PostMapping("/members/update") // 폼 데이터를 받고 진행하는 코드
    public String update(MemberForm form) {
        // dto 객체가 잘 저장됬는지 확인
        log.info(form.toString());
        // dto를 엔티티로 변환
        Member memberEntity = form.toEntity();
        // 2-1 db에서 id에 해당하는 엔티티 가져오기
        Member target = memberRepository.findById(memberEntity.getId()).orElse(null);
        // 2-2 엔티티가 null이 아니면 새로운 데이터로 갱신
        if(target != null) {
            memberRepository.save(memberEntity); // 새로운 입력 정보로 갱신
        }

        return "redirect:/members/" + memberEntity.getId();
    }


    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        Member target = memberRepository.findById(id).orElse(null);

        if(target != null){
            memberRepository.delete(target);
            rttr.addFlashAttribute("msg", "탈퇴완료되었습니다!!");
        }

        return "redirect:/members";
    }

}
