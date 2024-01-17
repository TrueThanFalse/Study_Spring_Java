package com.hms.www.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hms.www.domain.BoardDTO;
import com.hms.www.domain.BoardVO;
import com.hms.www.domain.FileVO;
import com.hms.www.domain.PagingVO;
import com.hms.www.repository.BoardDAO;
import com.hms.www.repository.FileDAO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardDAO bdao;
	
	private final FileDAO fdao;

	@Override
	@Transactional
	public int insert(BoardDTO bdto) {
		// bvo는 boardMapper를 통해서 board DB에 등록하기
		// flist는 fileMapper를 통해서 file DB에 등록하기
		
		int isOK = bdao.insert(bdto.getBvo());
		// board DB에 bvo 등록 완료.
		// bvo 저장 후 file upload할 자료가 존재하는지 확인
		if(bdto.getFlist() == null) {
			// file upload 자료가 없다면 즉시 return하여 Method 종료
			return isOK;
		}
		
		// file upload 자료가 존재한다면...
		if(isOK > 0 && bdto.getFlist().size() > 0) {
			// fvo의 bno 값은 아직 세팅되지 않았음
			
			// bno는 현 시점에서 bvo가 DB에 등록되면서
			// file들이 저장될 bvo의 bno 값이 생성됨
			// 따라서 bno를 가져와서 fvo에 세팅해줘야 함
			long bno = bdao.selectOneBno();
			// 가장 마지막에 등록된 bno 가져오기 (방금 생성한 bvo의 bno)
			// => MAX(bno)
			
			// 각각의 fvo에 bno 세팅 후 file DB에 등록하기
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				isOK *= fdao.insertFile(fvo);
			}
		}
		
		return isOK;
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		return bdao.selectList(pgvo);
	}

	@Override
	public BoardVO getDetail(int bno) {
		return bdao.selectDetail(bno);
	}

	@Override
	public BoardVO getModify(int bno) {
		bdao.updateReadCountUp(bno);
		return bdao.selectModify(bno);
	}

	@Override
	public int bvoDelete(int bno) {
		return bdao.deleteBvo(bno);
	}

	@Override
	public int edit(BoardVO bvo) {
		return bdao.updateEdit(bvo);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return bdao.selectTotalCount(pgvo);
	} 
	
}
