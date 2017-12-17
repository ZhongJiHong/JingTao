package com.jt.manage.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jt.common.po.BasePojo;

@Table(name = "tb_item_cat")
@JsonIgnoreProperties(ignoreUnknown = true)	// 忽略未知的属性 
public class ItemCat extends BasePojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 商品分类的id
	@Column(name = "parent_id")
	private Long parentId; // 上级分类的id
	private String name; // 商品分类的名称
	private Integer status; // 分类状态1正常2删除
	private Integer sortOrder; // 商品分类序号
	private Boolean isParent; // 是否为上级分类

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
	// EasyUI富客户端Tree添加的get方法
	public String getText() {
		return name;
	}

	public String getState() {
		return isParent ? "closed" : "open";
	}
}
