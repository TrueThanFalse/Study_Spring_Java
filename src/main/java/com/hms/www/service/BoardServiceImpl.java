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
	@Transactional
	public List<BoardVO> getList(PagingVO pgvo) {
		/*
		 * list.jsp 진입할 때 마다 모든 BoardVO의
		 * cmtQty와 hasFile을 update 해주기
		 */
		bdao.updateCommentQty();
		bdao.updateFileQty();
		return bdao.selectList(pgvo);
	}

	@Override
	@Transactional
	/*
	 * DB와 연결되는 DAO가 2개 이상 존재하면 @Transactional
	 * 어노테이션을 활용하여 다른 간섭을 예방해야 한다.
	 */
	public BoardDTO getDetail(int bno) {
		bdao.updateReadCountUp(bno);
		
		BoardVO bvo = bdao.selectDetail(bno);
		List<FileVO> flist = fdao.selectFlist(bno);
		
		BoardDTO bdto = new BoardDTO(bvo, flist);
		return bdto;
	}

	@Override
	@Transactional
	public BoardDTO getModify(int bno) {
		BoardVO bvo = bdao.selectDetail(bno);
		List<FileVO> flist = fdao.selectFlist(bno);
		
		BoardDTO bdto = new BoardDTO(bvo, flist);
		return bdto;
	}

	@Override
	public int bvoDelete(int bno) {
		return bdao.deleteBvo(bno);
	}

	@Override
	@Transactional
	public int edit(BoardDTO bdto) {
		// insert Method와 비슷함
		int isOK = bdao.updateEdit(bdto.getBvo());
		
		if(bdto.getFlist() == null) {
			return isOK;
		}
		
		if(isOK > 0 && bdto.getFlist().size() > 0) {
			// bno setting : insert 때와는 달리
			// bno가 이미 생성 되어 있음
			long bno = bdto.getBvo().getBno();
			
			for(FileVO fvo : bdto.getFlist()) {
				fvo.setBno(bno);
				isOK *= fdao.insertFile(fvo);
			}
		}
		
		return isOK;
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return bdao.selectTotalCount(pgvo);
	}

	@Override
	public int deleteFile(String uuid) {
		return fdao.deleteFile(uuid);
	} 
	
}
