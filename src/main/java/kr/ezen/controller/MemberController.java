package kr.ezen.controller;

import java.util.List;
import java.util.UUID;

import kr.ezen.gl.domain.MemberDTO;
import kr.ezen.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MemberService service;

	@RequestMapping("/memberList.do")
	public String memberList(Model model) {
//		List<MemberDTO> memberList = dao.memberList();
		List<MemberDTO> memberList = service.memberList();
		model.addAttribute("list", memberList);

		return "member/memberList";
	}

	@RequestMapping("/memberRegister.do")
	public String memberRegister() {

		return "member/memberRegister";
	}

	@RequestMapping("/memberInsert.do")
	public String memberInsert(MemberDTO dto) {
//		dao.memberInsert(dto);
		service.memberRegister(dto);

		return "redirect:/member/memberList.do";
	}

	@RequestMapping("/memberInfo.do")
	public String memberInfo(int no, Model model) {
//		MemberDTO dto = dao.memberInfo(no);
		MemberDTO dto = service.memberInfo(no);

		model.addAttribute("dto", dto);
		return "/member/memberInfo";
	}

	@RequestMapping("/memberUpdate.do")
	public String memberUpdate(MemberDTO dto) {
//		dao.memberUpdate(dto);
		service.memberModify(dto);

		return "redirect:/member/memberList.do";
	}

	@RequestMapping("/memberDelete.do")
	public String memberDelete(int no) {
//		dao.deleteMember(no);
		service.memberRemove(no);

		return "redirect:/member/memberList.do";
	}

	// Message Converter API : jackson
	// ==> JSON 형식 자바객체로 변환, 또는 그 반대 변환

	// 비동기 전송데이터는 HTTP MSG의 body 담아서 전송한다.

	// @ResponseBody : 서버에서 클라이언트에 응답할 때, 자바객체를 HTTP 응답 MSG body에 변환해서
	// 클라이언트에 전송

	// @RequestBody : 클라이언트에서 서버로 데이터를 보낼때, HTTP 요청 MSG의 body에 담긴 값을
	// 자바객체로 변환해서 전송

	// 최근에는 주로 JSON형식, 예전에는 XML형식

	@RequestMapping("/memberAjaxList.do")
	public @ResponseBody List<MemberDTO> memberAjaxList() {
		List<MemberDTO> memberList = service.memberList();
		return memberList;
	}

	@RequestMapping("/memberIdCheck.do")
	public @ResponseBody String memberIdCheck(@RequestParam("uid") String uid) {
		MemberDTO dto = service.idCheck(uid);
		if (dto != null || "".equals(uid.trim())) {
			return "no";
		}

		return "yes";
	}

	/*ajax 는 responseBody로 데이터를 담음*/
	@RequestMapping("/memberEmailCheck.do")
	@ResponseBody()
	public String emailCheck(@RequestParam("uEmail") String uEmail) {
		System.out.println("uEmail: " + uEmail);

		//인증 코드 생성
		//자바에서 제공해주는 UUID가 랜덤으로 생성(32자리),6자리로 자르기
		String uuid = UUID.randomUUID().toString().substring(0, 6);
		System.out.println("uuid: " + uuid);

		//mime 타입으로 mailSender 연결
		MimeMessage mail = mailSender.createMimeMessage();

		//내용을 넣어 메일 보내기(사용자 인증 코드)
		String mailContents = "<h3>이메일 주소 확인</h3></br>" +
				"<span>사용자가 본인임을 확인하려고 합니다. 다음 확인 코드를 입력하세요</span>"
				+"<h2>"+uuid+"</h2>";
		//메일 컨텐츠,예외처리
		try {
			mail.setSubject("jh아카데미 [이메일 인증]", "utf-8");
			mail.setText(mailContents, "utf-8", "html");

			// 상대방 메일 셋팅(수신자정하기)
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(uEmail));

			//메일 보내기
			mailSender.send(mail);

			return uuid;

		} catch (Exception e) {
			e.printStackTrace();
		}

	return "fail";

}
}
