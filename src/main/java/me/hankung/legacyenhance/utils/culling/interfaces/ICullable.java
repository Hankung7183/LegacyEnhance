package me.hankung.legacyenhance.utils.culling.interfaces;

public interface ICullable {

	public void setTimeout();
	public boolean isForcedVisible();
	
	public void setCulled(boolean value);
	public boolean isCulled();
	
	public void setOutOfCamera(boolean value);
	public boolean isOutOfCamera();
	
}
