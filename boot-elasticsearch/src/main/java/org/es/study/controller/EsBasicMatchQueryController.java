package org.es.study.controller;

import org.es.study.config.Response;
import org.es.study.domain.Book;
import org.es.study.service.BasicMatchQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/8
 * Time: 16:51
 * Description: No Description
 */
@RestController
@RequestMapping("/basicMatch")
public class EsBasicMatchQueryController {

    @Autowired
    private BasicMatchQueryService basicMatchQueryService;

    /**
     * query的全文检索
     * @param query
     * @return
     * 测试：http://localhost:8080/basicmatch/multimatch?query=guide
     */
    @RequestMapping("/multiMatch")
    public Response<List<Book>> multiMatch(@RequestParam(value = "query",required = true) String query) {
        return basicMatchQueryService.multiMatch(query);
    }


}
