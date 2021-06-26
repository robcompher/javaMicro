package sample.restModels;

public class PersonWithPagination extends PersonRestModel{
	
	Pagination pagination;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	
	
}
