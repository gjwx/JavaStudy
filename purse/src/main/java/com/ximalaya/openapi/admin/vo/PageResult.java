package com.ximalaya.openapi.admin.vo;

/**
 * 〈返回结果〉
 *
 * @author gongjiawei
 * @date 2025/9/11 下午4:38
 */
import java.util.Collections;
import java.util.List;

public class PageResult<T> {
    private List<T> dataList;
    private Integer totalNum;
    private Integer totalPage;
    private Integer currentPage;
    private Integer pageSize;
    public static final PageResult EMPTY_RESULT = new PageResult();

    public PageResult() {
        this.dataList = Collections.emptyList();
        this.totalNum = 0;
        this.totalPage = 0;
        this.currentPage = 0;
        this.pageSize = 0;
    }

    public static <T> PageResultBuilder<T> builder() {
        return new PageResultBuilder();
    }

    public List<T> getDataList() {
        return this.dataList;
    }

    public Integer getTotalNum() {
        return this.totalNum;
    }

    public Integer getTotalPage() {
        return this.totalPage;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof PageResult)) {
            return false;
        } else {
            PageResult<?> other = (PageResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label71: {
                    Object this$totalNum = this.getTotalNum();
                    Object other$totalNum = other.getTotalNum();
                    if (this$totalNum == null) {
                        if (other$totalNum == null) {
                            break label71;
                        }
                    } else if (this$totalNum.equals(other$totalNum)) {
                        break label71;
                    }

                    return false;
                }

                Object this$totalPage = this.getTotalPage();
                Object other$totalPage = other.getTotalPage();
                if (this$totalPage == null) {
                    if (other$totalPage != null) {
                        return false;
                    }
                } else if (!this$totalPage.equals(other$totalPage)) {
                    return false;
                }

                label57: {
                    Object this$currentPage = this.getCurrentPage();
                    Object other$currentPage = other.getCurrentPage();
                    if (this$currentPage == null) {
                        if (other$currentPage == null) {
                            break label57;
                        }
                    } else if (this$currentPage.equals(other$currentPage)) {
                        break label57;
                    }

                    return false;
                }

                Object this$pageSize = this.getPageSize();
                Object other$pageSize = other.getPageSize();
                if (this$pageSize == null) {
                    if (other$pageSize != null) {
                        return false;
                    }
                } else if (!this$pageSize.equals(other$pageSize)) {
                    return false;
                }

                Object this$dataList = this.getDataList();
                Object other$dataList = other.getDataList();
                if (this$dataList == null) {
                    if (other$dataList == null) {
                        return true;
                    }
                } else if (this$dataList.equals(other$dataList)) {
                    return true;
                }

                return false;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof PageResult;
    }
    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $totalNum = this.getTotalNum();
        result = result * 59 + ($totalNum == null ? 43 : $totalNum.hashCode());
        Object $totalPage = this.getTotalPage();
        result = result * 59 + ($totalPage == null ? 43 : $totalPage.hashCode());
        Object $currentPage = this.getCurrentPage();
        result = result * 59 + ($currentPage == null ? 43 : $currentPage.hashCode());
        Object $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
        Object $dataList = this.getDataList();
        result = result * 59 + ($dataList == null ? 43 : $dataList.hashCode());
        return result;
    }
    @Override
    public String toString() {
        return "PageResult(dataList=" + this.getDataList() + ", totalNum=" + this.getTotalNum() + ", totalPage=" + this.getTotalPage() + ", currentPage=" + this.getCurrentPage() + ", pageSize=" + this.getPageSize() + ")";
    }

    public PageResult(List<T> dataList, Integer totalNum, Integer totalPage, Integer currentPage, Integer pageSize) {
        this.dataList = dataList;
        this.totalNum = totalNum;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public static class PageResultBuilder<T> {
        private List<T> dataList;
        private Integer totalNum;
        private Integer totalPage;
        private Integer currentPage;
        private Integer pageSize;

        PageResultBuilder() {
        }

        public PageResultBuilder<T> dataList(List<T> dataList) {
            this.dataList = dataList;
            return this;
        }

        public PageResultBuilder<T> totalNum(Integer totalNum) {
            this.totalNum = totalNum;
            return this;
        }

        public PageResultBuilder<T> totalPage(Integer totalPage) {
            this.totalPage = totalPage;
            return this;
        }

        public PageResultBuilder<T> currentPage(Integer currentPage) {
            this.currentPage = currentPage;
            return this;
        }

        public PageResultBuilder<T> pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public PageResult<T> build() {
            return new PageResult(this.dataList, this.totalNum, this.totalPage, this.currentPage, this.pageSize);
        }
        @Override
        public String toString() {
            return "PageResult.PageResultBuilder(dataList=" + this.dataList + ", totalNum=" + this.totalNum + ", totalPage=" + this.totalPage + ", currentPage=" + this.currentPage + ", pageSize=" + this.pageSize + ")";
        }
    }
}
