package com.ximalaya.openapi.admin.model.purchase;

/**
 */
public class AttachInfo {

  /**
     * 附件名称
     */
  private String name;
  /**
     * 附件链接
     */
  private String url;

  public AttachInfo() {
  }

  public AttachInfo(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrl() {
    return this.url;
  }

  @Override
  public String toString() {
    return "AttachInfo(" +"name=" + this.name + ", " + "url=" + this.url + ")";
  }

  @Override
  public int hashCode() {
    int hash = 1;
    hash = 31 * hash + (this.name == null ? 0 : this.name.hashCode());
    hash = 31 * hash + (this.url == null ? 0 : this.url.hashCode());
    return hash;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof AttachInfo)) {
      return false;
    }
    AttachInfo that = (AttachInfo) other;
    if (this.name == null) {
      if (that.name != null) {
          return false;
      }
    } else if (!this.name.equals(that.name)) {
      return false;
    }
    if (this.url == null) {
      if (that.url != null)
        return false;
    } else if (!this.url.equals(that.url)) {
      return false;
    }
    return true;
  }
}