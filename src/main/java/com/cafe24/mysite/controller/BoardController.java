package com.cafe24.mysite.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.mysite.security.AuthUser;
import com.cafe24.mysite.security.SecurityUser;
import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
// 매 페이지마다 수정이나 삭제 등의 작업을 거친 후에 목록으로 리다이렉트 되면
// 그 전에 자신이 방문했던 페이지로 이동하도록 구현해야 한다.(UX 관점에서)
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	// 게시글 리스트 조회 / 검색
	@RequestMapping(value = {"/list", ""})
	public String list(
			Model model,
			@RequestParam(value = "p", defaultValue = "1") int currPage,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		
		model.addAttribute("map", boardService.getListAndPager(currPage, keyword));
		
		return "board/list";
	}
	
	
	// 게시글 상세 조회
	@RequestMapping("/view/{no}")
	public String view(
			@PathVariable("no") Long no,
			HttpSession session,
			Model model) {
		
		BoardVo boardVo = boardService.view(no, "view", (List)session.getAttribute("viewList"));
		model.addAttribute("vo", boardVo);
		
		// 당사자일 경우 수정이나 삭제 작업을 할 수도 있으니 세션에 정보를 등록해둔다.
		// 보기만 할거면 비효율적인 처리기는 하다.
		if(session.getAttribute("authUser") != null && ((UserVo) session.getAttribute("authUser")).getNo() == boardVo.getUserNo()) {
			// 이미지 삭제 or 업데이트 위함
			session.setAttribute("boardNo", no);
		}
		
		return "board/view";
	}
	
	
	// 글 작성 (일반 게시글)
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(
			@RequestParam(value = "groupno", defaultValue = "-1") int groupNo,
			@RequestParam(value = "orderno", defaultValue = "-1") int orderNo,
			@RequestParam(value = "depth", defaultValue = "-1") int depth,
			Model model) {
		
		model.addAttribute("groupNo", groupNo);
		model.addAttribute("orderNo", orderNo);
		model.addAttribute("depth", depth);
		
		return "board/write";
	}
	
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(
			@ModelAttribute BoardVo boardVo,
			HttpSession session) {
		
		boardVo.setUserNo(((UserVo) session.getAttribute("authUser")).getNo());
		boardService.writeBoard(boardVo);
		return "redirect:/board/list";
	}
	

	// 게시글 수정
	@RequestMapping(value = "/update/{no}", method = RequestMethod.GET)
	public String update(
			Model model,
			@PathVariable("no") Long no,
			HttpSession session) {
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		BoardVo boardVo = boardService.view(no, "update", null);
		if(authUser.getNo() != boardVo.getUserNo()) {	// 수정하려는 사람 != 작성한 사람
			return "redirect:/board/list";
		}
	
		model.addAttribute("vo", boardVo);
		
		return "/board/update";
	}
	@RequestMapping(value = "/update/{no}", method = RequestMethod.POST)
	public String update(
			HttpSession session,
			@PathVariable("no") Long no,
			@ModelAttribute BoardVo boardVo,
			@AuthUser SecurityUser securityUser) {
		
		// 글의 주인이 아니면 수정 권한이 없다.
		// update form과 view에서 처리하긴 한다.
		BoardVo thisBoardVo = boardService.view(no, "", null);
		if(securityUser.getNo() != thisBoardVo.getUserNo()) {
			return "redirect:/board/list";
		}
		
		boardVo.setNo(no);
		boardService.updateBoard(boardVo);
		
		// 세션에 설정해놨던 게시글 번호 속성을 삭제한다.
		session.removeAttribute("boardNo");
		
		return "redirect:/board/list";
	}
	
	// 게시글 삭제
	@RequestMapping(value = "/delete/{no}")
	public String delete(
			HttpSession session,
			@PathVariable("no") Long no) {
		
		boardService.deleteBoard(no);
		
		// 세션에 설정해놨던 게시글 번호 속성 삭제한다.
		session.removeAttribute("boardNo");
		
		return "redirect:/board/list";
	}
	
	
	// 파일 업로드
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	@ResponseBody
	public String multiplePhotoUpload(
			HttpSession session,
			@RequestHeader("file-name") String fileName,
			@RequestHeader("file-size") long fileSize,
			HttpServletRequest request) throws IOException {
		
		InputStream is = request.getInputStream();

		return boardService.fileUpload((Long)session.getAttribute("boardNo"), fileName, fileSize, request.getContextPath(), is);
	}
}
