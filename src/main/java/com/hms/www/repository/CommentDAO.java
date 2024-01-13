package com.hms.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hms.www.domain.CommentVO;
import com.hms.www.domain.PagingVO;

public interface CommentDAO {

	int insert(CommentVO cvo);

	// Mapper에서 매개변수를 2개 이상 인식시키기 위해선
	// 반드시 @Param 어노테이션으로 정확한 변수명을 선언해야 함
	List<CommentVO> selectList(@Param("bno")int bno, @Param("pgvo")PagingVO pgvo);

	int selectOneBnoTotalCount(int bno);

	int update(CommentVO cvo);

	int delete(int cno);
}
