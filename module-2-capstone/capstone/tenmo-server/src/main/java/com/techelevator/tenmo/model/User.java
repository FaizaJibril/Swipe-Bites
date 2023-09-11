package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {

   private long id;
   private String username;
   private String email;
   @JsonIgnore // prevent from being sent to client
   private String password;
   @JsonIgnore
   private boolean activated;
   @JsonIgnore
   private Set<Authority> authorities = new HashSet<>();

   public User() {
   }

   public User(long id, String username, String password, String authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      if (authorities != null) this.setAuthorities(authorities);
      this.activated = true;
   }

   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
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
      this.password = password;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   public static User valueOf(Principal principal) {
      UserDetail userDetail = (UserDetail) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
      StringBuilder builder = new StringBuilder();
      for (GrantedAuthority a : userDetail.getAuthorities()) {
         builder.append(a.getAuthority()).append(",");
      }
      String auths = builder.toString();
      return new User(userDetail.getId(), userDetail.getUsername(), userDetail.getPassword(), auths);
   }

   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for (String role : roles) {
         this.authorities.add(new Authority("ROLE_" + role));
      }
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              '}';
   }

   public BigDecimal getBalance() {
      return getBalance();

   }
}
