package com.example.toyproject1.controller;


import com.example.toyproject1.dto.MemberForm;
import com.example.toyproject1.entity.Member;
import com.example.toyproject1.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/members/new")
    public String newMemberForm() {
        return "members/new";
    }

    @GetMapping("/signup")
    public String signUpPage() {
        return "members/new";
    }

    @PostMapping("/join")
    public String join(MemberForm memberForm) {

        log.info(memberForm.toString());

        Member member = memberForm.toEntity();
        log.info(member.toString());

        Member saved = memberRepository.save(member);
        log.info(saved.toString());
        return "redirect:/members/" + saved.getId();

    }

    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model) {

        Member member = memberRepository.findById(id).orElse(null);
        model.addAttribute("member", member);
        return "members/show";

    }

    @GetMapping("/members")
    public String index(Model model) {

        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "members/index";

    }

    @GetMapping("/members/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        // id 기준으로 수정할 데이터 가져오기
        Member member = memberRepository.findById(id).orElse(null);

        // 모델에 데이터 추가
        model.addAttribute("member", member);

        return "members/edit";

    }

    @PostMapping("/members/update")
    public String update(MemberForm memberForm) {   // 매개변수로 DTO 받아오기

        log.info(memberForm.toString());

        // DTO를 엔티티로 변환
        Member memberEntity = memberForm.toEntity();
        log.info(memberEntity.toString());

        // DB에서 기존 데이터 가져오기
        Member memberData = memberRepository.findById(memberEntity.getId()).orElse(null);

        // 기존 데이터가 있다면 갱신
        if (memberData != null) {
            memberRepository.save(memberEntity);
        }

        // 상세 페이지로 리다이렉트
        return "redirect:/members/" + memberEntity.getId();

    }

    @GetMapping("/members/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {

        log.info("member 삭제 요청 수신");

        // 삭제 대상 가져오기
        Member memberData = memberRepository.findById(id).orElse(null);
        log.info(Objects.requireNonNull(memberData).toString());    // null이 아닌 경우 로깅, null이면 NullPointerException 발생

        // 대상 엔티티 삭제
        memberRepository.delete(memberData);
        rttr.addFlashAttribute("msg","삭제됐습니다!");

        return "redirect:/members";

    }

}
