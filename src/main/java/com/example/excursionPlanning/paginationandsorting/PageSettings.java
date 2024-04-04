package com.example.excursionPlanning.paginationandsorting;

import lombok.Data;

@Data
public class PageSettings {

    private int page = 0;

    private int size = 20;

    public PageSettings(Integer page, Integer size) {
        if (page != null) {
            this.page = page;
        }
        if (size != null) {
            this.size = size;
        }
    }
}
