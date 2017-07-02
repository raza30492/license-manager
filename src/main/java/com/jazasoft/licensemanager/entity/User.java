package com.jazasoft.licensemanager.entity;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jazasoft.licensemanager.Role;
import com.jazasoft.licensemanager.util.Utils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users", indexes = @Index(columnList = "name,email,username"))
public class User extends Auditable<String> implements UserDetails{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String mobile;

    @JsonIgnore
    private Integer retryCount;

    @JsonIgnore
    private String otp;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date otpSentAt;

    private boolean accountExpired;

    private boolean accountLocked;

    private boolean credentialExpired;

    private String roles;

    public User() {
    }

    public User(String name, String username, String email, String password, String mobile,boolean enabled, boolean accountExpired, boolean accountLocked, boolean credentialExpired) {
        super(enabled);
        this.name = name;
        this.username = username;
        this.email = email;
        setPassword(password);
        this.mobile = mobile;
        this.accountExpired = accountExpired;
        this.accountLocked = accountLocked;
        this.credentialExpired = credentialExpired;
    }

    public User(String name, String username, String email, String password, String mobile) {
        this.name = name;
        this.username = username;
        this.email = email;
        setPassword(password);
        this.mobile = mobile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roleList = getRoles();
        List<GrantedAuthority> rls = new ArrayList<>();
        for (Role role: roleList) {
            rls.add(new SimpleGrantedAuthority(role.getValue()));
        }
        return rls;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.password = encoder.encode(password);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public void setCredentialExpired(boolean credentialExpired) {
        this.credentialExpired = credentialExpired;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getOtpSentAt() {
        return otpSentAt;
    }

    public void setOtpSentAt(Date otpSentAt) {
        this.otpSentAt = otpSentAt;
    }

    public void setRoles(Set<Role> roles) {
        if (roles != null) {
            StringBuilder builder = new StringBuilder();
            for (Role role: roles) {
                builder.append(role.getValue()).append(",");
            }
            if (builder.length() > 0) {
                builder.setLength(builder.length()-1);
                this.roles = builder.toString();
            }
        }
    }

    public Set<Role> getRoles() {
        return Utils.getRoles(this.roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", modifiedAt=" + modifiedAt +
                ", name='" + name + '\'' +
                ", enabled=" + enabled +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}