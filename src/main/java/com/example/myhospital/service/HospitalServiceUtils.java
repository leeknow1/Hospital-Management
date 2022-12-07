package com.example.myhospital.service;


import com.example.myhospital.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public class HospitalServiceUtils {

    public static List<Integer> getThreePages(int page, int totalP) {
        List<Integer> pagesList = new ArrayList<>();
        if (page == 1) {
            pagesList.add(1);
            pagesList.add(2);
            pagesList.add(3);
        }

        if (page > 1) {
            pagesList.add(page - 1);
            pagesList.add(page);
            pagesList.add(page + 1);
        }

        if (page == (totalP - 1) || page == totalP) {
            pagesList.clear();
            pagesList.add(totalP - 2);
            pagesList.add(totalP - 1);
            pagesList.add(totalP);
        }

        if(page > totalP) {
            pagesList.clear();
        }

        return pagesList;
    }

    public static List<Integer> getTotalPageList(int totalPages){
        List<Integer> totalPagesList = new ArrayList<>();
        int j=1;
        for(int i=0; i<totalPages; i++){
            totalPagesList.add(i, j);
            j++;
        }
        return totalPagesList;
    }

    public static String getEverything(Page<?> findPost, int page, Model model, String pageName, String view){

        int totalP = findPost.getTotalPages();
        long totalItems = findPost.getTotalElements();
        List<?> list = findPost.getContent();

        model.addAttribute("pageList", getTotalPageList(totalP));
        model.addAttribute("threePage", getThreePages(page, totalP));

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalP);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("pageName", pageName);

        model.addAttribute("list", list);

        return view;
    }

    public static Pageable getPages(int page, int size, String sort){
        return PageRequest.of(page-1, size, Sort.by(Sort.Direction.ASC, sort ));
    }

    public static void sortingDoctors(List<DoctorsEntity> list){
        list.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
    }

    public static void sortingPatients(List<PatientEntity> list){
        list.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
    }

    public static void sortingMedicine(List<MedicineEntity> list){
        list.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
    }

    public static void sortingRoomsByType(List<RoomsEntity> list){
        list.sort((o1, o2) -> (int) (o1.getRoomNumber() - o2.getRoomNumber()));
    }

    public static void sortingInpatients(List<InpatientEntity> list){
        list.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
    }
}
