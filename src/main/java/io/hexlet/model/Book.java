package io.hexlet.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Book {
    private Long id;
    private String title;
    private String author;
    private Date publishedDate;
    private String isbn;
}
