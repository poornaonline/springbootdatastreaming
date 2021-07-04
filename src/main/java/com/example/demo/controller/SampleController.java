package com.example.demo.controller;

import com.example.demo.model.UserDetail;
import com.example.demo.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class SampleController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private SampleRepository sampleRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String sampleController() {
        return "Sample Controller";
    }

    @RequestMapping(value = "/userdetail/list/csv", method = RequestMethod.GET)
    public void generateCSVUsingList(HttpServletResponse response) {
        response.addHeader("Content-Type", "application/csv");
        response.addHeader("Content-Disposition", "attachment; filename=user_details.csv");
        response.setCharacterEncoding("UTF-8");
        try {
            List<UserDetail> userDetailsStream = sampleRepository.getAllOldWay();
            PrintWriter out = response.getWriter();
            userDetailsStream.forEach(userDetail -> {
                out.write(userDetail.toString());
                out.write("\n");
                entityManager.detach(userDetail);
            });
            out.flush();
            out.close();
        } catch (IOException ix) {
            throw new RuntimeException("There is an error while downloading user_details.csv", ix);
        }
    }

    @RequestMapping(value = "/userdetail/stream/csv", method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public void generateCSVUsingStream(HttpServletResponse response) {
        response.addHeader("Content-Type", "application/csv");
        response.addHeader("Content-Disposition", "attachment; filename=user_details.csv");
        response.setCharacterEncoding("UTF-8");
        try (Stream<UserDetail> userDetailsStream = sampleRepository.getAll();) {
            PrintWriter out = response.getWriter();
            userDetailsStream.forEach(userDetail -> {
                out.write(userDetail.toString());
                out.write("\n");
                entityManager.detach(userDetail);
            });
            out.flush();
            out.close();
            userDetailsStream.close();
        } catch (IOException ix) {
            throw new RuntimeException("There is an error while downloading user_details.csv", ix);
        }
    }
}
