package org.es.study.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @program: UsefullESSearchSkill
 * @description: Book实体类
 * @author: 赖键锋
 * @create: 2018-08-21 20:44
 **/
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Book {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String id;

    private String title;

    private List<String> authors;

    private String summary;

    private String publish_date;

    private Integer num_reviews;

    private String publisher;

}
