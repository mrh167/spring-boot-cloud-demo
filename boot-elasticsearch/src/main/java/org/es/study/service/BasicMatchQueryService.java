package org.es.study.service;

import org.es.study.config.Response;
import org.es.study.domain.Book;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/11
 * Time: 15:10
 * Description: No Description
 */
public interface BasicMatchQueryService {
    Response<List<Book>> multiMatch(String query);
}
