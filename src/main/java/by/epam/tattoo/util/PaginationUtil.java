package main.java.by.epam.tattoo.util;

import main.java.by.epam.tattoo.entity.Entity;
import main.java.by.epam.tattoo.util.ConstantHeap;
import main.java.by.epam.tattoo.util.JspParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PaginationUtil<T extends Entity> {
    private static final String NUMBER_REGEX = "^\\d+$";

    private ArrayList<T> showRecordsOnPage(ArrayList<T> list, int page, int recordsPerPage) {
        int listLength = list.size();
        int begin = (page - 1) * recordsPerPage;
        int end = page * recordsPerPage - 1;
        ArrayList<T> resultList = new ArrayList<>();
        for (int i = 0; i < listLength; i++) {
            if (i >= begin && i <= end) {
                resultList.add(list.get(i));
            }
        }
        return resultList;
    }

    public ArrayList<T> executePagination(HttpServletRequest request, ArrayList<T> list) {
        HttpSession session = request.getSession();
        String pageString = request.getParameter(JspParam.PAGE);
        int page = 1;
        int recordsPerPage = ConstantHeap.RECORDS_PER_PAGE;
        int noOfRecords = list.size();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        if (pageString != null) {
            Pattern pattern = Pattern.compile(NUMBER_REGEX);
            Matcher matcher = pattern.matcher(pageString);
            if (matcher.matches()) {
                page = Integer.parseInt(pageString);
                if (page > noOfPages) {
                    page = noOfPages;
                }
            }else {
                page = 1;
            }
        }
        session.setAttribute(JspParam.NUMBER_OF_PAGES, noOfPages);
        request.setAttribute(JspParam.PAGE, page);
        return showRecordsOnPage(list, page, recordsPerPage);

    }
}
