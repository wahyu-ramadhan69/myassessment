package com.recruitment.myassessment.util;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public class TransformDataPaging {

        public Map<String, Object> mapDataPaging(Map<String, Object> mapz, Page page) {
                mapz.put("content", page.getContent());
                mapz.put("currentPage", page.getNumber());
                // mapz.put("totalItems",page.getTotalElements());
                mapz.put("totalPages", page.getTotalPages());
                // mapz.put("sort",pageKategoriBarang.getSort());
                mapz.put("numberOfElements", page.getNumberOfElements());

                return mapz;
        }

        public Map<String, Object> mapDataPaging(Map<String, Object> mapz, Page page, List list) {
                mapz.put("content", list);
                mapz.put("currentPage", page.getNumber());
                // mapz.put("totalItems",page.getTotalElements());
                mapz.put("totalPages", page.getTotalPages());
                // mapz.put("sort",pageKategoriBarang.getSort());
                mapz.put("numberOfElements", page.getNumberOfElements());

                return mapz;

        }
}
