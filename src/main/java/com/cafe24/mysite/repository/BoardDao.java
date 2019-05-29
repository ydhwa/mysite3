package com.cafe24.mysite.repository;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;

@Repository
public class BoardDao {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private SqlSession sqlSession;
	
	// 리스트 얻기 [일반 / 검색]
	public List<BoardVo> getList(Map map) {
		return sqlSession.selectList("board.getList", map);
	}
	
	// 게시글 얻기 [뷰 / 수정]
	public BoardVo get(Map map) {
		return sqlSession.selectOne("board.getByNo", map);
	}
	
	// 전체 게시글 수 얻기 [일반 / 검색]
	public Integer getListSize(String keyword) {
		return sqlSession.selectOne("board.getListSize", keyword);
	}
	
	// 삽입
	public Boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return 1 == count;
	}
	
	// orderNo 재정렬(수정 시 사용)
	public Boolean updateOrderNo(Map map) {
		int count = sqlSession.update("board.updateOrderNo", map);
		return 1 == count;
	}
	
	// 수정
	public Boolean update(BoardVo vo) {
		int count = sqlSession.update("board.update", vo);
		return 1 == count;
	}
	
	// 조회수 1+
	public Boolean updateHit(Long no) {
		int count = sqlSession.update("board.updateHit", no);
		return 1 == count;
	}
	
	// 삭제 (정말 삭제하는 것이 아니라 비활성화 상태로 만들어둔다.)
	public Boolean delete(Long no) {
		int count = sqlSession.delete("board.delete", no);
		return 1 == count;
	}
}
