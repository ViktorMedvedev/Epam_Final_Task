package main.java.by.epam.tattoo.service;

import main.java.by.epam.tattoo.entity.Entity;
import main.java.by.epam.tattoo.util.ConstantHeap;
import main.java.by.epam.tattoo.util.JspParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;


public class PaginationUtil<T extends Entity> {
    private ArrayList<T> showRecordsOnPage(ArrayList<T> list, int page, int recordsPerPage) {
        int listLength = list.size();
        int begin = (page - 1) * recordsPerPage;
        int end = page * recordsPerPage -1;
        ArrayList<T> resultList = new ArrayList<>();
        for (int i = 0; i < listLength; i++) {
            if (i >= begin && i <= end) {
                resultList.add(list.get(i));
            }
        }
        return resultList;
    }
    public ArrayList<T> executePagination(HttpServletRequest request, ArrayList<T> list){
        HttpSession session = request.getSession();
        int page = 1;
        int recordsPerPage = ConstantHeap.RECORDS_PER_PAGE;
        int noOfRecords = list.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (request.getParameter(JspParam.PAGE) != null) {
            page = Integer.parseInt(request.getParameter(JspParam.PAGE));
        }
        session.setAttribute(JspParam.NUMBER_OF_PAGES, noOfPages);
        request.setAttribute(JspParam.PAGE, page);
        return showRecordsOnPage(list, page, recordsPerPage);

    }
}
