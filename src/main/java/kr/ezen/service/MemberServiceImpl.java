package kr.ezen.service;

import kr.ezen.gl.domain.MemberDTO;
import kr.ezen.gl.mapper.BoardMapper;
import kr.ezen.gl.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper mapper;

	@Override
	public List<MemberDTO> memberList() {
		return mapper.memberList();
	}

	@Override
	public int memberRegister(MemberDTO dto) {
		return mapper.memberInsert(dto);
	}

	@Override
	public int memberRemove(int no) {
		return mapper.memberDelete(no);
	}

	@Override
	public MemberDTO memberInfo(int no) {
		return mapper.memberInfo(no);
	}

	@Override
	public int memberModify(MemberDTO dto) {
		return mapper.memberUpdate(dto);
	}

	@Override
	public MemberDTO idCheck(String uid) {
		return mapper.idCheck(uid);
	}
}