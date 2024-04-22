package mindSafe.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EmailToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	private String token;
	private LocalDateTime createAt;
	private LocalDateTime expires;
	private LocalDateTime confirmedAt;
	public EmailToken( String token, LocalDateTime createAt, LocalDateTime expires,
			LocalDateTime confirmedAt) {
		super();
		this.token = token;
		this.createAt = createAt;
		this.expires = expires;
		this.confirmedAt = confirmedAt;
	}
	
	@ManyToOne
	private User user;
}
