package llpandroid.com.searchdata;

public class SortModel {

    private String name;   //显示的数据
    private String sortLetters;  //显示数据拼音的首字母

    private int id;
    private int province_id;
    private String sort_name;
    private String month_price;
    private String order_price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public String getSort_name() {
        return sort_name;
    }

    public void setSort_name(String sort_name) {
        this.sort_name = sort_name;
    }

    public String getMonth_price() {
        return month_price;
    }

    public void setMonth_price(String month_price) {
        this.month_price = month_price;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }
}
