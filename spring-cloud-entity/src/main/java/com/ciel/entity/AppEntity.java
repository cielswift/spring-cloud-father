package com.ciel.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Table(name="ssh_application")

//@SQLDelete(sql = "update ssh_application set deleted = 1 where id = ?") //逻辑删除
@Where(clause = "deleted = 0")
public class AppEntity  extends BaseEntity{

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
