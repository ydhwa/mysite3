package com.cafe24.mysite.vo;

public class FileVo {
	private Long boardNo;
	
	private String saveName;
	private String originalName;
	private String extName;
	private String path;
	public Long getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(Long boardNo) {
		this.boardNo = boardNo;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getExtName() {
		return extName;
	}
	public void setExtName(String extName) {
		this.extName = extName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	@Override
	public String toString() {
		return "FileVo [boardNo=" + boardNo + ", saveName=" + saveName + ", originalName=" + originalName
				+ ", extName=" + extName + ", path=" + path + "]";
	}
}
