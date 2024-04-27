package org.geekhub.application.pagination;

public class Pagination {

    private Pagination() {
    }

    public static int getOffset(int pageNum, int pageSize) {
        return (pageNum - 1) * pageSize;
    }
}
