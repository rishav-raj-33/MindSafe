package mindSafe.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails  {	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
   private Integer userId;
   @Column(unique = true)
   private String email;
   private String name;
   private String password;
   private String profilePhoto;
   private boolean enabled=false;
	private boolean locked=false;
   
   @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<KeyVault> listOfKey=new ArrayList<>();

  @Override
 public Collection<? extends GrantedAuthority> getAuthorities() {
	return Collections.emptyList();
}

 @Override
 public String getUsername() {
	return this.email;
}

 @Override
 public boolean isAccountNonExpired() {
	return true;
}

 @Override
 public boolean isAccountNonLocked() {
	return !locked;
}

 @Override
 public boolean isCredentialsNonExpired() {

	return true;
}
   @Override
   public boolean isEnabled() {
	return enabled;
}
}
