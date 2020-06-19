package com.seemmo.airecheck.imgstore.entity;

import cn.hutool.core.util.HashUtil;
import org.springframework.beans.BeanUtils;

public class ImageEntity {
	private String imageId;

	private String filepath;
	
	private String fileurl;

	private long storeTime;

	private String type;

	private byte[] imageData;

	public ImageEntity() {
		
	}
	
	public ImageEntity(ImageEntity entity) {
		BeanUtils.copyProperties(entity, this);
	}
	

	public String getImageId() {
		return imageId;
	}

	public ImageEntity setImageId(String imageId) {
		this.imageId = imageId;
		return this;
	}

	public String getFilepath() {
		return filepath;
	}

	public ImageEntity setFilepath(String filepath) {
		this.filepath = filepath;
		return this;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public ImageEntity setImageData(byte[] imageData) {
		this.imageData = imageData;
		return this;
	}

	public long getStoreTime() {
		return storeTime;
	}

	public ImageEntity setStoreTime(long storeTime) {
		
		this.storeTime = storeTime;
		return this;
	}

	public String getType() {
		return type;
	}

	public ImageEntity setType(String type) {
		this.type = type;
		return this;
	}

	public String getFileurl() {
		return fileurl;
	}

	public ImageEntity setFileurl(String fileurl) {
		this.fileurl = fileurl;
		return this;
	}
	
	@Override
    public boolean equals(Object obj) {
		if(obj==null||!this.getClass().isInstance(obj)||this.getImageId()==null) {
            return false;
        }
		ImageEntity entity=(ImageEntity)obj;
		return this.getImageId().equals(entity.getImageId());
	}
	
	public long hashcode() {
		int len=this.getImageData().length;
		return HashUtil.tianlHash(this.getImageId()+"_"+len+"_"+this.getImageData()[0]+"_"+this.getImageData()[len-1]);
	}
}
