package mindSafe.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyVault {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer keyId;
	@Column(unique = true)
	private String userName;
	private String password;
	private String notes;
	@ManyToOne
	private User user;
}
