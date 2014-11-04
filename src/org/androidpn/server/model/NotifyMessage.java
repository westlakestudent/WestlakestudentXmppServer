package org.androidpn.server.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * NotifyMessage
 * 
 * @author chendong 2014年10月31日 下午3:18:37
 * @version 1.0.0
 * 
 */

@Entity
@Table(name = "notify_msg")
public class NotifyMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "imeis")
	private String imeis;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "createDate")
	private Date createDate = new Date();

	
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImeis() {
		return imeis;
	}

	public void setImeis(String imeis) {
		this.imeis = imeis;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "id : " + id + " ;\ntitle : " + title + " ;\nmessage : " + message
				+ " ;\nimeis : " + imeis + " ;\nremark : " + remark;
	}
	
	
	

}
