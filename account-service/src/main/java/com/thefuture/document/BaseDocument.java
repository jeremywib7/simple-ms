package com.thefuture.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

import static com.thefuture.service.GlobalService.generateId;

@Getter
@Setter
public class BaseDocument {
    @Id
    @JsonIgnore
    private String id = generateId();

    private String publicId;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    private boolean deleted;


}
