package com.company.maven.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import java.util.Date;

import org.springframework.data.annotation.Id;


@Entity
@Table(name="POST")
public class Post {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="POST_ID")
	Integer postId;
	
	@Column(name="TITLE")
	String title;
	
	@Column(name="POST_DATE")
	Date postDate;
	@jakarta.persistence.Id
	private Long id;

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
