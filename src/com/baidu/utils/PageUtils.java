package com.baidu.utils;



/*@名称：分页工具类
 *说明：TOOD
 *时间：2019/10/29 10:51
 *参数：
 *返回：
 */
public class PageUtils {

    //当前页
    private int currentPage;
    //上一页
    private int prevPage;
    //下一页
    private int nextPage;
    //尾页
    private int lastPage;
    //页面条数
    private int pageSize;
    //总条数
    private int count;

    public PageUtils(String page, int pageSize, int count ){

        this.pageSize=pageSize;
        this.count=count;
        initCurrentPage(page);
        initLastPage();
        initPrevPage();
        initNextPage();
    }

    private void initNextPage() {
        nextPage=currentPage==lastPage ?lastPage:currentPage+1;
    }

    private void initPrevPage() {
        prevPage=currentPage==1 ? 1:currentPage-1;
    }

    private void initLastPage() {

        lastPage=count/pageSize;
        if (count%pageSize!=0){
            lastPage++;
        }
    }

    private void initCurrentPage(String page) {

        if (null==page || "".equals(page)){
            page="1";
        }
        //计算当前页
        currentPage=Integer.parseInt(page);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
