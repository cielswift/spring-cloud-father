package com.ciel.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="ssh_user")
//@SQLDelete(sql = "update ssh_user set deleted = 1 where id = ?") //逻辑删除
@Where(clause = "deleted = 0")
public class UserEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;

    @JsonIgnoreProperties(value = {"user"}) //不序列化AppEntity的user属性,防止溢出
    @OneToMany(mappedBy = "user",fetch =FetchType.LAZY,cascade = {CascadeType.REMOVE})
    //mappedBy对方维护关联关系, cascade级联删除,删除多端的所有数据
    @OrderBy("createDate ASC")
    private List<AppEntity> apps = new ArrayList<>();

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "sex")
    private Byte sex;

    @Column(name = "head_Sculpture_path")
    private String headSculpturePath;

    @JsonIgnoreProperties(value = {"users"})  //不序列化LanguageEntity的users属性,防止溢出
    @OrderBy("createDate desc")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="ssh_user_lanhuager",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="language_id",referencedColumnName = "id"))
    //joinColumns 此表在中间表中对应的外键 ; referencedColumnName这个外键指向本表(或对应表)的主键,不写也可以
    // inverseJoinColumns 另一个表对应的中间表的外键;
    List<LanguageEntity> langs = new ArrayList<>();


    @JsonIgnoreProperties(value = {"users"})  //不序列化LanguageEntity的users属性,防止溢出
    @OrderBy("createDate desc")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="ssh_user_role",joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
    List<RoleEntity> roles = new ArrayList<>();

}
