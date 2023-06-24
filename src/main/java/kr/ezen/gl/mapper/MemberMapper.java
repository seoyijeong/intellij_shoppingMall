package kr.ezen.gl.mapper;

import java.util.List;

import kr.ezen.gl.domain.MemberDTO;
import org.apache.ibatis.annotations.Mapper;



@Mapper
public interface MemberMapper {
	List<MemberDTO> memberList();
	
	int memberInsert(MemberDTO dto);
	
	int memberDelete(int no);
	
	MemberDTO memberInfo(int no);
	
	int memberUpdate(MemberDTO dto);
	
	MemberDTO idCheck(String uid);
}
