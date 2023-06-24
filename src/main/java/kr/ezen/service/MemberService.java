package kr.ezen.service;

import kr.ezen.gl.domain.MemberDTO;

import java.util.List;

public interface MemberService {
    //추상 메서드
    List<MemberDTO> memberList();

    int memberRegister(MemberDTO dto);

    int memberRemove(int no);

    MemberDTO memberInfo(int no);

    int memberModify(MemberDTO dto);

    MemberDTO idCheck(String uid);
}











