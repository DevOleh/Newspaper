package com.example.oleg.newspaper.Filters;

public class ArticleFilter {

    private ArticleFilter(){};

    private static ArticleFilter articleFilterInstance = null;
    private String search;
    private String fromDate;
    private String toDate;
    private String sortOrder;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public static synchronized ArticleFilter getArticleFilterInstance(){
        if(articleFilterInstance == null)
            synchronized (ArticleFilter.class){
                if(articleFilterInstance == null){
                    articleFilterInstance = new ArticleFilter();
                }
            }

        return articleFilterInstance;
    }




}
