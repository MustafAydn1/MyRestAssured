package goRest.model;

import java.util.List;

public class Posts {

           private int total;
           private int pages;
           private int page;
           private int limit;

           List<postData> dataList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<postData> getDataList() {
        return dataList;
    }

    public void setDataList(List<postData> dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "total=" + total +
                ", pages=" + pages +
                ", page=" + page +
                ", limit=" + limit +
                ", dataList=" + dataList +
                '}';
    }
}
