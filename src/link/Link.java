package link;

public class Link {

	private String title;
	private String url;
	private String category;
	private String memo;

	public Link(String title, String url, String category, String memo) {
		this.title = title;
		this.url = url;
		this.category = category;
		this.memo = memo;
	}

	public Link() {
		
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return url;
	}

	public String getCategory() {
		return category;
	}

	public String getMemo() {
		return memo;
	}

	@Override
	public String toString() {
		return title + "(" + category + ")";
	}

}
