package com.TallerMecanicoSpring.TM.dto;

import java.util.List;

public class PaginatedResponse<T> {
    private List<T> data;
    private int totalPages;
    private long totalElements;
    // Otros campos según sea necesario

    // Getters y Setters

    public List<T> getData(){
        return data;
    }

    public void setData( List<T> data){
        this.data = data;
    }

    public int getTotalPages(){
        return totalPages;
    }

    public void setTotalPages( int totalPages){
        this.totalPages = totalPages;
    }

    public long getTotalElements(){
        return totalElements;
    }

    public void setTotalElements( long totalElements){
        this.totalElements = totalElements;
    }
}
