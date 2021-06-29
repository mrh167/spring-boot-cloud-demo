package com.msc.fix.lisa.mybatiscn.dto;

public class MapperElement {

	private String id;

	private String content;

	private String comment;

	private ElementPosition location;

	private boolean existed;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ElementPosition getLocation() {
		return location;
	}

	public void setLocation(ElementPosition location) {
		this.location = location;
	}

	public boolean isExisted() {
		return existed;
	}

	public void setExisted(boolean existed) {
		this.existed = existed;
	}


	public static MapperElement builder() {
		return new MapperElement();
	}

	public MapperElement id(String id) {
		this.id = id;
		return this;
	}

	public MapperElement comment(String comment) {
		this.comment = comment;
		return this;
	}

	public MapperElement content(String content) {
		this.content = content;
		return this;
	}

	public MapperElement location(ElementPosition location) {
		this.location = location;
		return this;
	}

	public MapperElement build() {
		return this;
	}
}
