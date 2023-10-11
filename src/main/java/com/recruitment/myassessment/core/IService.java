package com.recruitment.myassessment.core;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IService<T> {

    public ResponseEntity<Object> save(T t, HttpServletRequest request);// 001-010

    public ResponseEntity<Object> update(Long id, T t, HttpServletRequest request) throws Exception;// 011-020

    public ResponseEntity<Object> delete(Long id, HttpServletRequest request);// 021-030

    public ResponseEntity<Object> saveBatch(List<T> lt, HttpServletRequest request);// 031-040

    public ResponseEntity<Object> findById(Long id, HttpServletRequest request);// 041-050

    public ResponseEntity<Object> findByPage(Integer page, Integer size, String columFirst, String valueFirst,
            HttpServletRequest request);// 051-060

    public ResponseEntity<Object> findAllByPage(Integer page, Integer size, HttpServletRequest request);// 061-070

    public ResponseEntity<Object> findAll(HttpServletRequest request);// 071-080

    public ResponseEntity<Object> dataToExport(MultipartFile multipartFile, HttpServletRequest request);// 081-090
}