package shecodeshack.org.audioview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListWrapper<T> implements Serializable {
    private List<T> list;

    public ListWrapper(){
        list = new ArrayList<>();
    }

    public ListWrapper(List<T> list){
        this.list = list;
    }


    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
