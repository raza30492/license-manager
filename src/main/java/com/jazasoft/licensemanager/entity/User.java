package com.jazasoft.licensemanager.entity;

import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jazasoft.licensemanager.Role;
import com.jazasoft.licensemanager.util.Utils;
import com.jazasoft.licensemanager.validation.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "users", indexes = @Index(columnList = "email,username"))
public class User extends Auditable<String> implements UserDetails{

    private static final long serialVersionUID = 8541623741428134463L;

    @NotNull @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String firstName;

    @NotNull @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String lastName;

    @NotNull @Pattern(regexp="^[a-zA-Z0-9_.-]{3,50}$")
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull @Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Pattern(regexp="[0-9]{10}")
    private String mobile;

    @OneToOne(cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn
    private Company company;

    @JsonIgnore
    private String otp;

    @JsonIgnore
    private Integer retryCount;

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    private Date otpSentAt;

    private boolean accountExpired;

    private boolean accountLocked;

    private boolean credentialExpired;

    @NotNull @Roles(enumClass = Role.class)
    private String roles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<License> licenses = new HashSet<>();

    public User() {
    }

    public User(String firstName, String username, String email, String password, String mobile,boolean enabled, boolean accountExpired, boolean accountLocked, boolean credentialExpired) {
        super(enabled);
        this.firstName = firstName;
        this.username = username;
        this.email = email;
        setPassword(password);
        this.mobile = mobile;
        this.accountExpired = accountExpired;
        this.accountLocked = accountLocked;
        this.credentialExpired = credentialExpired;
    }

    public User(String firstName, String username, String email, String mobile, String roles) {
        this.firstName = firstName;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roleList = Utils.getRoles(roles);
        List<GrantedAuthority> rls = new ArrayList<>();
        if (roleList == null) return rls;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(Set<License> licenses) {
        this.licenses = licenses;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                ", createdBy=" + createdBy +
                ", modifiedBy=" + modifiedBy +
                ", firstName='" + firstName + '\'' +
                ", enabled=" + enabled +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", mobile='" + mobile + '\'' +
                ", otp='" + otp + '\'' +
                ", retryCount=" + retryCount +
                ", otpSentAt=" + otpSentAt +
                ", accountExpired=" + accountExpired +
                ", accountLocked=" + accountLocked +
                ", credentialExpired=" + credentialExpired +
                ", roles='" + roles + '\'' +
                '}';
    }
}
