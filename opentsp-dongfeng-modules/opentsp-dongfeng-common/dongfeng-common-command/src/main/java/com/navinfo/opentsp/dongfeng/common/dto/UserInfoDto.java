package com.navinfo.opentsp.dongfeng.common.dto;

public class UserInfoDto extends BaseDto
{
    private Long userId;
    
    private String username;
    
    private String password;
    
    private String verycode;
    
    private String email;// 用户邮箱
    
    private int type;// 1.系统管理员 2.车厂 3.服务站 4.运输企业
    
    private int jobType;
    
    private int verycodetype;// 是否需要验证，1：要验证，-1：不需要验证
    
    private String token; // 用户标记
    
    private String accountImage; // 用户头像

    private int terAuthority;//终端及终端型号权限，0 无，1 有

    public int getJobType()
    {
        return jobType;
    }
    
    public void setJobType(int jobType)
    {
        this.jobType = jobType;
    }
    
    public Long getUserId()
    {
        return userId;
    }
    
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    
    public int getVerycodetype()
    {
        return verycodetype;
    }
    
    public void setVerycodetype(int verycodetype)
    {
        this.verycodetype = verycodetype;
    }
    
    public int getType()
    {
        return type;
    }
    
    public void setType(int type)
    {
        this.type = type;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getVerycode()
    {
        return verycode;
    }
    
    public void setVerycode(String verycode)
    {
        this.verycode = verycode;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getAccountImage()
    {
        return accountImage;
    }
    
    public void setAccountImage(String accountImage)
    {
        this.accountImage = accountImage;
    }

    public int getTerAuthority() {
        return terAuthority;
    }

    public void setTerAuthority(int terAuthority) {
        this.terAuthority = terAuthority;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", verycode='" + verycode + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", jobType=" + jobType +
                ", verycodetype=" + verycodetype +
                ", token='" + token + '\'' +
                ", accountImage='" + accountImage + '\'' +
                ", terAuthority=" + terAuthority +
                '}';
    }
}
