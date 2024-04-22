package mindSafe.helpers;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyPageResponse {
	private List<KeyVaultDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElemnets;
	private int totalPages;
	private boolean isLastPage;
}
