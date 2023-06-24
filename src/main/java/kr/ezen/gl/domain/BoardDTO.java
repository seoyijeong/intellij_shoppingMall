package kr.ezen.gl.domain;

import java.sql.Date;

import lombok.Data;

//@Setter @Getter @ToString
@Data
public class BoardDTO {
	private int bid;
	private String subject;
	private String contents;
	private int hit;
	private String writer;
	private Date reg_date;
}
