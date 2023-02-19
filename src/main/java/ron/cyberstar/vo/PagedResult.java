package ron.cyberstar.vo;

import java.util.List;

public class PagedResult {

  private long total;
  private int pageSize;
  private int index;

  private List<String> name;

  public long getTotal() {
    return total;
  }

  public void setTotal(long total) {
    this.total = total;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public List<String> getName() {
    return name;
  }

  public void setName(List<String> name) {
    this.name = name;
  }

}
