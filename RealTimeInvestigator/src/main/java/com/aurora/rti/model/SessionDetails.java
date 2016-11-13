package com.aurora.rti.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="session_details")
public class SessionDetails implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long SID;
	private String sessionId;
	private String sessionCreatedTime;
	private Long sessionAccessCount;
	private String lastAccessTime;
	private String heartBeatTime;
	private String status;
	private String cssStatus;
	private String jsStatus;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="SID")
	public Long getSID() {
		return SID;
	}
	public void setSID(Long sID) {
		SID = sID;
	}
	
	@Column(name="session_id", nullable=true, length=100)
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Column(name="session_created_time", nullable=true, length=100)
	public String getSessionCreatedTime() {
		return sessionCreatedTime;
	}
	public void setSessionCreatedTime(String sessionCreatedTime) {
		this.sessionCreatedTime = sessionCreatedTime;
	}
	
	@Column(name="session_accessed_count", nullable=true, length=100)
	public Long getSessionAccessCount() {
		return sessionAccessCount;
	}
	public void setSessionAccessCount(Long sessionAccessCount) {
		this.sessionAccessCount = sessionAccessCount;
	}
	
	@Column(name="last_access_time", nullable=true, length=100)
	public String getLastAccessTime() {
		return lastAccessTime;
	}
	public void setLastAccessTime(String lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	
	@Column(name="status", nullable=true, length=20)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name="heartBeatTime", nullable=true, length=100)
	public String getHeartBeatTime() {
		return heartBeatTime;
	}
	public void setHeartBeatTime(String heartBeatTime) {
		this.heartBeatTime = heartBeatTime;
	}
	
	@Column(name="cssStatus", nullable=true, length=20)
	public String getCssStatus() {
		return cssStatus;
	}
	public void setCssStatus(String cssStatus) {
		this.cssStatus = cssStatus;
	}
	
	@Column(name="JsStatus", nullable=true, length=20)
	public String getJsStatus() {
		return jsStatus;
	}
	public void setJsStatus(String jsStatus) {
		this.jsStatus = jsStatus;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((SID == null) ? 0 : SID.hashCode());
		result = prime * result + ((cssStatus == null) ? 0 : cssStatus.hashCode());
		result = prime * result + ((heartBeatTime == null) ? 0 : heartBeatTime.hashCode());
		result = prime * result + ((jsStatus == null) ? 0 : jsStatus.hashCode());
		result = prime * result + ((lastAccessTime == null) ? 0 : lastAccessTime.hashCode());
		result = prime * result + ((sessionAccessCount == null) ? 0 : sessionAccessCount.hashCode());
		result = prime * result + ((sessionCreatedTime == null) ? 0 : sessionCreatedTime.hashCode());
		result = prime * result + ((sessionId == null) ? 0 : sessionId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SessionDetails other = (SessionDetails) obj;
		if (SID == null) {
			if (other.SID != null)
				return false;
		} else if (!SID.equals(other.SID))
			return false;
		if (cssStatus == null) {
			if (other.cssStatus != null)
				return false;
		} else if (!cssStatus.equals(other.cssStatus))
			return false;
		if (heartBeatTime == null) {
			if (other.heartBeatTime != null)
				return false;
		} else if (!heartBeatTime.equals(other.heartBeatTime))
			return false;
		if (jsStatus == null) {
			if (other.jsStatus != null)
				return false;
		} else if (!jsStatus.equals(other.jsStatus))
			return false;
		if (lastAccessTime == null) {
			if (other.lastAccessTime != null)
				return false;
		} else if (!lastAccessTime.equals(other.lastAccessTime))
			return false;
		if (sessionAccessCount == null) {
			if (other.sessionAccessCount != null)
				return false;
		} else if (!sessionAccessCount.equals(other.sessionAccessCount))
			return false;
		if (sessionCreatedTime == null) {
			if (other.sessionCreatedTime != null)
				return false;
		} else if (!sessionCreatedTime.equals(other.sessionCreatedTime))
			return false;
		if (sessionId == null) {
			if (other.sessionId != null)
				return false;
		} else if (!sessionId.equals(other.sessionId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "SessionDetails [SID=" + SID + ", sessionId=" + sessionId + ", sessionCreatedTime=" + sessionCreatedTime
				+ ", sessionAccessCount=" + sessionAccessCount + ", lastAccessTime=" + lastAccessTime
				+ ", heartBeatTime=" + heartBeatTime + ", status=" + status + ", cssStatus=" + cssStatus + ", jsStatus="
				+ jsStatus + "]";
	}

	
}
