package models;

import java.util.List;

public class QuestionList {
    private List<Question> items;
    private boolean has_more;
    private int quota_max;
    private int quota_remaining;
    
	public List<Question> getItems() {
		return items;
	}

	public boolean isHasMore() {
		return has_more;
	}    
}
