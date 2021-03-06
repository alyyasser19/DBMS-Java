import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

public class Record implements Comparable, Serializable {

     private Vector <Pair> data ;
     private Hashtable<String,Object> content ;

    public Vector<Pair> getData() {
        return data;
    }

    public void setData(Vector<Pair> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{"+ data +
                '}';
    }

    public Hashtable<String, Object> getContent() {
        return content;
    }

    public void setContent(Hashtable<String, Object> content) {
        this.content = content;
    }

    public Record(Hashtable<String, Object> h , String cKey) {
        Pair Primary = null ;
        this.data = new Vector<>();
        for(Map.Entry m: h.entrySet()){
          Pair p =  new Pair( m.getKey().toString() , m.getValue()) ;
          if (m.getKey().toString().equals(cKey))
              Primary = p ;
          else
              data.add(p);
        }
        if(Primary!=null)
            data.add(0,Primary);
        content = h ;
    }

    @Override
    public int compareTo(Object o) {
        Record r = (Record) o ;
        return this.getData().get(0).compareTo(r.getData().get(0));
    }

    public int compareToValue(Object o , String col ,String type){

        Object x = this.getContent().get(col);
        if  (type.equals("java.lang.Integer")){
            return ((Integer)x).compareTo(((Integer)o));
        }
        else if (type.equals("java.lang.Double")){
            return ((Double)x).compareTo(((Double)o));
        }
        else if (type.equals("java.lang.String")){
            return ((String)x).compareTo(((String)o));

        }
        else{
            return ((Date)x).compareTo(((Date)o));
        }

    }

}
