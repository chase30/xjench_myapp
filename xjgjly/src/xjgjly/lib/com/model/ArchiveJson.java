package xjgjly.lib.com.model;

import java.io.Serializable;
import java.util.List;

public class ArchiveJson implements Serializable{
	
	private List<DedeArchivesEntity> dedeArchivelist;
	private int pageTotal;
	public List<DedeArchivesEntity> getDedeArchivelist() {
		return dedeArchivelist;
	}
	public void setDedeArchivelist(List<DedeArchivesEntity> dedeArchivelist) {
		this.dedeArchivelist = dedeArchivelist;
	}
	public int getPageTotal() {
		return pageTotal;
	}
	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
	public ArchiveJson(List<DedeArchivesEntity> dedeArchivelist, int pageTotal) {
		super();
		this.dedeArchivelist = dedeArchivelist;
		this.pageTotal = pageTotal;
	}
}
