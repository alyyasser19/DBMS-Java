import java.beans.Transient;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.Vector;

public class Page implements Serializable {
    private int noRows;
    private Vector clusteringKey;
    private Vector <Record>tuples;
    private String table;
    private Transient  min;
    private Transient max;
    private static int maxPage;

    public Page(String table) throws IOException {
        this.table=table;
        this.maxPage= readingFromConfigFile("MaximumRowsCountinPage");
        tuples = new Vector<>();
    }


    public void insertRecord (Record r){ // this inserts in the right place
        if (this.tuples.size()==0)
            this.tuples.add(r);
       else {
           int i = 0 ;
           int indexF = 0 ;
           Record f = this.tuples.get(indexF);
           int indexL =this.tuples.size()-1;
           Record l = this.tuples.get(indexL);
           int indexM = indexF + (indexL - indexF)/2 ;
           Record m = this.tuples.get(indexM);

           if (f.compareTo(r)>0){  // first record greater than input
               this.tuples.add(0,r);
           }
           else if (r.compareTo(l)>0){
               this.tuples.add(this.tuples.size(),r);
           }
           else if (r.compareTo(f)>0 && r.compareTo(l)<0){  // input is in this page for sure
               while (indexF<=indexL){
                   indexM = indexF + (indexL - indexF)/2 ;
                   m = this.tuples.get(indexM);
                   f = this.tuples.get(indexF);
                   l = this.tuples.get(indexL);

                   if (r.compareTo(m)>=0){ // meaning upper half
                      indexF = indexM + 1;

                   }
                   else {                   //meaning lower half
                       indexL = indexM - 1 ;
                   }
               }//end of our loop
               i = indexF ;
               this.tuples.add(i,r);

           }
           else {
               System.out.println("Something went wrong in insertion");
           }



        }//end of else

    }//end of method



    public Object getMin() {
        return min;
    }

    public void setMin(Object min) {
        this.min = (Transient) min;
    }

    public Object getMax() {
        return max;
    }

    public void setMax(Object max) {
        this.max = (Transient) max;
    }

    public int getNoRows() {
        return noRows;
    }

    public void setNoRows(int noRows) {
        this.noRows = noRows;
    }

    public static void setMaxPage(int maxPage) {
        Page.maxPage = maxPage;
    }

    public static int getMaxPage() { return maxPage; }

    public Vector getTuples() {
        return tuples;
    }

    public void setTuples(Vector tuples) {
        this.tuples = tuples;
    }

    public Vector getClusteringKey() {
        return clusteringKey;
    }

    public void setClusteringKey(Vector clusteringKey) {
        this.clusteringKey = clusteringKey;
    }

    public String getTable() { return table; }

    public void setTable(String table) { this.table = table; }

    public int readingFromConfigFile(String string) throws IOException {
        Properties prop = new Properties();
        FileInputStream property = new FileInputStream("src/main/resources/DBApp.config");
        prop.load(property);
        return Integer.parseInt(prop.getProperty(string));
    }
}
