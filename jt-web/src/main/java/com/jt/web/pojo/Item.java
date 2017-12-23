package com.jt.web.pojo;

import com.jt.common.po.BasePojo;

public class Item extends BasePojo {

	private Long id; // 商品的编号
	private String title; // 商品名称
	private String sellPoint; // 商品卖点
	private Integer price; // 商品单价
	private Integer num; // 商品库存
	private String barcode; // 商品条形码
	private String image; // 商品图片
	private Long cid; // 商品分类
	private Integer status; // 商品状态1正常2下架3删除
	private String[] images;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String[] getImages() {

		if (image != null) {
			return image.split(",");
		}
		return null;
	}

}
