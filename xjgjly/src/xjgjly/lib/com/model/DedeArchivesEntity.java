package xjgjly.lib.com.model;

import java.util.ArrayList;

/**   
 * @Title: Entity
 * @Description: 封面图片
 * @author zhangdaihao
 * @date 2014-12-11 18:27:43
 * @version V1.0   
 *
 */
public class DedeArchivesEntity implements java.io.Serializable {
	/**id*/
	private java.lang.Integer id;
	/**typeid*/
	private java.lang.Integer typeid;
	/**时间顺序*/
	private java.lang.Integer sortrank;
	/**所属模型*/
	private java.lang.Integer channel;
	/**封面标题*/
	private java.lang.String title;
	/**封面路径*/
	private java.lang.String litpic;
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(java.lang.Integer typeid) {
		this.typeid = typeid;
	}
	public java.lang.Integer getSortrank() {
		return sortrank;
	}
	public void setSortrank(java.lang.Integer sortrank) {
		this.sortrank = sortrank;
	}
	public java.lang.Integer getChannel() {
		return channel;
	}
	public void setChannel(java.lang.Integer channel) {
		this.channel = channel;
	}
	public java.lang.String getTitle() {
		return title;
	}
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	public java.lang.String getLitpic() {
		return litpic;
	}
	public void setLitpic(java.lang.String litpic) {
		this.litpic = litpic;
	}
	
}
