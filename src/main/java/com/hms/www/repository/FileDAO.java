package com.hms.www.repository;

import java.util.List;

import com.hms.www.domain.FileVO;

public interface FileDAO {

	int insertFile(FileVO fvo);

	List<FileVO> selectFlist(int bno);

	int deleteFile(String uuid);

}
