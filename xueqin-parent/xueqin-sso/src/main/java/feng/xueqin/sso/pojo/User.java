/*
 * @(#)User.java V2.0 2016年6月29日
 * 百联集团	版权所有
 * 
 * 文件描述...
 *
 * @Title: User.java 
 * @Package feng.xueqin.sso.pojo 
 * @author qinxf
 * @date 2016年6月29日 上午10:29:55
 * @version V2.0
 * 历史版本：
 * 	1. 【2016年6月29日】 创建文件   by qinxf
 */
package feng.xueqin.sso.pojo;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

/** 
 * TODO 对该类总结性的描述
 * 
 * @ClassName: User 
 * @author qinxf
 * @date 2016年6月29日 上午10:29:55
 * @version V2.0 
 *  
 */
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Length(max=20,min=6,message="用户名的长度必须在6-20字之间")
    private String username;
    
    @JsonIgnore
    @Length(max=18,min=6,message="密码的长度必须在6-18字之间")
    private String password;

    @Length(max=11,min=11,message="手机号必须是11位")
    private String phone;

    @Email(message="邮箱不符合规则")
    private String email;

    private Date created;

    private Date updated;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the updated
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    
    
}
