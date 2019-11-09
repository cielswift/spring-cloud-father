package com.ciel.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name="ssh_application")

@ApiModel(description="用户实体")

//@SQLDelete(sql = "update ssh_application set deleted = 1 where id = ?") //逻辑删除
@Where(clause = "deleted = 0")
public class AppEntity  extends BaseEntity{

    @ApiModelProperty("用户姓名")
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Transient  //表示数据库没有这个字段
    private String nun;

    @Temporal(TemporalType.TIMESTAMP) //日期类型指定精确的时间
    @Transient
    private Date nda;

}
